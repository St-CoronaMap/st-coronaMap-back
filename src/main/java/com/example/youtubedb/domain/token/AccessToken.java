package com.example.youtubedb.domain.token;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.time.LocalDateTime;

import static com.example.youtubedb.util.ContractUtil.requires;

@Value
@Accessors(fluent = true)
public class AccessToken {
	String loginId;
	Instant expirationAt;

	public AccessToken(
		String loginId,
		Instant expirationAt) {

		requires(loginId != null && !loginId.isEmpty());
		requires(expirationAt.isAfter(Instant.now()));

		this.loginId = loginId;
		this.expirationAt = expirationAt;
	}
}
