package com.kooli.app.kooli.payloads;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RefreshTokenRequest {
	
    @NotBlank
    private String refreshToken;

}
