package Sea.incubator.sgdeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
public class Account {

	public static void main(String[] args) {
		SpringApplication.run(Account.class, args);
	}

}
