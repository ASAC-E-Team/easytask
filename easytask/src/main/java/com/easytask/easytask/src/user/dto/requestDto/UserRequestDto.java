package com.easytask.easytask.src.user.dto.requestDto;

import com.easytask.easytask.src.user.entity.Role;
import com.easytask.easytask.src.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
    private String password;
    private String name;

    private List<Map<String,String>> possibleTask; //큰카테고리 작은카테고리

    private List<Map<String,String>> taskAbility;

}
