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

    public static String textTooLong(String className, String fieldName, int maxLength) {
        return String.format("%s %s is too long. Max text length is %d.", className, fieldName, maxLength);
    }
}
