package rc.holding.houseplants.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class PhotoResourceWebConfig implements WebMvcConfigurer {

    @Value("${app.photos.original-path}")
    public String originalDir;

    @Value("${app.photos.thumbnail-path}")
    public String thumbnailDir;
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("file:" + originalDir);
        registry
                .addResourceHandler("/images/thumbnails/**")
                .addResourceLocations("file:" + thumbnailDir);
    }
}
