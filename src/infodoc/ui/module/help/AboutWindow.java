package infodoc.ui.module.help;

import infodoc.core.InfodocConstants;
import infodoc.core.ui.common.InfodocTheme;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AboutWindow extends Window {

	private static final long serialVersionUID = 1L;
	
	public AboutWindow() {
		super(InfodocConstants.uiAbout);
		addStyleName(InfodocTheme.WINDOW_OPAQUE);
		setIcon(new ThemeResource(InfodocTheme.iconAbout));
		setSizeUndefined();
		setWidth("580px");
		setResizable(false);
		
		String installedModules = InfodocConstants.infodocModules.replace(" ", "");
		String installedModulesHtml = "";
		
		for(String module : installedModules.split(",")) {
			installedModulesHtml += "<span style='margin-left: 20px;'>" + module + "</span><br/>";
		}
		
		String top =
				"<h2><i>InfoDoc " + InfodocConstants.infodocVersion + "</i></h2>" +
				"Modules:" +
				"<pre><small>" + installedModulesHtml + "</small></pre>" +
				"<br/>License granted to:" +
				"<pre><small><span style='margin-left: 20px;'>" + InfodocConstants.infodocCompanyName + "</span>" + 
				(InfodocConstants.infodocCompanyId == null ? "" : "<br/><span style='margin-left: 20px;'> " + InfodocConstants.infodocCompanyId  + "</span>") +
				(InfodocConstants.infodocCompanyAddress == null ? "" : "<br/><span style='margin-left: 20px;'>" + InfodocConstants.infodocCompanyAddress + "</span></pre></small>") + "<br/><br/>";
		
		String lower = "<br/>Contact the developer: <a href='" + InfodocConstants.infodocDeveloperUrl + "'>" + InfodocConstants.infodocDeveloperUrl + "</a>," +
				"<br/><b>All rights reserved</b>.";
		
		Label topLabel = new Label(top, Label.CONTENT_XHTML);
		Label lowerLabel = new Label(lower, Label.CONTENT_XHTML);
		lowerLabel.addStyleName(InfodocTheme.LABEL_TINY);
		
		VerticalLayout rightLayout = new VerticalLayout();
		rightLayout.setMargin(true);
		rightLayout.setStyleName(InfodocTheme.PANEL_LIGHT);
		rightLayout.addStyleName(InfodocTheme.PANEL_BORDERLESS);
		rightLayout.addStyleName(InfodocTheme.COOL_FONT);
		rightLayout.addComponent(topLabel);
		rightLayout.addComponent(lowerLabel);
		
		VerticalLayout leftLayout = new VerticalLayout();
		leftLayout.setWidth("130px");
		leftLayout.addComponent(new Label("<h2></h2>", Label.CONTENT_XHTML));
		leftLayout.addComponent(new Embedded(null, new ThemeResource(InfodocTheme.infodocBoxImage)));
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
		layout.addComponent(leftLayout);
		layout.addComponent(rightLayout);
		layout.setExpandRatio(rightLayout, 1);
		
		Panel panel = new Panel();
		panel.addComponent(layout);
		
		setContent(panel);
	}

}
