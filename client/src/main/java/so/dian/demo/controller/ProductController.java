package so.dian.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sali
 * @date 2020/12/8 20:39
 * @Description
 */
@RestController
@Slf4j
public class ProductController {

    @RequestMapping("/product")
    public String product(HttpServletRequest request) {
        log.info("{}",request.getRequestURL());
        return "OK";
    }
}
