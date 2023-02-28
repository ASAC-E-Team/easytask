package com.easytask.easytask.src.user.dto.requestDto;

import com.easytask.easytask.src.user.entity.Role;
import com.easytask.easytask.src.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    private String email;
    private String password;
    private String name;

    private List<Map<String,String>> possibleTask; //큰카테고리 작은카테고리

    private List<Map<String,String>> taskAbility;

}
