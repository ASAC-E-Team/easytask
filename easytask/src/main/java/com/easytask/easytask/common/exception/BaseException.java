package com.easytask.easytask.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {
    public BaseException(String message) { super(message); }
}