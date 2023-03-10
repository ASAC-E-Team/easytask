package com.easytask.easytask.common.jwt;
import com.easytask.easytask.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//TokenProvider, JwtFilter 를 SecurityConfig에 적용할때 사용

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private TokenProvider tokenProvider;
    private RedisUtil redisUtil;

    public JwtSecurityConfig(TokenProvider tokenProvider, RedisUtil redisUtil) {
        this.tokenProvider = tokenProvider;
        this.redisUtil=redisUtil;
    }

    //TokenProvider 를 주입받아서 JwtFilter 를 통해 Security 로직에 필터를 등록한다.
    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
                new JwtFilter(tokenProvider,redisUtil),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
