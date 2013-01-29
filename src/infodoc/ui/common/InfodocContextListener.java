package infodoc.ui.common;

import infodoc.core.container.InfodocContainerFactory;

import java.util.TimeZone;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import enterpriseapp.DefaultContextListener;
import enterpriseapp.Utils;

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
		Utils.loadProperties("/infodoc-configuration.properties", getPassword());
		String language = Utils.getProperty("infodoc.language"); // do not use InfodocConstants here, as it has final static members
		
		if(language != null && !language.isEmpty()) {
			language = "-" + language;
		} else {
			language = "";
		}
		
		return new String[] {
			"/defaultConfiguration" + language + ".properties",
			"/infodoc-ui" + language + ".properties",
			"/infodoc-configuration.properties",
			"/infodoc-custom.properties"
		};
	}
	
}
