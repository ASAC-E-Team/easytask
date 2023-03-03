package com.easytask.easytask.src.user.mapper;

import com.easytask.easytask.src.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findUserOnMatchingMailBatch();
}
