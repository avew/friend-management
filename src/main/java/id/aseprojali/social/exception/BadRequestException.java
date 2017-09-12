package id.aseprojali.social.exception;

/**
 * Created by avew on 9/13/17.
 */
public class BadRequestException extends Exception {

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }


    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public BadRequestException() {
        super();
    }
}
