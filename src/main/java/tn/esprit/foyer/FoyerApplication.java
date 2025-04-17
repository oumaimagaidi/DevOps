package tn.esprit.foyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class FoyerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoyerApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer globalCorsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
						.addMapping("/**")                         // apply to all endpoints
						.allowedOrigins("http://192.168.252.114:4200") // only this frontend
						.allowedMethods("*")                       // GET, POST, PUT, DELETE, OPTIONS, etc.
						.allowedHeaders("*")                       // all request headers
						.allowCredentials(true);                   // allow cookies/auth if needed
			}
		};
	}
}
