package com.cayuse.demo.exceptions;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityNotFound extends RuntimeException implements GraphQLError {

    private Map<String, Object> extensions = new HashMap<>();

    public CityNotFound(String message, String zipCode) {
        super(message);
        extensions.put("invalid_zip_code", zipCode);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }
}
