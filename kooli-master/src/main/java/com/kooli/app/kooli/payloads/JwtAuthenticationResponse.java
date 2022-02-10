package com.kooli.app.kooli.payloads;



import com.kooli.app.kooli.securities.UserPrincipal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private UserPrincipal user;

    public JwtAuthenticationResponse(String accessToken, String refreshToken, UserPrincipal user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }
}