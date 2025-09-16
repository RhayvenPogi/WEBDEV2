package com.rhayven.prelim.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles application-wide exceptions for employee operations.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles cases where an employee resource is not found.
     *
     * @param ex    the thrown exception
     * @param model model to pass error message
     * @return error view
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("message", "The employee ID you’re trying to access does not exist.");
        return "error";
    }

    /**
     * Handles all other unexpected exceptions.
     *
     * @param ex    the thrown exception
     * @param model model to pass error message
     * @return error view
     */
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("message", "An unexpected error occurred while processing the employee information.");
        return "error";
    }
}
