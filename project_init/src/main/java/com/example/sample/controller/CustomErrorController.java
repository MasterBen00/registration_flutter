//package com.example.sample.controller;
//
//import com.example.sample.exception.AppError;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@RestController
//public class CustomErrorController implements ErrorController {
//
//    private static final String PATH = "/error";
//
//    @RequestMapping(PATH)
//    public ResponseEntity<AppError> handleError(final HttpServletRequest request,
//                                                final HttpServletResponse response) throws Throwable {
//        throw (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
//    }
//
//    @Override
//    public String getErrorPath() {
//        return PATH;
//    }
//}