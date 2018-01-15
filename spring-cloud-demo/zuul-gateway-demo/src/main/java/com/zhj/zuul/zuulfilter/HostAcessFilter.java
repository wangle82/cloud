package com.zhj.zuul.zuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhanghongjun on 2017/12/7.
 */
@Component
public class HostAcessFilter extends ZuulFilter {
    @Override
    public boolean shouldFilter() {
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public int filterOrder() {
        return 0;// 优先级为0，数字越大，优先级越低
    }

    /**
     * pre：可以在请求被路由之前调用
     route：在路由请求时候被调用
     post：在route和error过滤器之后被调用
     error：处理请求时发生错误时被调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";// 前置过滤器
    }

    /**
     * 过滤器具体逻辑
     * @return
     */
    @Override
    public Object run() {
        // 过滤器间 通过RequestContext共享状态，通过一些显示set设置，达到过滤器间通讯的目的
        RequestContext ctx = RequestContext.getCurrentContext();  // threadlocal 实现
        HttpServletRequest request = ctx.getRequest();


        String host = request.getHeader("host");// 获取请求的参数
        if("localhost".equals(host)) {// 如果请求的参数不为空，且值为chhliu时，则通过
            System.err.printf("%s 验证 %s 通过",this.getClass().getSimpleName(),request.getRequestURL());
            ctx.setSendZuulResponse(true);// 对该请求进行路由到后端
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
            return null;
        }else{
            System.err.printf("%s 验证 %s 不通过",this.getClass().getSimpleName(),request.getRequestURL());
            ctx.setSendZuulResponse(false);// 拦截该请求，不对其进行路由
            ctx.setResponseStatusCode(401);// 返回错误码
            ctx.setResponseBody("{\"result\":\"host is not correct!\"}");// 返回错误内容
            ctx.set("isSuccess", false);
            return null;
        }
    }
}
