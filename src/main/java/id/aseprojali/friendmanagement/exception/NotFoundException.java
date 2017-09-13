package id.aseprojali.friendmanagement.exception;

/**
 * Created by avew on 9/13/17.
 */
public class NotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }


    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public NotFoundException() {
        super();
    }
}
