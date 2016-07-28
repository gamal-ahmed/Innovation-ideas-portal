package eg.com.etisalat.contest.utility;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.log4j.Logger;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private final ExceptionHandler exceptionHandler;
	private static final Logger logger = Logger.getLogger(CustomExceptionHandler.class);

	public CustomExceptionHandler(ExceptionHandler exceptionHandler) {

		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public ExceptionHandler getWrapped() {

		return this.exceptionHandler;
	}

	@Override
	public void handle() throws FacesException {

		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {

			ExceptionQueuedEvent exceptionQueuedEvent = i.next();

			ExceptionQueuedEventContext exceptionQueuedEventContext = (ExceptionQueuedEventContext) exceptionQueuedEvent.getSource();

			Throwable throwable = exceptionQueuedEventContext.getException();

			if (throwable instanceof Throwable) {
				Throwable t = throwable;

				FacesContext facesContext = FacesContext.getCurrentInstance();

				NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
				try {
					facesContext.getExternalContext().getFlash().put("exceptioniNFO", t.getCause());
					logger.error("[Exception] !", t);
					navigationHandler.handleNavigation(facesContext, null, "/errorPage.xhtml?faces-redirect=true");
					facesContext.renderResponse();
				} finally {
					i.remove();

				}
			}

		}
		getWrapped().handle();
	}

}
