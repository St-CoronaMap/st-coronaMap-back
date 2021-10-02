package com.example.youtubedb.config;

import com.example.youtubedb.config.jwt.JwtResolver;
import com.example.youtubedb.config.jwt.handler.JwtAccessDeniedHandler;
import com.example.youtubedb.config.jwt.handler.JwtAuthenticationEntryPoint;
import com.example.youtubedb.config.jwt.TokenProvider;
import com.example.youtubedb.domain.member.Authority;
import com.example.youtubedb.repository.MemberRepository;
import com.example.youtubedb.service.ChangingPassword;
import com.example.youtubedb.service.ChangingPassword.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtResolver jwtResolver;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final MemberRepository memberRepository;
  private final StringRedisTemplate template;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()

      .exceptionHandling()
      .authenticationEntryPoint(jwtAuthenticationEntryPoint)
      .accessDeniedHandler(jwtAccessDeniedHandler)

      .and()
      .headers()
      .frameOptions()
      .sameOrigin()

      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

      .and()
      .authorizeRequests()
      .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
      .antMatchers("/api/member").authenticated()
      .antMatchers("/api/member/change/**").authenticated()
      .antMatchers("/api/member/upload").authenticated()
      .antMatchers("/api/member/**").permitAll()
      .antMatchers("/api/**").authenticated()
      .and()
      .cors()
      .and()
      .apply(new JwtSecurityConfig(jwtResolver));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public ChangingPassword changingPassword() {
    final ChangingPassword db = new UpdateDatabase(memberRepository);
    final ChangingPassword retryableDB = new RetryableDB(10, db);

    final CacheClient cache = new StringRedis(template);
    final CacheClient retryableCache = new RetryableCache(10, cache);

    return new EvictCache(retryableDB, retryableCache);
  }
}
