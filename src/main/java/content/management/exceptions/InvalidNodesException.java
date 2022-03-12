package content.management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidNodesException extends Exception {
    public InvalidNodesException() {
        super("Invalid Nodes In The List");
    }
}
