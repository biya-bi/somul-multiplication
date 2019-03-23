/**
 * 
 */
package microservices.book.multiplication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author biya-bi
 *
 */
@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {

	/**
	 * Enables Cross-Origin Resource Sharing (CORS).
	 * <p>
	 * More info:
	 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cors.html
	 * 
	 * @param registry the registry to use for the registration of CORS configuration
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
}
