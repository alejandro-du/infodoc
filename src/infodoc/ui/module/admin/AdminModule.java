package infodoc.ui.module.admin;

import infodoc.core.InfodocConstants;
import infodoc.core.container.InfodocContainerFactory;
import infodoc.core.dto.PropertyValue;
import infodoc.core.dto.Case;
import infodoc.core.dto.AuditLog;
import infodoc.core.dto.JavaReport;
import infodoc.core.dto.HqlReport;
import infodoc.core.dto.NotificationInstance;
import infodoc.core.dto.ActivityInstance;
import infodoc.core.dto.User;
import infodoc.core.dto.UserGroup;
import infodoc.core.ui.common.InfodocModule;
import infodoc.core.ui.common.InfodocTheme;
import infodoc.core.ui.fieldfactory.InfodocFieldFactory;

import java.io.Serializable;
import java.util.Date;

import com.vaadin.data.Property;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import enterpriseapp.Utils;
import enterpriseapp.ui.FilesViewerComponent;
import enterpriseapp.ui.crud.CrudBuilder;
import enterpriseapp.ui.crud.CrudComponent;
import enterpriseapp.ui.crud.CrudTable;
import enterpriseapp.ui.reports.HqlQueryBrowser;
import enterpriseapp.ui.window.MDIWindow;

public class AdminModule extends InfodocModule implements Command, Serializable {
	
	private static final long serialVersionUID = 1L;
	private MenuItem userGroupsMenuItem;
	private MenuItem usersMenuItem;
	private MenuItem caseDtosMenuItem;
	private MenuItem propertyValuesMenuItem;
	private MenuItem activityInstancesMenuItem;
	private MenuItem notificationInstancesMenuItem;
	private MenuItem hqlQueryItem;
	private MenuItem javaReportsItem;
	private MenuItem hqlReportsItem;
	private MenuItem logMenuItem;
	private MenuItem auditLogMenuItem;
	private MDIWindow mdiWindow;
	private User user;

	@Override
	public void add(MDIWindow mdiWindow, enterpriseapp.hibernate.dto.User user) {
		this.mdiWindow = mdiWindow;
		this.user = (User) user;
		
		MenuItem adminMenuItem = mdiWindow.getMenuBar().addItem(InfodocConstants.uiAdmin, new ThemeResource(InfodocTheme.iconAdmin), null);
		
		userGroupsMenuItem = addMenuItem(adminMenuItem, InfodocConstants.uiUserGroups, new ThemeResource(InfodocTheme.iconUserGroup), this, this.user.getUserGroup().isAccessAdminUserGroups());
		usersMenuItem = addMenuItem(adminMenuItem, InfodocConstants.uiUsers, new ThemeResource(InfodocTheme.iconUser), this, this.user.getUserGroup().isAccessAdminUsers());
		
		addSeparator(adminMenuItem);
		caseDtosMenuItem = addMenuItem(adminMenuItem, InfodocConstants.uiCases, new ThemeResource(InfodocTheme.iconCases), this, this.user.getUserGroup().isAccessAdminCases());
		propertyValuesMenuItem = addMenuItem(adminMenuItem, InfodocConstants.uiPropertyValues, new ThemeResource(InfodocTheme.iconPropertyValue), this, this.user.getUserGroup().isAccessAdminPropertyValues());
		activityInstancesMenuItem = addMenuItem(adminMenuItem, InfodocConstants.uiActivitiesInstances, new ThemeResource(InfodocTheme.iconActivityInstance), this, this.user.getUserGroup().isAccessAdminActivityInstances());
		notificationInstancesMenuItem = addMenuItem(adminMenuItem, InfodocConstants.uiNotificationInstances, new ThemeResource(InfodocTheme.iconNotificationInstance), this, this.user.getUserGroup().isAccessAdminNotificationInstances());
		
		addSeparator(adminMenuItem);
		hqlQueryItem = addMenuItem(adminMenuItem, InfodocConstants.uiHqlQuery, new ThemeResource(InfodocTheme.iconDatabase), this, this.user.getUserGroup().isAccessHqlQuery());
		javaReportsItem = addMenuItem(adminMenuItem, InfodocConstants.uiJavaReports, new ThemeResource(InfodocTheme.iconReport), this, this.user.getUserGroup().isAccessAdminJavaReports());
		hqlReportsItem = addMenuItem(adminMenuItem, InfodocConstants.uiHqlReports, new ThemeResource(InfodocTheme.iconReport), this, this.user.getUserGroup().isAccessAdminHqlReports());
		
		addSeparator(adminMenuItem);
		logMenuItem = addMenuItem(adminMenuItem, InfodocConstants.uiLog, new ThemeResource(InfodocTheme.iconLog), this, this.user.getUserGroup().isAccessLogFiles());
		auditLogMenuItem = addMenuItem(adminMenuItem, InfodocConstants.uiAuditLog, new ThemeResource(InfodocTheme.iconAuditLog), this, this.user.getUserGroup().isAccessAuditLog());
		
		removeEndingSeparator(adminMenuItem);
	}

