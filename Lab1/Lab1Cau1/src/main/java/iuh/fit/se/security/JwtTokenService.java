package iuh.fit.se.security;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class JwtTokenService {
    private final JwtEncoder accessEncoder;
    private final JwtEncoder refreshEncoder;
    private final JwtDecoder refreshDecoder;
    private final UserDetailsService userDetailsService;
    @Value("${security.jwt.expiration_access-ms}")
    private long expirationAccessMs;
    @Value("${security.jwt.expiration_refresh-ms}")
    private long expirationRefreshMs;

    public JwtTokenService(@Qualifier("jwtAccessEncoder") JwtEncoder accessEncoder,
                           @Qualifier("jwtRefreshEncoder") JwtEncoder refreshEncoder,
                           @Qualifier("jwtRefreshDecoder") JwtDecoder refreshDecoder,
                           UserDetailsService userDetailsService) {
        this.accessEncoder = accessEncoder;
        this.refreshEncoder = refreshEncoder;
        this.refreshDecoder = refreshDecoder;
        this.userDetailsService = userDetailsService;
    }

    public String generateAccessToken(UserDetails userDetails) {
        Instant now = Instant.now();

        List<String> authorities = userDetails
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusMillis(expirationAccessMs))
                .subject(userDetails.getUsername())
                .claim("authorities", authorities)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        String tokenValue = accessEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

        return tokenValue;
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Instant now = Instant.now();

        List<String> authorities = userDetails
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusMillis(expirationRefreshMs))
                .subject(userDetails.getUsername())
                .claim("authorities", authorities)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        String tokenValue = refreshEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

        return tokenValue;
    }

    public UserDetails validateRefreshToken(String refreshToken) {
        try {
            Jwt jwt = refreshDecoder.decode(refreshToken);;
            return userDetailsService.loadUserByUsername(jwt.getSubject());
        } catch (JwtException e) {
            throw new RuntimeException(e);
        }
    }
}
