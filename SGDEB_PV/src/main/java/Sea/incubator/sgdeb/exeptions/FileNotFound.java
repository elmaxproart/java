package Sea.incubator.sgdeb.exeptions;

public class FileNotFound extends RuntimeException {
    public FileNotFound(String message) {
        super(message);
    }
}
