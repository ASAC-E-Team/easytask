package com.easytask.easytask.src.user.dto.requestDto;

import com.easytask.easytask.src.user.entity.Role;
import com.easytask.easytask.src.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
    @NotNull(message = "비밀번호를 입력해 주세요")
    private String password;
    private String name;

    private List<Map<String,String>> possibleTask; //큰카테고리 작은카테고리

    private List<Map<String,String>> taskAbility;

}
