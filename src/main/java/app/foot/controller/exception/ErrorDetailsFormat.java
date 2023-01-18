package app.foot.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorDetailsFormat {
	private Date timestamp;
	private String message;
	private String details;
}
