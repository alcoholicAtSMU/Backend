package graduation.alcoholic.web.login.domain.jwt;

import graduation.alcoholic.domain.enums.RoleType;
import graduation.alcoholic.web.login.domain.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AuthTokenProvider {

    @Value("${app.auth.tokenExpiry}")
    private String expiry;

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(@Value("${app.auth.tokenSecret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    public AuthToken createToken(Long id, RoleType roleType,String expiry) {
        Date expiryDate = getExpiryDate(expiry);
        return new AuthToken(id,roleType, expiryDate, key);
    }

    //USER에 대한 jwtToken생성
    public AuthToken createUserAppToken(Long id) {
        return createToken(id,RoleType.USER, expiry);
    }

    //String to jwtToken
    public AuthToken convertAuthToken(String token) {
        AuthToken call=new AuthToken(token, key);
        return call;
    }

    //토큰 만료 시간 설정
    public static Date getExpiryDate(String expiry) {
        return new Date(System.currentTimeMillis() + Long.parseLong(expiry));
    }

    public Authentication getAuthentication(String jwtToken,AuthToken authToken) {
        //권한 인증되면
        if(authToken.validate(jwtToken)) {
            Claims claims = authToken.getTokenClaims();
            //권한 부여
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            User principal = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }
}