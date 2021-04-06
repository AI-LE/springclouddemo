package so.dian.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import so.dian.demo.feign.ProductFeignService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author sali
 * @date 2020/12/8 20:39
 * @Description
 */
@RestController
public class ConsumerController {


    @Resource
    private ProductFeignService productFeignService;

    @RequestMapping("/consumer")
    public String product() {
        return productFeignService.product();
    }
}
