package be.lennix.oshish.cookie;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static be.lennix.oshish.cookie.OshiShCookie.DefaultOshishCookieData.*;

@Component
public class CookieFilter implements Filter {
    @Resource
    OshiShSaveDto oshiShSaveDto;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        Optional<Cookie> cookie = Optional.empty();

        try {
            cookie = Arrays.stream(httpServletRequest.getCookies())
                    .filter(c -> c.getName().equals(OshiShCookie.name))
                    .findFirst();
        } catch (NullPointerException ignored) {}

        if(cookie.isPresent()) {
            oshiShSaveDto = new ObjectMapper()
                    .readValue(cookie.get().getValue(), OshiShSaveDto.class);
        } else {
            oshiShSaveDto = OshiShSaveDto.builder()
                    .youtubers(YOUTUBERS)
                    .hashtags(HASHTAGS)
                    .build();
        }

        ((HttpServletResponse) servletResponse).addCookie(new OshiShCookie(oshiShSaveDto));

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
