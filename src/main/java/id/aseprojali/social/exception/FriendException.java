package id.aseprojali.social.exception;

/**
 * Created by avew on 9/13/17.
 */
public class FriendException extends Exception {

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }


    public FriendException(String message) {
        super(message);
        this.message = message;
    }

    public FriendException() {
        super();
    }
}
