/**
 * Copyright (c) {2003,2010} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openmobster.core.cloud.console.client.ui;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 *
 * @author openmobster@gmail.com
 */
public class AuthenticateScreen implements Screen
{
	public String title()
	{
		return "authenticate";
	}
	
	public Canvas render()
	{
		VLayout main = new VLayout();
		
		//Generate the TopBar
		ToolStrip topBar = new ToolStrip();
        topBar.setHeight(33);
        topBar.setWidth100();
        topBar.addSpacer(6);
        
        //TODO: Add when an official Logo is ready
        /*ImgButton sgwtHomeButton = new ImgButton();
        sgwtHomeButton.setSrc("pieces/24/cube_green.png");
        sgwtHomeButton.setWidth(24);
        sgwtHomeButton.setHeight(24);
        sgwtHomeButton.setPrompt("Smart GWT Project Page");
        sgwtHomeButton.setHoverStyle("interactImageHover");
        sgwtHomeButton.setShowRollOver(false);
        sgwtHomeButton.setShowDownIcon(false);
        sgwtHomeButton.setShowDown(false);
        sgwtHomeButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            public void onClick(ClickEvent event) {
                com.google.gwt.user.client.Window.open("http://code.google.com/p/smartgwt/", "sgwt", null);
            }
        });
        topBar.addMember(sgwtHomeButton);
        topBar.addSpacer(6);*/
        
        //Add a title
        Label title = new Label("OpenMobster Cloud Console");
        title.setStyleName("sgwtTitle");
        title.setWidth(300);
        topBar.addMember(title);
        topBar.addFill();
        
        
        //Add component to the main window
        main.addMember(topBar);
        
        main.setShowEdges(true);
        
        //set the dimensions
        main.setWidth100();
        main.setHeight100();
        
        return main;
	}
}
