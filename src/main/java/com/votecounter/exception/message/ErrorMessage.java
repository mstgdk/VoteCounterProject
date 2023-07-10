package com.votecounter.exception.message;

public class ErrorMessage {
    public final static String RESOURCE_NOT_FOUND_EXCEPTION = "Resource with id %s not found";
    public final static String ALLIANCE_NOT_FOUND_EXCEPTION = "Party with name %s has no Alliance";
    public final static String JWTTOKEN_ERROR_MESSAGE = "JWT Token Validation Error: %s";
    public final static String ROLE_NOT_FOUND_EXCEPTION = "Role : %s not found";
    public final static String CITNUMBER_ALREADY_EXIST_MESSAGE = "CitNumber: %s already exist";
    public final static String USER_NOT_FOUND_EXCEPTION = "User with email: %s not found";
    public final static String IMAGE_NOT_FOUND_MESSAGE = "ImageFile with id %s not found";
    public final static String IMAGE_USED_MESSAGE = "ImageFile is used by another party";
    public final static String THIS_ALLIANCE_NAME_ALREADY_EXIST_MESSAGE = "This Alliance Name is used by another alliance";
    public final static String PARTY_AND_CANDIDATE_CONFLICT_EXCEPTION = "This Party with id %s has already a CANDIDATE. You can't be a candidate of this party..";
}
