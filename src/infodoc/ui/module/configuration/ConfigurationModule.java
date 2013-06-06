package infodoc.ui.module.configuration;

import java.io.Serializable;

import infodoc.core.InfodocConstants;
import infodoc.core.dto.Activity;
import infodoc.core.dto.Classification;
import infodoc.core.dto.ClassificationValue;
import infodoc.core.dto.Form;
import infodoc.core.dto.Notification;
import infodoc.core.dto.Numeration;
import infodoc.core.dto.Property;
import infodoc.core.dto.User;
import infodoc.core.dto.Validation;
import infodoc.core.ui.common.InfodocModule;
import infodoc.core.ui.common.InfodocTheme;
import infodoc.core.ui.fieldfactory.InfodocFieldFactory;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import enterpriseapp.ui.crud.CrudComponent;
import enterpriseapp.ui.window.MDIWindow;

public class ConfigurationModule extends InfodocModule implements Command, Serializable {
	
	private static final long serialVersionUID = 1L;
	private MenuItem numerationsMenuItem;
	private MenuItem formsMenuItem;
	private MenuItem propertiesMenuItem;
	private MenuItem validationsMenuItem;
	private MenuItem classificationsMenuItem;
	private MenuItem classificationValuesMenuItem;
	private MenuItem activitiesMenuItem;
	private MenuItem notificationsMenuItem;
	private MDIWindow mdiWindow;

	@Override
	public void add(MDIWindow mdiWindow, enterpriseapp.hibernate.dto.User user) {
		this.mdiWindow = mdiWindow;
		User theUser = (User) user;
		
		MenuItem configurationMenuItem = mdiWindow.getMenuBar().addItem(InfodocConstants.uiConfiguration, new ThemeResource(InfodocTheme.iconConfiguration), null);
		
		numerationsMenuItem = addMenuItem(configurationMenuItem, InfodocConstants.uiNumerations, new ThemeResource(InfodocTheme.iconNumeration), this, theUser.getUserGroup().isAccessConfigNumeration());
		formsMenuItem = addMenuItem(configurationMenuItem, InfodocConstants.uiForms, new ThemeResource(InfodocTheme.iconForm), this, theUser.getUserGroup().isAccessConfigForms());
		propertiesMenuItem = addMenuItem(configurationMenuItem, InfodocConstants.uiProperties, new ThemeResource(InfodocTheme.iconProperty), this, theUser.getUserGroup().isAccessConfigProperties());
		validationsMenuItem = addMenuItem(configurationMenuItem, InfodocConstants.uiValidations, new ThemeResource(InfodocTheme.iconValidationInstance), this, theUser.getUserGroup().isAccessConfigValidations());
		
		configurationMenuItem.addSeparator();
		classificationsMenuItem = addMenuItem(configurationMenuItem, InfodocConstants.uiClassifications, new ThemeResource(InfodocTheme.iconClassification), this, theUser.getUserGroup().isAccessConfigClassifications());
		classificationValuesMenuItem = addMenuItem(configurationMenuItem, InfodocConstants.uiClassificationValues, new ThemeResource(InfodocTheme.iconClassificationInstance), this, theUser.getUserGroup().isAccessConfigClassificationValues());
		
		configurationMenuItem.addSeparator();
		activitiesMenuItem = addMenuItem(configurationMenuItem, InfodocConstants.uiActivities, new ThemeResource(InfodocTheme.iconActivity), this, theUser.getUserGroup().isAccessConfigActivities());
		notificationsMenuItem = addMenuItem(configurationMenuItem, InfodocConstants.uiNotifications, new ThemeResource(InfodocTheme.iconNotification), this, theUser.getUserGroup().isAccessConfigNotifications());
	}

	@Override
	public boolean userCanAccess(enterpriseapp.hibernate.dto.User user) {
		return ((User) user).getUserGroup().isAccesoConfigModule();
	}
	
	@Override
	public void menuSelected(MenuItem selectedItem) {
		if(selectedItem.equals(numerationsMenuItem)) {
			addNumerationsTab();
		
		} else if(selectedItem.equals(formsMenuItem)) {
			addFormsTab();
			
		} else if(selectedItem.equals(propertiesMenuItem)) {
			addPropertiesTab();
			
		} else if(selectedItem.equals(validationsMenuItem)) {
			addValidationsTab();
			
		} else if(selectedItem.equals(classificationsMenuItem)) {
			addClassificationsTab();
			
		} else if(selectedItem.equals(classificationValuesMenuItem)) {
			addClassificationValuesTab();
			
		} else if(selectedItem.equals(activitiesMenuItem)) {
			addActivitiesTab();
			
		} else if(selectedItem.equals(notificationsMenuItem)) {
			addNotificationsTab();
		}
	}
	
	private void addFormsTab() {
		CrudComponent<Form> crud = new CrudComponent<Form>(Form.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiForms, null, true, false);
	}
	
	private void addNumerationsTab() {
		CrudComponent<Numeration> crud = new CrudComponent<Numeration>(Numeration.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiNumerations, null, true, false);
	}
	
	private void addPropertiesTab() {
		CrudComponent<Property> crud = new CrudComponent<Property>(Property.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiProperties, null, true, false);
	}

	private void addValidationsTab() {
		CrudComponent<Validation> crud = new CrudComponent<Validation>(Validation.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiValidations, null, true, false);
	}

	private void addClassificationsTab() {
		CrudComponent<Classification> crud = new CrudComponent<Classification>(Classification.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiClassifications, null, true, false);
	}
	
	private void addClassificationValuesTab() {
		CrudComponent<ClassificationValue> crud = new CrudComponent<ClassificationValue>(ClassificationValue.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiClassificationValues, null, true, false);
	}
	
	private void addActivitiesTab() {
		CrudComponent<Activity> crud = new CrudComponent<Activity>(Activity.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiActivities, null, true, false);
	}

	private void addNotificationsTab() {
		CrudComponent<Notification> crud = new CrudComponent<Notification>(Notification.class, new InfodocFieldFactory());
		crud.setSizeFull();
		mdiWindow.addWorkbenchContent(crud, InfodocConstants.uiNotifications, null, true, false);
	}

}
