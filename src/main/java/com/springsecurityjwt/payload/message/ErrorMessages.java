
package com.springsecurityjwt.payload.message;

public class ErrorMessages {

    private ErrorMessages() {
    }
    public static final String  ALREADY_SEND_A_MESSAGE_TODAY= "Error: You have already sent a message today with this e-mail.";
    public static final String ALREADY_EXIST_BY_EMAIL="Error: This e-mail is already exist.";
    public static final String ALREADY_EXIST_BY_PHONE_NUMBER="Error: This phone number is already exist.";
    public static final String USER_ROLE_NOT_FOUND_BY_ROLE_TYPE="Error: User role not found by role type";
    public static final String USER_ROLE_ALREADY_EXIST ="Error: User role already exist by type %s";
    public static final String USER_NOT_FOUND ="Error: User isn't found by id : %s";
    public static final String IMAGE_FILE_NOT_FOUND ="Error: Image file isn't found by id : %s";

    public static final String IMAGE_FILE_COULD_NOT_DELETED = "Error : You can not delete default Image File %s";
    public static final String NOT_ALLOWED_DELETE_DEFAULT_USER = "Error : You can't delete default user %s";
}

