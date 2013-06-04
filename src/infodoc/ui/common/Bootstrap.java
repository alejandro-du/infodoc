package infodoc.ui.common;

import infodoc.core.InfodocConstants;
import infodoc.core.container.InfodocContainerFactory;
import infodoc.core.dto.User;
import infodoc.core.dto.UserGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import enterpriseapp.hibernate.ContainerFactory;

public class Bootstrap {
	
	private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	
	public static void run() {
		logger.info("Initializing container factory...");
		ContainerFactory.init(new InfodocContainerFactory());
		
		logger.info("Initializing modules...");
		InfodocApplication.initializeModules();
		
		if(InfodocContainerFactory.getUserGroupContainer().count() == 0) {
			LoggerFactory.getLogger(Bootstrap.class).info("Creating user groups...");
			createUserGroups();
		}
		
		if(InfodocContainerFactory.getUserContainer().count() == 0) {
			LoggerFactory.getLogger(Bootstrap.class).info("Creating users...");
			createUsers();
		}
		
		logger.info("Scheduling pending notification instances...");
		InfodocContainerFactory.getNotificationInstanceContainer().schedulePendingInstances();
	}
	
	protected static void createUserGroups() {
		UserGroup userGroup = new UserGroup();
		userGroup.setName(InfodocConstants.uiDefaultUserGroupName);
		
		userGroup.setAccessAdminModule(true);
		userGroup.setAccessAdminUserGroups(true);
		userGroup.setAccessAdminUsers(true);
		userGroup.setCanCreateAndDeleteUsers(true);
		userGroup.setAccessLogFiles(true);
		userGroup.setAccessAuditLog(true);
		
		userGroup.setAccesoConfigModule(true);
		userGroup.setAccessConfigForms(true);
		userGroup.setAccessConfigNumeration(true);
		userGroup.setAccessConfigProperties(true);
		userGroup.setAccessConfigValidations(true);
		userGroup.setAccessConfigClassifications(true);
		userGroup.setAccessConfigClassificationValues(true);
		userGroup.setAccessConfigActivities(true);
		userGroup.setAccessConfigNotifications(true);
		
		userGroup.setAccessBasicModule(true);
		userGroup.setAccessLastActivityInstances(true);
		
		InfodocContainerFactory.getUserGroupContainer().saveOrUpdateEntity(userGroup);
	}

	protected static void createUsers() {
		User user = new User();
		user.setLogin("admin@test.com");
		user.setPassword("test");
		user.setDisabled(false);
		user.setExpirePassword(false);
		user.setName("test");
		user.setLastName("test");
		user.setUserGroup(InfodocContainerFactory.getUserGroupContainer().listAll().get(0));
		
		InfodocContainerFactory.getUserContainer().saveEntity(user);
	}

}
