package com.easytask.easytask.src.user;

import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.common.jwt.JwtFilter;
import com.easytask.easytask.common.jwt.TokenProvider;
import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.src.user.dto.requestDto.TaskRequestDto;
import com.easytask.easytask.src.user.dto.requestDto.UserLoginDto;
import com.easytask.easytask.src.user.dto.requestDto.UserRequestDto;
import com.easytask.easytask.src.user.dto.responseDto.PossibleTaskResponseDto;
import com.easytask.easytask.src.user.dto.responseDto.TaskAbilityResponseDto;
import com.easytask.easytask.src.user.dto.responseDto.UserResponseDto;
import com.easytask.easytask.src.user.entity.User;
import com.easytask.easytask.src.user.login.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.easytask.easytask.common.jwt.JwtFilter.AUTHORIZATION_HEADER;
import static com.easytask.easytask.common.response.BaseResponseStatus.NOT_FIND_USER;
import static com.easytask.easytask.common.response.BaseResponseStatus.NOT_VALID_EMAIL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/easytask/user")
public class UserController {
    private final UserService userService;
    private final RedisUtil redisUtil;

    @PostMapping("/logout")
    public BaseResponse<String> logout(ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER).substring(7);
        redisUtil.setDataExpire(bearerToken,"logout",1800); //레디스에 userEmail(key), 토큰 값(value), 만료시간 설정 후 저장
        return new BaseResponse<>("로그아웃 되었습니다.");
    }

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody UserLoginDto loginDto) {
        String jwt = userService.login(loginDto);
        return new BaseResponse<>(jwt);
    }

    @PostMapping("/sign-up")
    public BaseResponse<UserResponseDto> registerUser(
            @Valid @RequestBody UserRequestDto requestDto){
        try{
            UserResponseDto userResponseDto = userService.registerUser(requestDto);
            return new BaseResponse<>(userResponseDto);
        } catch (Exception e){
            throw new BaseException(NOT_VALID_EMAIL);
        }
    }

    @PatchMapping("/{email}")
    public BaseResponse<String> updateUser(
            @Valid @RequestBody UserRequestDto requestDto, @PathVariable String email) {

        userService.updateUser(requestDto,email);
        return new BaseResponse<>("회원 정보를 변경하였습니다.");
    }

    @GetMapping("/{email}")
    public BaseResponse<UserResponseDto> getUser(@PathVariable String email){
        UserResponseDto userResponseDto = userService.getUser(email);
        return new BaseResponse<>(userResponseDto);
    }

    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public BaseResponse<List<UserResponseDto>> getAllUser(
            @PageableDefault(page = 0, size=10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable){
        List<UserResponseDto> userResponseDtoList = userService.getAllUser(pageable);
        return new BaseResponse<>(userResponseDtoList);
    }
}
