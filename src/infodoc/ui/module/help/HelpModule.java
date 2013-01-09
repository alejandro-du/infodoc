package infodoc.ui.module.help;

import infodoc.core.InfodocConstants;
import infodoc.core.ui.common.InfodocTheme;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import enterpriseapp.EnterpriseApplication;
import enterpriseapp.hibernate.dto.User;
import enterpriseapp.ui.window.MDIWindow;
import enterpriseapp.ui.window.Module;

public class HelpModule implements Module, Command {
	
	private static final long serialVersionUID = 1L;
	
	private MenuItem helpMenuItem;
	private MenuItem userManualMenuItem;
	private MenuItem aboutMenuItem;
	private MDIWindow mdiWindow;

	@Override
	public void init() {
	}

	@Override
	public void add(MDIWindow mdiWindow, User user) {
		this.mdiWindow = mdiWindow;
		
		helpMenuItem = mdiWindow.getMenuBar().addItem(InfodocConstants.uiHelp, new ThemeResource(InfodocTheme.iconHelp), null);
		userManualMenuItem = helpMenuItem.addItem(InfodocConstants.uiUserManual, new ThemeResource(InfodocTheme.iconUserManual), this);
		aboutMenuItem = helpMenuItem.addItem(InfodocConstants.uiAbout, new ThemeResource(InfodocTheme.iconAbout), this);
	}
	
	@Override
	public boolean userCanAccess(User user) {
		return true;
	}
	
	public MenuItem getHelpMenuItem() {
		return helpMenuItem;
	}

	@Override
	public void menuSelected(MenuItem selectedItem) {
		if(selectedItem.equals(aboutMenuItem)) {
			AboutWindow aboutWindow = new AboutWindow();
			aboutWindow.setModal(true);
			mdiWindow.addWindow(aboutWindow);
			
		} else if(selectedItem.equals(userManualMenuItem)) {
			ThemeResource resource = new ThemeResource(InfodocConstants.uiUserManualPdfName);
			EnterpriseApplication.getInstance().getMainWindow().open(resource, "_blank", 700, 350, 0);
		}
	}

}
