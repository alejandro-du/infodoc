package infodoc.ui.comun;

import infodoc.core.InfodocConstants;
import infodoc.core.container.InfodocContainerFactory;
import infodoc.core.dto.UserGroup;
import infodoc.core.dto.User;

import org.slf4j.LoggerFactory;

public class Bootstrap {
	
	public static void run() {
		if(InfodocContainerFactory.getUserGroupContainer().count() == 0) {
			LoggerFactory.getLogger(Bootstrap.class).info("Creating user grops...");
			createUserGroups();
			LoggerFactory.getLogger(Bootstrap.class).info("User groups created.");
		}
		if(InfodocContainerFactory.getUserContainer().count() == 0) {
			LoggerFactory.getLogger(Bootstrap.class).info("Creating users...");
			createUsers();
			LoggerFactory.getLogger(Bootstrap.class).info("Users created.");
		}
	}
	
	protected static void createUserGroups() {
		UserGroup userGroup = new UserGroup();
		userGroup.setName(InfodocConstants.uiDefaultUserGroupName);
		
		userGroup.setAccessAdminModule(true);
		userGroup.setAccessAdminUserGroups(true);
		userGroup.setAccessAdminUsers(true);
		userGroup.setCanCreateAndModifyUsers(true);
		userGroup.setAccessLogFiles(true);
		userGroup.setAccessAuditLog(true);
		
		userGroup.setAccesoConfigModule(true);
		userGroup.setAccessConfigProcesses(true);
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
		user.setLogin("test@test.test");
		user.setPassword("test");
		user.setDisabled(false);
		user.setName("test");
		user.setLastName("test");
		user.setUserGroup(InfodocContainerFactory.getUserGroupContainer().listAll().get(0));
		
		InfodocContainerFactory.getUserContainer().saveEntity(user);
	}

}
