package com.easytask.easytask.src.user;

import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.src.user.dto.requestDto.UserRequestDto;
import com.easytask.easytask.src.user.dto.responseDto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/easytask/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public BaseResponse<UserResponseDto> registerUser(
            @RequestBody UserRequestDto requestDto){
        UserResponseDto userResponseDto = userService.registerUser(requestDto);
        return new BaseResponse<>(userResponseDto);
    }
}
