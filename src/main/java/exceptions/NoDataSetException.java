package exceptions;

public class NoDataSetException extends RuntimeException {
    public NoDataSetException() {
        super("No data was set before building. Please set at least one field.");
    }

    public NoDataSetException(String message){
        super(message);
    }
}
