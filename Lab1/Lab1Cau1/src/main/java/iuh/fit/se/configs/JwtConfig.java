package iuh.fit.se.configs;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {
    @Value("${security.jwt.secret_access}")
    private String jwtSecretAccess;
    @Value("${security.jwt.secret_refresh}")
    private String jwtSecretRefresh;

    @Bean
    public SecretKey jwtSecretAccessKey() {
        if (jwtSecretAccess == null || jwtSecretAccess.isEmpty()) {
            throw new IllegalArgumentException("jwtSecret cannot be null or empty");
        }

        try {
            byte[] keyBytes = Base64.getDecoder().decode(jwtSecretAccess);
            // Cần đảm bảo key dài ít nhất 32 byte (256 bit) cho HMAC-SHA256
            if (keyBytes.length < 32) {
                throw new IllegalArgumentException("JWT secret key must be at least 32 bytes long after decoding.");
            }
            return new SecretKeySpec(keyBytes, "HmacSHA256");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT secret key is not a valid Base64 string", e);
        }
    }

    @Bean
    public SecretKey jwtSecretRefreshKey() {
        if (jwtSecretRefresh == null || jwtSecretRefresh.isEmpty()) {
            throw new IllegalArgumentException("jwtSecret cannot be null or empty");
        }

        try {
            byte[] keyBytes = Base64.getDecoder().decode(jwtSecretRefresh);
            // Cần đảm bảo key dài ít nhất 32 byte (256 bit) cho HMAC-SHA256
            if (keyBytes.length < 32) {
                throw new IllegalArgumentException("JWT secret key must be at least 32 bytes long after decoding.");
            }
            return new SecretKeySpec(keyBytes, "HmacSHA256");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT secret key is not a valid Base64 string", e);
        }
    }

    @Bean
    public JwtEncoder jwtAccessEncoder(@Qualifier("jwtSecretAccessKey") SecretKey secretKey) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
    }

    @Bean
    public JwtDecoder jwtAccessDecoder(@Qualifier("jwtSecretAccessKey") SecretKey secretKey) {
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    public JwtEncoder jwtRefreshEncoder(@Qualifier("jwtSecretRefreshKey") SecretKey secretKey) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
    }

    @Bean
    public JwtDecoder jwtRefreshDecoder(@Qualifier("jwtSecretRefreshKey") SecretKey secretKey) {
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }
}
