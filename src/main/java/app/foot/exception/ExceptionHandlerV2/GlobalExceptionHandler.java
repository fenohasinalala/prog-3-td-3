package app.foot.exception.ExceptionHandlerV2;

import app.foot.exception.BadRequestException;
import app.foot.exception.NotFoundException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// handling specific exception
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDetailsFormat> NotFoundExceptionHandling(NotFoundException exception, WebRequest request){
		if (AnnotationUtils.findAnnotation
				(exception.getClass(), ResponseStatus.class) != null)
			throw exception;
		ErrorDetailsFormat errorDetailsFormat =
				new ErrorDetailsFormat(new Date(), exception.getMessage(), request.getDescription(true));

		return new ResponseEntity<>(errorDetailsFormat, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDetailsFormat> BadRequestExceptionHandling(BadRequestException exception, WebRequest request){
		if (AnnotationUtils.findAnnotation
				(exception.getClass(), ResponseStatus.class) != null)
			throw exception;
		ErrorDetailsFormat errorDetailsFormat =
				new ErrorDetailsFormat(new Date(), exception.getMessage(), request.getDescription(true));
		return new ResponseEntity<>(errorDetailsFormat, HttpStatus.BAD_REQUEST);
	}

	// handling global exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetailsFormat> GlobalExceptionHandling(Exception exception, WebRequest request) throws Exception {
		if (AnnotationUtils.findAnnotation
				(exception.getClass(), ResponseStatus.class) != null)
			throw exception;
		ErrorDetailsFormat errorDetailsFormat =
				new ErrorDetailsFormat(new Date(), exception.getMessage(), request.getDescription(true));
		return new ResponseEntity<>(errorDetailsFormat, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
