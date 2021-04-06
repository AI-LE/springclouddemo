package so.dian.demo;

//import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author sali
 * @date 2020/12/8 13:29
 * @Description
 */

@SpringBootApplication
@EnableEurekaServer
//@Slf4j
public class ServerApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ServerApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
