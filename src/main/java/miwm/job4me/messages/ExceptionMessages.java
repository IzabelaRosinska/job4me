package miwm.job4me.messages;

public class ExceptionMessages {
    public static String idCannotBeNull(String className) {
        return String.format("%s id can not be null", className);
    }

    public static String elementNotFound(String className, Long id) {
        return String.format("%s with id %d not found", className, id);
    }

    public static String unauthorized(String className) {
        return String.format("%s unauthorized", className);
    }

    public static String elementNotFoundByName(String className, String name) {
        return String.format("%s with name %s not found", className, name);
    }

    public static String elementNotFound(String className, String fieldName, String value) {
        return String.format("%s with %s %s not found", className, fieldName, value);
    }

    public static String nullArgument(String className) {
        return String.format("%s can not be null", className);
    }

    public static String textTooShort(String className, String fieldName, int minLength) {
        return String.format("%s %s is too short. Min text length is %d.", className, fieldName, minLength);
    }

    public static String textTooLong(String className, String fieldName, int maxLength) {
        return String.format("%s %s is too long. Max text length is %d.", className, fieldName, maxLength);
    }

    public static String lengthOutOfRange(String className, String fieldName, int minLength, int maxLength) {
        return String.format("%s %s length must be between %d and %d.", className, fieldName, minLength, maxLength);
    }

    public static String notNullNotEmpty(String className, String fieldName) {
        return String.format("%s %s can not be null or empty.", className, fieldName);
    }

    public static String notNull(String className, String fieldName) {
        return String.format("%s %s can not be null.", className, fieldName);
    }

    public static String invalidRange(String className, String fieldMinName, String fieldMaxName) {
        return String.format("%s %s must be lower than %s.", className, fieldMinName, fieldMaxName);
    }

    public static String mustBePositive(String className, String fieldName) {
        return String.format("%s %s must be positive.", className, fieldName);
    }

    public static String notNullNotBlank(String className, String fieldName) {
        return String.format("%s %s can not be null or blank.", className, fieldName);
    }

    public static String mustContain(String className, String fieldName, String mustContain) {
        return String.format("%s %s must contain %s.", className, fieldName, mustContain);
    }

    public static String mustMatchPattern(String className, String fieldName, String pattern) {
        return String.format("%s %s must match pattern %s.", className, fieldName, pattern);
    }

    public static String listTooLong(String className, String fieldName, int maxLength) {
        return String.format("%s %s is too long. Max list length is %d.", className, fieldName, maxLength);
    }

    public static String notBlank(String fieldName, String className) {
        return String.format("%s %s can not be blank.", className, fieldName);
    }

    public static String atLeastOne(String className, String fieldName) {
        return String.format("%s %s must contain at least one element.", className, fieldName);
    }

    public static String elementAlreadyExists(String className, String fieldName, String value) {
        return String.format("%s %s with given value %s already exists.", className, fieldName, value);
    }

    public static String idMustBeNullForCreate(String className) {
        return String.format("%s id must be null for create.", className);
    }

    public static String dateStartAfterDateEnd(String className, String dateStartFieldName, String dateEndFieldName) {
        return String.format("%s %s must be before %s.", className, dateStartFieldName, dateEndFieldName);
    }

    public static String dateFromPast(String className, String dateFieldName) {
        return String.format("%s %s must be from future.", className, dateFieldName);
    }
}
