package com.fpcms.common.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.duowan.common.util.LogTraceUtils;
import com.duowan.common.util.Profiler;
import com.fpcms.common.util.Constants;

/**
 * 存放在MDC中的数据，log4j可以直接引用并作为日志信息打印出来.
 * 
 * <pre>
 * 示例使用:
 * log4j.appender.stdout.layout.conversionPattern=%d [%X{loginUserId}/%X{req.remoteAddr}/%X{traceId} - %X{req.requestURI}?%X{req.queryString}] %-5p %c{2} - %m%n
 * </pre>
 * @author badqiu
 */
public class LoggerMDCFilter extends OncePerRequestFilter implements Filter{
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain chain)throws ServletException,IOException {
        try {
            //示例为一个固定的登陆用户,请直接修改代码
            MDC.put("loginUserId", "demo-loginUsername");
            
            MDC.put("req.requestURI", StringUtils.defaultString(request.getRequestURI()));
            MDC.put("req.queryString", StringUtils.defaultString(request.getQueryString()));
            MDC.put("req.requestURIWithQueryString", request.getRequestURI() + (request.getQueryString() == null ? "" : "?"+request.getQueryString()));
            MDC.put("req.remoteAddr", StringUtils.defaultString(request.getRemoteAddr()));
            
            Profiler.start(request.getServletPath());
            
            //为每一个请求创建一个traceId，方便查找日志时可以根据ID查找出一个http请求所有相关日志
            // LogTraceUtils完成的功能是: MDC.put("traceId",StringUtils.remove(UUID.randomUUID().toString(),"-"))
            LogTraceUtils.beginTrace();
            chain.doFilter(request, response);
        }finally {
        	Profiler.release();
        	Constants.LOGGER_DUMP_PROFILER.info(Profiler.dump());
            clearMDC();
        }
    }

    private static void clearMDC() {
        Map map = MDC.getContext();
        if(map != null) {
            map.clear();
        }
    }
}
