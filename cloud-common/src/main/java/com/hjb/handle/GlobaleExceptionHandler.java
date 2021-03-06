package com.hjb.handle;

import com.hjb.execption.BaseException;
import com.hjb.execption.auth.UserJwtException;
import com.hjb.execption.good.GoodsException;
import com.hjb.util.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobaleExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public Result baseExceptionHandler(BaseException e) {
        return Result.FAILURE(e.getMessage(),e.getCode());
    }

    @ExceptionHandler(Exception.class)
    public Result otherExceptionHandler(Exception e) {
        return Result.FAILURE(e.getMessage());
    }

    @ExceptionHandler(UserJwtException.class)
    public Result UserJwtExceptionHandler(UserJwtException e) {
        return Result.FAILURE(e.getMessage(),e.getCode());
    }

    @ExceptionHandler(GoodsException.class)
    public Result GoodsExceptionHandler(GoodsException e){
        return Result.FAILURE(e.getMessage(),e.getCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result MethodParamHandler(MethodArgumentNotValidException e){
        return Result.FAILURE(e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
