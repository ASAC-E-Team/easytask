package com.easytask.easytask.src.user;

import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.src.user.dto.requestDto.TaskRequestDto;
import com.easytask.easytask.src.user.dto.requestDto.UserRequestDto;
import com.easytask.easytask.src.user.dto.responseDto.PossibleTaskResponseDto;
import com.easytask.easytask.src.user.dto.responseDto.TaskAbilityResponseDto;
import com.easytask.easytask.src.user.dto.responseDto.UserResponseDto;
import com.easytask.easytask.src.user.entity.PossibleTask;
import com.easytask.easytask.src.user.entity.Role;
import com.easytask.easytask.src.user.entity.TaskAbility;
import com.easytask.easytask.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.easytask.easytask.common.response.BaseResponseStatus.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResponseDto registerUser(UserRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new BaseException(REGISTERED_USER);
        }
        User user = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .role(Role.ROLE_USER)
                .build();

        for(Map<String,String> a : requestDto.getPossibleTask()){
            PossibleTask possibleTask = PossibleTask.builder()
                    .user(user)
                    .categoryBig(a.keySet().iterator().next())
                    .categorySmall(a.get(a.keySet().iterator().next()))
                    .build();
            user.addPossibleTask(possibleTask);
        }
        for(Map<String,String> a : requestDto.getTaskAbility()){
            TaskAbility taskAbility = TaskAbility.builder()
                    .user(user)
                    .categoryBig(a.keySet().iterator().next())
                    .categorySmall(a.get(a.keySet().iterator().next()))
                    .build();
            user.addTaskAbility(taskAbility);
        }
        userRepository.save(user);

        return new UserResponseDto(user);


    }
    public void updateUser(UserRequestDto requestDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new BaseException(NOT_FIND_USER));
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.updateUser(requestDto);
    }


}

