package com.caiofonseca.foundationapi.api.exceptions;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class ApiErrors {

    private List<String> errors;
    public ApiErrors(BindingResult bindingResults) {
        this.errors = new ArrayList<>();
        bindingResults.getAllErrors().forEach(error -> this.errors.add(error.getDefaultMessage()));
    }

    public List<String> getErrors() {
        return errors;
    }

}
