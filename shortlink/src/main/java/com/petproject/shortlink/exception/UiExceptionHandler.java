package com.petproject.shortlink.exception;

import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(
        basePackages = "com.petproject.shortlink.controller.web"
)
public class UiExceptionHandler {

    @ExceptionHandler(LinkNotFoundException.class)
    public ModelAndView handleLinkNotFound(
            LinkNotFoundException exception
    ) {
        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        modelAndView.addObject("status", 404);
        modelAndView.addObject("message", exception.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(BindException.class)
    public String handleValidationException(
            BindException exception,
            Model model
    ) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Validation failed");

        model.addAttribute("status", 400);
        model.addAttribute("message", message);

        return "error";
    }
}