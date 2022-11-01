package be.lennix.oshish.cookie;

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
            oshiShSaveDto = ((OshiShCookie) cookie.get()).getOshiShSaveDto();
        } else {
            oshiShSaveDto = OshiShSaveDto.builder()
                    .youtubers(List.of("Virgil", "Raian", "Lila", "Diana", "Lapynne"))
                    .build();
        }

        ((HttpServletResponse) servletResponse).addCookie(new OshiShCookie(oshiShSaveDto));

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setupNewOshiShCookie() {
    }
}
