package graduation.alcoholic.login.domain.member;

import graduation.alcoholic.login.domain.auth.enumerate.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private String sex;
    private String age_range;
    private BigDecimal capacity;
    private RoleType roletype;

    @Builder
    public UserDto(String name, String email, String age_range, BigDecimal capacity, String sex, RoleType roletype) {
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.age_range = age_range;
        this.capacity = capacity;
        this.roletype = roletype;
    }
}