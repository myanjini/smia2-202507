package com.optimagrowth.license.model.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper {
    
    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("errors")
    private List<ErrorMessage> errors;

    public ResponseWrapper(Object metadata, List<ErrorMessage> errors) {
        this.metadata = metadata;
        this.errors = errors;
    }
}
