package eg.com.etisalat.contest.utility;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ExceptionHandlingActionListener extends ExceptionHandlerFactory {

	private final ExceptionHandlerFactory exceptionHandlerFactory;

	public ExceptionHandlingActionListener(ExceptionHandlerFactory exceptionHandlerFactory) {
		this.exceptionHandlerFactory = exceptionHandlerFactory;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler result = exceptionHandlerFactory.getExceptionHandler();
		result = new CustomExceptionHandler(result);
		return result;
	}
}