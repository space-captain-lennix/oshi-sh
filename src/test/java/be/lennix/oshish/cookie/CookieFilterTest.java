package be.lennix.oshish.cookie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static be.lennix.oshish.cookie.OshiShCookie.DefaultOshishCookieData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CookieFilterTest {

    @Test
    void doFilter() throws ServletException, IOException {
        CookieFilter cookieFilter = new CookieFilter();
        ArgumentCaptor<OshiShCookie> cookieArgumentCaptor = ArgumentCaptor.forClass(OshiShCookie.class);

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(httpServletRequest.getCookies()).thenReturn(null);

        cookieFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        verify(httpServletResponse, times(1)).addCookie(cookieArgumentCaptor.capture());

        OshiShCookie oshiShCookieDefault = cookieArgumentCaptor.getValue();

        oshiShCookieDefault.getOshiShSaveDto()
                .getYoutubers()
                .forEach(youtuber -> Assertions.assertThat(YOUTUBERS).contains(youtuber));

        oshiShCookieDefault.getOshiShSaveDto()
                .getHashtags()
                .forEach(hashtag -> Assertions.assertThat(HASHTAGS).contains(hashtag));

        OshiShSaveDto oshiShSaveDto = OshiShSaveDto.builder()
                .youtubers(List.of("Excellyne", "Lazer", "Kai", "Seri"))
                .hashtags(List.of("Test1", "Test2"))
                .build();

        OshiShCookie oshiShCookie = new OshiShCookie(oshiShSaveDto);

        when(httpServletRequest.getCookies()).thenReturn(new Cookie[]{oshiShCookie});

        cookieFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        verify(httpServletResponse, times(2)).addCookie(cookieArgumentCaptor.capture());

        OshiShCookie oshiShCookieResult = cookieArgumentCaptor.getValue();

        oshiShCookieResult.getOshiShSaveDto()
                .getYoutubers()
                .forEach(youtuber -> Assertions.assertThat(oshiShSaveDto.getYoutubers()).contains(youtuber));

        oshiShCookieResult.getOshiShSaveDto()
                .getHashtags()
                .forEach(hashtag -> Assertions.assertThat(oshiShSaveDto.getHashtags()).contains(hashtag));


    }
}