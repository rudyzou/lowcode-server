package com.hongshu.lowcode.designer.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.hongshu.boot.starter.api.Result;
import com.hongshu.boot.starter.controller.SuperControllerAdvance;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdvanceController extends SuperControllerAdvance {
    private Logger logger = LoggerFactory.getLogger(AdvanceController.class);
    @ExceptionHandler(value = NotLoginException.class)
    public ResponseEntity<Result> handleNotLoginException(Exception exception){
        logger.error("Failed to execute,handleException-not-login", exception.getMessage(),exception);
        return  new ResponseEntity(Result.buildFailure(ERROR_CODE_NOT_LOGIN, "Not login!"), HttpStatus.OK);
    }
}
