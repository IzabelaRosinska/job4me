package miwm.job4me.messages;

public class ExceptionMessages {
    public static String idCannotBeNull(String className) {
        return String.format("%s id can not be null", className);
    }

    public static String elementNotFound(String className, Long id) {
        return String.format("%s with id %d not found", className, id);
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

    public static String notNullNotEmpty(String className, String fieldName) {
        return String.format("%s %s can not be null or empty.", className, fieldName);
    }

    public static String mustContain(String className, String fieldName, String mustContain) {
        return String.format("%s %s must contain %s.", className, fieldName, mustContain);
    }

    public static String listTooLong(String className, String fieldName, int maxLength) {
        return String.format("%s %s is too long. Max list length is %d.", className, fieldName, maxLength);
    }
}
