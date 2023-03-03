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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import javax.validation.Valid;

import java.util.List;

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
    public BaseResponse<String> login(@RequestBody UserLoginDto loginDto) {
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
