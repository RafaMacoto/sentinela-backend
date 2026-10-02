package com.sentinela.exception;

import com.sentinela.exception.account.AccountAlreadyExistsException;
import com.sentinela.exception.account.AccountNotFoundException;
import com.sentinela.exception.customer.CustomerAlreadyExistsException;
import com.sentinela.exception.customer.CustomerNotFoundException;
import com.sentinela.exception.device.DeviceAlreadyExistsException;
import com.sentinela.exception.device.DeviceNotFoundException;
import com.sentinela.exception.fraudanalysis.FraudAnalysisNotFoundException;
import com.sentinela.exception.transaction.TransactionNotFoundException;
import com.sentinela.exception.transaction.TransactionRelationshipException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler({
			CustomerNotFoundException.class,
			AccountNotFoundException.class,
			DeviceNotFoundException.class,
			TransactionNotFoundException.class,
			FraudAnalysisNotFoundException.class
	})
	public ProblemDetail handleNotFound(RuntimeException exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
	}

	@ExceptionHandler({
			CustomerAlreadyExistsException.class,
			AccountAlreadyExistsException.class,
			DeviceAlreadyExistsException.class,
			DataIntegrityViolationException.class
	})
	public ProblemDetail handleConflict(Exception exception) {
		String detail = exception instanceof DataIntegrityViolationException
				? "The request conflicts with existing data."
				: exception.getMessage();
		return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, detail);
	}

	@ExceptionHandler(TransactionRelationshipException.class)
	public ProblemDetail handleInvalidTransactionRelationship(TransactionRelationshipException exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
	public ProblemDetail handleInvalidRequest(Exception exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "The request is invalid.");
	}
}
