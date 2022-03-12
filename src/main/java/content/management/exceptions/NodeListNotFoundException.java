package content.management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NodeListNotFoundException extends Exception {
    public NodeListNotFoundException() {
        super("List Not Found");
    }
}
