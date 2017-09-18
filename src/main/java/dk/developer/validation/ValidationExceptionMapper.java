package dk.developer.validation;

import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ResteasyViolationException;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException exception) {
        String message = message(exception);
        return Response.status(BAD_REQUEST).entity(message).build();
    }

    private String message(ValidationException exception) {
        if (exception instanceof ResteasyViolationException ) {
            return convertResteasyViolationToException((ResteasyViolationException) exception);
        } else {
            return exception.getMessage();
        }
    }

    private String convertResteasyViolationToException(ResteasyViolationException exception) {
        return exception.getViolations().stream()
                .map(ResteasyConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));
    }
}
