package eg.com.etisalat;

/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ResourceBundle;

import javax.ejb.SessionContext;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public class Resources {

	// Expose an entity manager using the resource producer pattern
	@SuppressWarnings("unused")
	@PersistenceContext
	@Produces
	private EntityManager em;

	@Produces
	FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	@Produces
	Logger getLogger(InjectionPoint ip) {
		String category = ip.getMember().getDeclaringClass().getName();
		return Logger.getLogger(category);
	}

	@Produces
	ResourceBundle getResourceBundle() {
		return getFacesContext().getApplication().getResourceBundle(getFacesContext(), "messages");
	}

	@Produces
	SessionContext getSessionContext() {
		SessionContext sctxLookup = null;
		try {
			InitialContext ic = new InitialContext();
			sctxLookup = (SessionContext) ic.lookup("java:comp/EJBContext");
		} catch (NamingException ex) {
			throw new IllegalStateException(ex);
		}
		return sctxLookup;
	}

}