	@Override
	public boolean userCanAccess(enterpriseapp.hibernate.dto.User user) {
		return ((User) user).getUserGroup().isAccessAdminModule();
	}
	
	@Override
	public void menuSelected(MenuItem selectedItem) {
		if(selectedItem.equals(usersMenuItem)) {
			addUsersTab();
			
		} else if(selectedItem.equals(userGroupsMenuItem)) {
			addUserGroupsTab();
			
		} else if(selectedItem.equals(caseDtosMenuItem)) {
			addCasesTab();
			
		} else if(selectedItem.equals(propertyValuesMenuItem)) {
			addPropertyValuesTab();
			
		} else if(selectedItem.equals(activityInstancesMenuItem)) {
			addActivityInstancesTab();
			
		} else if(selectedItem.equals(notificationInstancesMenuItem)) {
			addNotificationInstancesTab();
			
		} else if(selectedItem.equals(hqlQueryItem)) {
			addHqlQueryTab();
			
		} else if(selectedItem.equals(javaReportsItem)) {
			addJavaReportsTab();
			
		} else if(selectedItem.equals(hqlReportsItem)) {
			addHqlReportsTab();
			
		} else if(selectedItem.equals(logMenuItem)) {
			addLogTab();
			
		} else if(selectedItem.equals(auditLogMenuItem)) {
			addAuditLogTab();
		}
	}
	
	private void addUsersTab() {
		CrudComponent<User> crud = new CrudBuilder<User>(User.class)
			.setFieldFactory(new InfodocFieldFactory())
			.setShowDeleteButton(user.getUserGroup().isCanCreateAndDeleteUsers())
			.setShowNewButton(user.getUserGroup().isCanCreateAndDeleteUsers())
			.build();
		
		crud.setReadOnly(!user.getUserGroup().isCanCreateAndDeleteUsers());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiUsers, null, true, false);
	}

	private void addUserGroupsTab() {
		CrudComponent<UserGroup> crud = new CrudComponent<UserGroup>(UserGroup.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiUserGroups, null, true, false);
	}

	private void addCasesTab() {
		CrudComponent<Case> crud = new CrudComponent<Case>(Case.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiCases, null, true, false);
	}

	private void addPropertyValuesTab() {
		CrudComponent<PropertyValue> crud = new CrudComponent<PropertyValue>(PropertyValue.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiPropertyValues, null, true, false);
	}

	private void addActivityInstancesTab() {
		CrudComponent<ActivityInstance> crud = new CrudComponent<ActivityInstance>(ActivityInstance.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiActivitiesInstances, null, true, false);
	}

	private void addNotificationInstancesTab() {
		CrudComponent<NotificationInstance> crud = new CrudComponent<NotificationInstance>(NotificationInstance.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiNotificationInstances, null, true, false);
	}

	private void addAuditLogTab() {
		CrudTable<AuditLog> table = new CrudTable<AuditLog>(AuditLog.class, InfodocContainerFactory.getAuditLogContainer(), new InfodocFieldFactory()) {
			private static final long serialVersionUID = 1L;
			@Override
			public String formatPropertyValue(Object rowId, Object colId, Property property) {
				
				if(colId.equals("date")) {
					return Utils.dateToString((Date) property.getValue(), Utils.getDateTimeFormatPattern());
				}
				
				return super.formatPropertyValue(rowId, colId, property);
			}
		};
		
		CrudComponent<AuditLog> crud = new CrudComponent<AuditLog>(AuditLog.class, InfodocContainerFactory.getAuditLogContainer(), new InfodocFieldFactory(), table, null, false, true, false, false, false, false, false, false, 0);
		crud.setSizeFull();
		crud.setReadOnly(true);
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiAuditLog, null, true, false);
	}

	private void addHqlQueryTab() {
		HqlQueryBrowser hqlQueryBrowser = new HqlQueryBrowser(new ThemeResource(InfodocTheme.iconDatabase), new ThemeResource(InfodocTheme.iconDatabaseTable), new ThemeResource(InfodocTheme.iconDatabaseColumn));
		mdiWindow.addWorkbenchContent(hqlQueryBrowser, InfodocConstants.uiHqlQuery, null, true, false);
	}

	private void addJavaReportsTab() {
		CrudComponent<JavaReport> crud = new CrudComponent<JavaReport>(JavaReport.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiJavaReports, null, true, false);
	}

	private void addHqlReportsTab() {
		CrudComponent<HqlReport> crud = new CrudComponent<HqlReport>(HqlReport.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiHqlReports, null, true, false);
	}

	private void addLogTab() {
		FilesViewerComponent filesViewerComponent = new FilesViewerComponent(Utils.getServerLogsDirectory());
		filesViewerComponent.setSizeFull();
		mdiWindow.addWorkbenchContent(filesViewerComponent, InfodocConstants.uiLog, null, true, false);
	}

}
