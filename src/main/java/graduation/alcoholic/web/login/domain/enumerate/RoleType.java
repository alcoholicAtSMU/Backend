package graduation.alcoholic.web.login.domain.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {
    ROLE_USER("USER", "일반 사용자 권한"),
    ROLE_ADMIN("ADMIN", "관리자 권한"),
    NONE("NONE", "권한 없음");

    private final String code;
    private final String name;

    public static RoleType of(String code) {
        return Arrays.stream(RoleType.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(NONE);
    }
}