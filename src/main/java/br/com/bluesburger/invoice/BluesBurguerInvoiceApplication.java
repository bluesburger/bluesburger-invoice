package br.com.bluesburger.invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/** 
 * 
*/
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = { "br.com.bluesburger.invoice" })
@EnableSqs
public class BluesBurguerInvoiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BluesBurguerInvoiceApplication.class, args);
	}
}