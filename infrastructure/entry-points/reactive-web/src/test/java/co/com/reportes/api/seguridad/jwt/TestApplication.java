package co.com.reportes.api.seguridad.jwt;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan(basePackageClasses = JwtAuthenticationFilter.class)
public class TestApplication {
}
