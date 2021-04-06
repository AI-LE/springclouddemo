package so.dian.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sali
 * @date 2020/12/9 15:36
 * @Description
 */
@Slf4j
@Component
public class DemoFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获得一个特殊请求头，判断是否有值：有返回null(放行)，没有 响应状态码401
        //1 获得工具类 (获得上下文对象)
        RequestContext requestContext = RequestContext.getCurrentContext();
        //2 通过工具类 获得请求对象
        HttpServletRequest request = requestContext.getRequest();
        log.info("{}", request.getRequestURL());
        //判断
//        requestContext.setSendZuulResponse(false);
//        requestContext.setResponseStatusCode(401);
        return null;

    }
}
