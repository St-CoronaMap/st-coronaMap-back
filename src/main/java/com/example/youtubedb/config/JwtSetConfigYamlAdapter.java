package com.example.youtubedb.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtSetConfigYamlAdapter {
	@Value("${jwt.secretKey}")
	String secretKey;
	@Value("${jwt.algorithm}")
	SignatureAlgorithm signatureAlgorithm;

	public JwtSetConfig toJwtSetConfig() {
		return new JwtSetConfig(secretKey);
	}
}