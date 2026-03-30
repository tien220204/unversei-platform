package com.tien.identity.service.application;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
// optional boi vi da duoc enable khi security duoc khoi  dong
@EnableWebSecurity
// pho bien hon so voi endpoint security
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomJwtDecoder customJwtDecoder;

    public SecurityConfig(CustomJwtDecoder customJwtDecoder){
        this.customJwtDecoder = customJwtDecoder;
    }

    private static final String[] PUBLIC_ENDPOINTS = {
        "/users/createUser", "/users/getMyInfo", "/auth/**",
    };

    //cors configuration
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //configure the origin,...
        corsConfiguration.addAllowedOrigin("http://localhost...");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        //apply for all endpoint
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);


        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                // cho phep nhung endpoin sau khong can xac thuc
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS)
                        .permitAll()
                        // yeu cau nhung enpoint sau can xac thu  va phan quyen
                        // claim trong token van la scope va sau khi vao filterchain bi doi ten thanh ROLE=>hasRole
                        // neu khong convert => SCOPE_...
                        // .requestMatchers(HttpMethod.GET, "/users/**").hasRole(Roles.ADMIN.name())
                        .anyRequest()
                        .authenticated());
        //        cau hinh de nhung endpoint khac co the truy cap bang token
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(customJwtDecoder)
                        .jwtAuthenticationConverter(jwtAujwtAuthenticationConverter())) // convert prefix claim
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
        //        spring security tu dong bat cau hinh csrf(bao ve endpoint khoi attack cross high)->forbiden(code
        // 403)->díable
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    // doi ten cho claim tu scope Prefix SCOPE sang prefix ROLE
    @Bean
    JwtAuthenticationConverter jwtAujwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // khong nen customize ten claim hoac delimeter vi la mac dinh cua spring security
        //        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); khong can nua boi vi da chu dong them
        // trong buildScope
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
