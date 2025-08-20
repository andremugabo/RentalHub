package rw.mugaboandre.rentalhub.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LOCKED)
public class ResourceLockedException extends RuntimeException {
    public ResourceLockedException(String message) {
        super(message);
    }
}