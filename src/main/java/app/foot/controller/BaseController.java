package app.foot.controller;
import app.foot.exception.BadRequestException;
import app.foot.controller.rest.RestException;
import app.foot.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class BaseController {

  private RestException toRest(java.lang.Exception e, HttpStatus status) {
    var restException = new RestException();
    restException.setType(status.toString());
    restException.setMessage(e.getMessage());
    return restException;
  }

  @ExceptionHandler(value = {BadRequestException.class})
  ResponseEntity<RestException> handleBadRequest(
      BadRequestException e) {
    log.info("Bad request", e);

    return new ResponseEntity<>(toRest(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);

  }

  @ExceptionHandler(value = {MissingServletRequestParameterException.class})
  ResponseEntity<RestException> handleBadRequest(
      MissingServletRequestParameterException e) {
    log.info("Missing parameter", e);
    return handleBadRequest(new BadRequestException(e.getMessage()));
  }

  @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
  ResponseEntity<RestException> handleConversionFailed(
      MethodArgumentTypeMismatchException e) {
    log.info("Conversion failed", e);
    String message = e.getCause().getCause().getMessage();
    return handleBadRequest(new BadRequestException(message));
  }




  @ExceptionHandler(value = {NotFoundException.class})
  ResponseEntity<RestException> handleNotFound(
      NotFoundException e) {
    log.info("Not found", e);
    return new ResponseEntity<>(toRest(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(value = {java.lang.Exception.class})
  ResponseEntity<RestException> handleDefault(java.lang.Exception e) {
    log.error("Internal error", e);
    return new ResponseEntity<>(
        toRest(e, HttpStatus.INTERNAL_SERVER_ERROR),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }


}
