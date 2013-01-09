package infodoc.ui.common;

import infodoc.core.container.InfodocContainerFactory;

import java.util.TimeZone;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import enterpriseapp.DefaultContextListener;

public class InfodocContextListener extends DefaultContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(InfodocContainerFactory.class);

	@Override
    public void contextInitialized(ServletContextEvent contextEvent) {
		super.contextInitialized(contextEvent);
		logger.info("Time zone: " + TimeZone.getDefault().getDisplayName() + " (" + TimeZone.getDefault().getID() + " " + TimeZone.getDefault().getRawOffset() + ")");
		
		InfodocContainerFactory.getNotificationInstanceContainer().schedulePendingInstances();
    }
	
	@Override
	public String[] getPropertiesFileNames() {
		return new String[] {
			"/defaultConfiguration.properties",
			"/infodoc-ui.properties",
			"/infodoc-configuration.properties",
			"/infodoc-custom.properties"
		};
	}
	
}
