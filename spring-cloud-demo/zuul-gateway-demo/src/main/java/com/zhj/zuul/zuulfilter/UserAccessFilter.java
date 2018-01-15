package com.zhj.zuul.zuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhanghongjun on 2017/12/7.
 */
@Component
public class UserAccessFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        // 根据前面顾虑器的处理结果 决定是否执行过滤逻辑
        RequestContext ctx = RequestContext.getCurrentContext();
        return (boolean) ctx.get("isSuccess");
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String username = request.getParameter("user");
        if(null != username ) {
            System.err.printf("%s 验证 %s 通过",this.getClass().getSimpleName(),request.getRequestURL());
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        }else{
            System.err.printf("%s 验证 %s 不通过",this.getClass().getSimpleName(),request.getRequestURL());
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("{\"result\":\"user is not correct!\"}");
            ctx.set("isSuccess", false);
            return null;
        }
    }
}
