package com.example.youtubedb.config;

import com.example.youtubedb.config.jwt.AccessTokenProvider;
import com.example.youtubedb.config.jwt.time.RealTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {
	private JwtSetConfigYamlAdapter jwtSetConfigYamlAdapter;

	@Bean
	public AccessTokenProvider accessTokenProvider() {
		return new AccessTokenProvider(new RealTime());
	}
}