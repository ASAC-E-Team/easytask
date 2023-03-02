package com.easytask.easytask.src.user.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TaskRequestDto {

    private Map<String,String> updateTask = new HashMap<>(); //큰카테고리 작은카테고리

}
