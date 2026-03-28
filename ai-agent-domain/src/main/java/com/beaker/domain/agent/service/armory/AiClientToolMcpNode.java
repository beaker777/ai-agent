package com.beaker.domain.agent.service.armory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import com.beaker.domain.agent.model.entity.ArmoryCommandEntity;
import com.beaker.domain.agent.model.valobj.enums.AiAgentEnumVO;
import com.beaker.domain.agent.model.valobj.AiClientToolMcpVO;
import com.beaker.domain.agent.service.armory.factory.DefaultArmoryStrategyFactory;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * @Author beaker
 * @Date 2026/3/22 20:51
 * @Description mcp 节点数据加载
 */
@Service
@Slf4j
public class AiClientToolMcpNode extends AbstractArmorySupport{

    @Resource
    private AiClientModelNode aiClientModelNode;

    @Override
    protected String doApply(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("Ai agent 构建, Mcp 节点 {}", JSON.toJSONString(requestParameter));

        List<AiClientToolMcpVO> aiClientToolMcpList = dynamicContext.getValue(dataName());

        if (aiClientToolMcpList == null || aiClientToolMcpList.isEmpty()) {
            log.warn("没有要被初始化的 ai client tool mcp");
            return router(requestParameter, dynamicContext);
        }

        for (AiClientToolMcpVO aiClientToolMcpVO : aiClientToolMcpList) {
            // 创建 Mcp 服务
            McpSyncClient mcpSyncClient = createMcpSyncClient(aiClientToolMcpVO);

            // 注册 Mcp bean
            registerBean(beanName(aiClientToolMcpVO.getMcpId()), McpSyncClient.class, mcpSyncClient);
        }

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> get(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return aiClientModelNode;
    }

    @Override
    protected String beanName(String id) {
        return AiAgentEnumVO.AI_CLIENT_TOOL_MCP.getBeanName(id);
    }

    @Override
    protected String dataName() {
        return AiAgentEnumVO.AI_CLIENT_TOOL_MCP.getDataName();
    }

    private McpSyncClient createMcpSyncClient(AiClientToolMcpVO aiClientToolMcpVO) {
        String transportType = aiClientToolMcpVO.getTransportType();

        switch (transportType) {
            case "sse" -> {
                AiClientToolMcpVO.TransportConfigSse transportConfigSse = aiClientToolMcpVO.getTransportConfigSse();
                SseTransportOptions sseTransportOptions = resolveSseTransportOptions(aiClientToolMcpVO, transportConfigSse);
                log.info("Initializing Tool Mcp SSE, mcpId: {}, mcpName: {}, baseUri: {}, sseEndpoint: {}, hasQuery: {}",
                        aiClientToolMcpVO.getMcpId(),
                        aiClientToolMcpVO.getMcpName(),
                        sseTransportOptions.baseUri(),
                        maskSensitiveQuery(sseTransportOptions.sseEndpoint()),
                        sseTransportOptions.sseEndpoint().contains("?"));

                HttpClientSseClientTransport sseClientTransport = HttpClientSseClientTransport
                        .builder(sseTransportOptions.baseUri())
                        .sseEndpoint(sseTransportOptions.sseEndpoint())
                        .build();

                McpSyncClient mcpSyncClient = McpClient.sync(sseClientTransport)
                        .requestTimeout(Duration.ofMinutes(aiClientToolMcpVO.getRequestTimeout()))
                        .build();
                var init_sse = mcpSyncClient.initialize();

                log.info("Tool Mcp Sse Initialized {}", init_sse);
                return mcpSyncClient;
            }
            case "stdio" -> {
                AiClientToolMcpVO.TransportConfigStdio transportConfigStdio = aiClientToolMcpVO.getTransportConfigStdio();
                Map<String, AiClientToolMcpVO.TransportConfigStdio.Stdio> stdioMap = transportConfigStdio.getStdio();
                AiClientToolMcpVO.TransportConfigStdio.Stdio stdio = stdioMap.get(aiClientToolMcpVO.getMcpName());

                ServerParameters stdioParams = ServerParameters.builder(stdio.getCommand())
                        .args(stdio.getArgs())
                        .env(stdio.getEnv())
                        .build();

                McpSyncClient mcpSyncClient = McpClient.sync(new StdioClientTransport(stdioParams))
                        .requestTimeout(Duration.ofMinutes(aiClientToolMcpVO.getRequestTimeout()))
                        .build();
                var init_stdio = mcpSyncClient.initialize();

                log.info("Tool Mcp Stdio Initialized {}", init_stdio);
                return mcpSyncClient;
            }
        }

        throw new RuntimeException("error! transportType " + transportType + " not exist!");
    }

    private SseTransportOptions resolveSseTransportOptions(AiClientToolMcpVO aiClientToolMcpVO,
                                                           AiClientToolMcpVO.TransportConfigSse transportConfigSse) {
        if (transportConfigSse == null) {
            throw new IllegalArgumentException("SSE transport config is null, mcpId: " + aiClientToolMcpVO.getMcpId());
        }

        String baseUri = StringUtils.trimToEmpty(transportConfigSse.getBaseUri());
        String sseEndpoint = StringUtils.trimToEmpty(transportConfigSse.getSseEndpoint());

        if (StringUtils.isBlank(baseUri)) {
            throw new IllegalArgumentException("SSE baseUri is blank, mcpId: " + aiClientToolMcpVO.getMcpId());
        }

        if (StringUtils.isBlank(sseEndpoint)) {
            // 兼容旧配置：baseUri 存完整 SSE URL 时，从完整 URL 中解析 endpoint。
            if (looksLikeFullSseUrl(baseUri)) {
                return parseFullSseUrl(baseUri, aiClientToolMcpVO.getMcpId());
            }

            throw new IllegalArgumentException("SSE sseEndpoint is blank, mcpId: " + aiClientToolMcpVO.getMcpId());
        }

        if (!(sseEndpoint.startsWith("sse") || sseEndpoint.startsWith("/sse"))) {
            throw new IllegalArgumentException("SSE sseEndpoint must start with sse or /sse, mcpId: "
                    + aiClientToolMcpVO.getMcpId() + ", current: " + sseEndpoint);
        }

        if (!containsKnownAuthParam(sseEndpoint)) {
            log.warn("SSE endpoint may be missing auth query parameter, mcpId: {}, sseEndpoint: {}",
                    aiClientToolMcpVO.getMcpId(), maskSensitiveQuery(sseEndpoint));
        }

        return new SseTransportOptions(baseUri, sseEndpoint);
    }

    private boolean looksLikeFullSseUrl(String baseUri) {
        return baseUri.contains("/sse") || baseUri.contains("sse?");
    }

    private SseTransportOptions parseFullSseUrl(String fullUrl, String mcpId) {
        try {
            URI uri = new URI(fullUrl);
            String path = StringUtils.defaultString(uri.getPath());
            int sseIndex = path.indexOf("/sse");
            if (sseIndex < 0) {
                throw new IllegalArgumentException("SSE full url does not contain /sse, mcpId: " + mcpId + ", fullUrl: " + fullUrl);
            }

            String basePath = path.substring(0, sseIndex + 1);
            String endpointPath = path.substring(sseIndex + 1);
            String query = uri.getRawQuery();

            String parsedBaseUri = new URI(uri.getScheme(), uri.getAuthority(), basePath, null, null).toString();
            String parsedSseEndpoint = StringUtils.isBlank(query) ? endpointPath : endpointPath + "?" + query;

            if (!containsKnownAuthParam(parsedSseEndpoint)) {
                log.warn("Parsed SSE endpoint may be missing auth query parameter, mcpId: {}, sseEndpoint: {}",
                        mcpId, maskSensitiveQuery(parsedSseEndpoint));
            }

            return new SseTransportOptions(parsedBaseUri, parsedSseEndpoint);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid SSE full url, mcpId: " + mcpId + ", fullUrl: " + fullUrl, e);
        }
    }

    private boolean containsKnownAuthParam(String sseEndpoint) {
        return sseEndpoint.contains("api_key=")
                || sseEndpoint.contains("apiKey=")
                || sseEndpoint.contains("appId=")
                || sseEndpoint.contains("appid=");
    }

    private String maskSensitiveQuery(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }

        return value
                .replaceAll("(api_key=)[^&]+", "$1***")
                .replaceAll("(apiKey=)[^&]+", "$1***")
                .replaceAll("(appId=)[^&]+", "$1***")
                .replaceAll("(appid=)[^&]+", "$1***");
    }

    private record SseTransportOptions(String baseUri, String sseEndpoint) {}
}
