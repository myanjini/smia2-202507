package com.optimagrowth.license.controller;

import static java.util.Collections.singletonMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.optimagrowth.license.model.utils.ErrorMessage;
import com.optimagrowth.license.model.utils.ResponseWrapper;
import com.optimagrowth.license.model.utils.RestErrorList;
import jakarta.servlet.http.HttpServletRequest;

// @ControllerAdvice			Spring MVC의 전역 예외 처리기 등록
// ResponseEntityExceptionHandler	Spring의 기본 예외 처리 기능을 상속받아 사용 가능하게 함
@ControllerAdvice					
public class ExceptionController extends ResponseEntityExceptionHandler {
				
    // 모든 Exception에 대해 500 Internal Server Error 응답을 반환
    @ExceptionHandler(Exception.class)		
    public ResponseEntity<ResponseWrapper> handleException(HttpServletRequest request, Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                request.getRequestURI()
        );

        RestErrorList errorList = new RestErrorList(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        ResponseWrapper responseWrapper 
= new ResponseWrapper(singletonMap("status", HttpStatus.INTERNAL_SERVER_ERROR), errorList.getErrors());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseWrapper);
    }

    // RuntimeException이 발생하면 406 Not Acceptable 응답을 반환
    @ExceptionHandler(RuntimeException.class)	
    public ResponseEntity<ResponseWrapper> handleRuntimeException(HttpServletRequest request, RuntimeException e) {
        ErrorMessage errorMessage = new ErrorMessage(
                e.getMessage(),
                HttpStatus.NOT_ACCEPTABLE.value(),
                "Not Acceptable",
                request.getRequestURI()
        );

        RestErrorList errorList = new RestErrorList(HttpStatus.NOT_ACCEPTABLE, errorMessage);
        ResponseWrapper responseWrapper 
= new ResponseWrapper(singletonMap("status", HttpStatus.NOT_ACCEPTABLE), errorList.getErrors());

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseWrapper);
    }
}
