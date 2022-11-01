package be.lennix.oshish.cookie;

import org.apache.catalina.Context;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class CookieConfiguration {
    @Bean
    WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return tomcatServletWebServerFactory -> tomcatServletWebServerFactory.addContextCustomizers(new TomcatContextCustomizer() {
            @Override
            public void customize(Context context) {
                context.setCookieProcessor(new LegacyCookieProcessor());
            }
        });
    }
}
