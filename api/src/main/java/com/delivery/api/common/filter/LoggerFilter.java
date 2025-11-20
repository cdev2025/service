package com.delivery.api.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // servletRequest, servletResponse -> HttpServletRequest, HttpServletResponse 형변환
        // 캐싱 가능한 래퍼 객체로 감싸서, 요청과 응답 데이터를 캐싱해서 나중에 읽을 수 있도록 함.
        var req = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        var res = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        log.info("INIT URI: {}", req.getRequestURI());

        // 필터 체인에 캐싱 래퍼로 감싼 요청과 응답 객체를 넘김.
        filterChain.doFilter(req, res);

        // 필터 이후의 모든 뒷단, 컨트롤러나 인터셉터 등에서 접근하는 request와response는
        // 캐싱 래퍼로 감싼 req와 res객체가 전달되므로, 요청/응답 데이터에 쉽게 접근 가능

        // request 정보
        var headerNames = req.getHeaderNames();
        var headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(headerkey -> {
            var headerValue = req.getHeader(headerkey);

            // authorization-token: ???, user-agent: ????, ...
            headerValues
                    .append("[")
                    .append(headerkey)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");
        });

        var requestBody = new String(req.getContentAsByteArray());
        var uri = req.getRequestURI();
        var method = req.getMethod();

        log.info(">>>>> uri: {}, method: {}, header: {}, body: {}", uri, method, headerValues, requestBody);

        // response 정보
        var responseHeaderValues = new StringBuilder();

        res.getHeaderNames().forEach(headerkey-> {
            var headerValue = res.getHeader(headerkey);

            responseHeaderValues
                    .append("[")
                    .append(headerkey)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");
        });

        var responseBody = new String(res.getContentAsByteArray());

        log.info("<<<< uri: {}, method: {}, header : {}, body : {}", uri, method, responseHeaderValues, responseBody);

        // 우리가 responseBody 내용을 읽어버렸기 때문에 다시 한번 초기화해줘야 함
        res.copyBodyToResponse(); // 꼭 있어야 함!!! 안쓰면 responseBody가 비워져서 나감
    }
}
