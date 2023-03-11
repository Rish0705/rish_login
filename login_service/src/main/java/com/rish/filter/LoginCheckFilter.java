package com.rish.filter;


import com.alibaba.fastjson.JSON;

import com.rish.domain.res.BaseRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {


    private final StringRedisTemplate template;


    // 路径匹配器，支持通配符 (饿汉)
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public LoginCheckFilter(StringRedisTemplate template) {
        this.template = template;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1、获取本次请求的URI
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{}", requestURI);

        // 定义不需要处理的请求路径
        String[] urls = new String[]{
                "/code",//移动端发送短信
                "/login"//移动端登录
        };

        // 2、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        // 3、如果不需要处理，则直接放行
        if (check) {
            log.info("本次请求{}不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // 4、需要处理，判断登录状态
        if (request.getHeader("token") != null){
            if(Boolean.TRUE.equals(template.hasKey(request.getHeader("token")))){
                // 带有登录权限令牌
                filterChain.doFilter(request, response);
                return;
            }
            response.getWriter().write(JSON.toJSONString(BaseRes.error("身份过期")));
        }
        log.info("用户未登录");
        // 5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(BaseRes.error("NOT LOGIN")));
    }


    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
