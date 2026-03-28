package com.beaker.api;

import com.beaker.api.dto.AutoAgentRequestDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @Author beaker
 * @Date 2026/3/27 15:31
 * @Description ai 服务接口
 */
public interface IAiAgentService {

    ResponseBodyEmitter autoAgent(AutoAgentRequestDTO request, HttpServletResponse response);
}
