package Sea.incubator.sgdeb;

import Sea.incubator.sgdeb.service.ExportationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PvNotesApplication {


    public static void main(String[] args) {
        SpringApplication.run(PvNotesApplication.class, args);

    }


}