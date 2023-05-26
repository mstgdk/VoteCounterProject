package com.votecounter.exception.message;

public class ErrorMessage {
    public final static String RESOURCE_NOT_FOUND_EXCEPTION = "Resource with id %s not found";
    public final static String JWTTOKEN_ERROR_MESSAGE = "JWT Token Validation Error: %s";
    public final static String ROLE_NOT_FOUND_EXCEPTION = "Role : %s not found";
    public final static String CITNUMBER_ALREADY_EXIST_MESSAGE = "CitNumber: %s already exist";
    public final static String USER_NOT_FOUND_EXCEPTION = "User with email: %s not found";
    public final static String IMAGE_NOT_FOUND_MESSAGE = "ImageFile with id %s not found";
    public final static String IMAGE_USED_MESSAGE = "ImageFile is used by another party";
}
