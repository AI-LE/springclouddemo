package so.dian.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sali
 * @date 2020/12/8 23:50
 * @Description
 */
@FeignClient(name = "product")
public interface ProductFeignService {

    @RequestMapping("product")
    String product();
}
