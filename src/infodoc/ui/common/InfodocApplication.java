package infodoc.ui.common;

import infodoc.core.InfodocConstants;
import infodoc.core.container.InfodocContainerFactory;
import infodoc.core.dto.User;
import infodoc.core.ui.common.InfodocMainWindow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import enterpriseapp.EnterpriseApplication;
import enterpriseapp.hibernate.ContainerFactory;
import enterpriseapp.ui.window.Module;

public class InfodocApplication extends EnterpriseApplication implements Serializable {
	
	private static final String BUILT_IN_MODULES = "infodoc.ui.module.configuration.ConfigurationModule,infodoc.ui.module.admin.AdminModule,infodoc.ui.module.help.HelpModule";

	private static final long serialVersionUID = 1L;
	
	private static boolean firstInit = true;
	
	@Override
	public void init() {
		super.init();
		
		ContainerFactory.init(new InfodocContainerFactory());
		setTheme("infodoc");
		
		User user = (User) getUser();
		
		if(user != null && user.getTimeZone() != null) {
			setTimeZoneId(user.getTimeZone());
		}
		
		InfodocMainWindow mainWindow = new InfodocMainWindow((User) getUser(), getModules((User) getUser()));
		setMainWindow(mainWindow);
		
		if(firstInit) {
			Bootstrap.run();
			firstInit = false;
		}
	}

	public List<Module> getModules(User user) {
		ArrayList<Module> modules = new ArrayList<Module>();
		String modulesString = BUILT_IN_MODULES;
		
		if(InfodocConstants.infodocModules != null && !InfodocConstants.infodocModules.isEmpty()) {
			modulesString = InfodocConstants.infodocModules + "," + modulesString.replace("" , "");
		}
		
		String[] moduleClasses = modulesString.split(",");
		
		for(String moduleClass : moduleClasses) {
			try {
				Module module = (Module) Class.forName(moduleClass).newInstance();
				modules.add(module);
				
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(InfodocConstants.uiErrorModuleNotFound + " " + moduleClass, e);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return modules;
	}
	
}
