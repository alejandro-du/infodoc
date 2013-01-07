package infodoc.ui.auth;

import infodoc.core.ui.auth.AuthService;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.LoggerFactory;

import enterpriseapp.hibernate.dto.User;


public class SessionListener implements HttpSessionListener {

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event) {
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event) {
    	User user = (User) event.getSession().getAttribute("user");
    	AuthService.getLoggedUserApps().get(user);
    	LoggerFactory.getLogger(SessionListener.class).debug("Session destroyed (user id = " + user == null ? "null" : user.getId() + ")");
    }
	
}
