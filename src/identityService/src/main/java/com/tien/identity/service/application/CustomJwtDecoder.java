package com.tien.identity.service.application;

import java.text.ParseException;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.tien.identity.service.dto.request.IntrospectRequest;
import com.tien.identity.service.service.AuthenticationService;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    private AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoderecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {

        // xac thuc bang introspect
        try {
            var response = authenticationService.introspect(
                    IntrospectRequest.builder().token(token).build());

            if (!response.isValid()) {
                throw new JwtException("invalid token");
            }
        } catch (ParseException | JOSEException e) {
            throw new JwtException(e.getMessage());
        }

        // neu introspect = true => decode
        if (Objects.isNull(nimbusJwtDecoderecoder)) {
            var secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");

            nimbusJwtDecoderecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoderecoder.decode(token);
    }
}
