package graduation.alcoholic.web.login.dto;

import graduation.alcoholic.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {
    private Long id;
    private String name;
    private String nickname;
    private RoleType roletype;
    private String email;
    private String age_range;
    private String sex;

    private String JwtToken;//jwt토큰
    private Boolean isNewMember;
}