package com.cayuse.demo.exceptions;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteException extends RuntimeException implements GraphQLError {

    private Map<String, Object> extensions = new HashMap<>();

    public RemoteException(String message, Map<String, Object> ext) {
        super(message);

        if(ext != null){
            extensions.putAll(ext);
        }
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
