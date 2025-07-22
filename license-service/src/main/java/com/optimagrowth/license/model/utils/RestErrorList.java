package com.optimagrowth.license.model.utils;

import static java.util.Arrays.asList;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestErrorList extends ArrayList<ErrorMessage> {
    private static final long serialVersionUID = -721424777198115589L;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("errors")
    private ArrayList<ErrorMessage> errors;

    public RestErrorList(HttpStatus status, ErrorMessage... errors) {
        this.status = status;
        this.errors = new ArrayList<>(asList(errors));
    }
}
