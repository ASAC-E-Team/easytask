package com.easytask.easytask.src.user;

import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.common.jwt.JwtFilter;
import com.easytask.easytask.common.jwt.TokenProvider;
import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.src.user.dto.requestDto.UserLoginDto;
import com.easytask.easytask.src.user.dto.requestDto.UserRequestDto;
import com.easytask.easytask.src.user.dto.responseDto.UserResponseDto;
import com.easytask.easytask.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.easytask.easytask.common.response.BaseResponseStatus.NOT_FIND_USER;
import static com.easytask.easytask.common.response.BaseResponseStatus.NOT_VALID_EMAIL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/easytask/user")
public class UserController {
    private final UserService userService;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/login")
    public BaseResponse<String> login(@Valid @RequestBody UserLoginDto loginDto) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);


            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.createToken(authentication);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

            System.out.println("jwt = " + jwt);
            return new BaseResponse<>(jwt);

        } catch (Exception e){
            throw new BaseException(NOT_FIND_USER);
        }

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

    //권한 테스트(지울 예정)
    @GetMapping("/{email}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public BaseResponse<String> getUserInfo1(@PathVariable String email) {
        System.out.println("ROLE_USER = " + email);

        return new BaseResponse<>(email);
    }

    //권한 테스트(지울 예정)
    @GetMapping("/admin/{email}")
    @Secured("ROLE_ADMIN")
    public BaseResponse<String> getUserInfo2(@PathVariable String email) {
        System.out.println("ROLE_ADMIN = " + email);

        return new BaseResponse<>(email);
    }
}
