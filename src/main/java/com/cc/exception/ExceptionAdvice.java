package com.cc.exception;

import com.cc.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response exception(Exception e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(-1000, "오류가 발생하였습니다.");
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response invalidTokenException(InvalidTokenException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(-1001, "유효하지 않은 토큰입니다.");
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response memberNotFoundException(MemberNotFoundException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(-1002, "사용자를 찾을 수 없습니다.");
    }

    @ExceptionHandler(MemberCustomIdAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response memberCustomIdAlreadyExistsException(MemberCustomIdAlreadyExistsException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(-1003, "이미 사용 중인 아이디입니다.");
    }

    @ExceptionHandler(MemberEmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response memberEmailAlreadyExistsException(MemberEmailAlreadyExistsException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(-1004, "이미 사용 중인 이메일입니다.");
    }

    @ExceptionHandler(MemberPhoneAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response memberPhoneAlreadyExistsException(MemberPhoneAlreadyExistsException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(-1005, "이미 사용 중인 전화번호입니다.");
    }

    @ExceptionHandler(InvalidPasswordFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response invalidPasswordFormatException(InvalidPasswordFormatException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(-1006, "비밀번호 형식이 올바르지 않습니다.");
    }

    @ExceptionHandler(FailedToSendEmailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response failedToSendEmailException(FailedToSendEmailException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(-1007, "이메일 전송에 실패하였습니다.");
    }

    @ExceptionHandler(ExpiredVerifyCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response expiredVerifyCodeException(ExpiredVerifyCodeException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(-1008, "인증코드가 만료되었습니다.");
    }

    @ExceptionHandler(WrongVerifyCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response wrongVerifyCodeException(WrongVerifyCodeException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(-1009, "인증코드가 일치하지 않습니다.");
    }
}
