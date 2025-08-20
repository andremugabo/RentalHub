package rw.mugaboandre.rentalhub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;



@Getter
@AllArgsConstructor
public class RentalHubAPIException extends RuntimeException {
    private HttpStatus status;
    private String message;
}
