/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openmobster.showcase.app.command.framework;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import android.widget.FrameLayout;

import org.openmobster.android.api.sync.MobileBean;
import org.openmobster.core.mobileCloud.android.configuration.Configuration;
import org.openmobster.core.mobileCloud.android.errors.ErrorHandler;
import org.openmobster.core.mobileCloud.android.errors.SystemException;
import org.openmobster.core.mobileCloud.android.service.Registry;
import org.openmobster.core.mobileCloud.android_native.framework.ViewHelper;
import org.openmobster.core.mobileCloud.android_native.framework.events.ListItemClickEvent;
import org.openmobster.core.mobileCloud.android_native.framework.events.ListItemClickListener;
import org.openmobster.core.mobileCloud.api.ui.framework.Services;
import org.openmobster.core.mobileCloud.api.ui.framework.command.CommandContext;
import org.openmobster.core.mobileCloud.api.ui.framework.navigation.NavigationContext;
import org.openmobster.core.mobileCloud.api.ui.framework.navigation.Screen;
import org.openmobster.core.mobileCloud.api.ui.framework.resources.AppResources;
import org.openmobster.showcase.app.AppConstants;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.ListView;

/**
 * Controls the 'home' screen that is displayed when the App is first launched.
 * 
 * It displays the Showcase options in a 'ListView' 
 * 
 * @author openmobster@gmail.com
 */
public class MainScreen extends Screen
{
	private Integer screenId;
	
	@Override
	public void render()
	{
		try
		{
			//Lays out the screen based on configuration in res/layout/home.xml
			final Activity currentActivity = Services.getInstance().getCurrentActivity();
			
			String layoutClass = currentActivity.getPackageName()+".R$layout";
			String home = "command_framework_main";
			Class clazz = Class.forName(layoutClass);
			Field field = clazz.getField(home);
		
			this.screenId = field.getInt(clazz);						
		}
		catch(Exception e)
		{
			SystemException se = new SystemException(this.getClass().getName(), "render", new Object[]{
				"Message:"+e.getMessage(),
				"Exception:"+e.toString()
			});
			ErrorHandler.getInstance().handle(se);
			throw se;
		}
	}
	
	@Override
	public Object getContentPane()
	{
		return this.screenId;
	}
	
	@Override
	public void postRender()
	{
		Activity app = Services.getInstance().getCurrentActivity();
		
		this.show(app);
	}
	
	private void show(Activity activity)
	{
		//Populate the List View
		ListView view = (ListView)ViewHelper.findViewById(activity, "list");
		activity.setTitle("Command Framework Showcase");
		
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("empty", "");
		map.put("title", "Ajax Command");
		mylist.add(map);
		
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("empty", "");
		map2.put("title", "Busy Command");
		mylist.add(map2);
		
		HashMap<String, String> map3 = new HashMap<String, String>();
		map3.put("empty", "");
		map3.put("title", "Fast Command");
		mylist.add(map3);
		
		int rowId = ViewHelper.findLayoutId(activity, "command_framework_row");
		String[] rows = new String[]{"empty","title"};
		int[] rowUI = new int[] {ViewHelper.findViewId(activity, "empty"), ViewHelper.findViewId(activity, "title")};
		SimpleAdapter showcaseAdapter = new SimpleAdapter(activity, mylist, rowId, rows, rowUI);
	    view.setAdapter(showcaseAdapter);
	    
	    OnItemClickListener clickListener = new ClickListener();
		view.setOnItemClickListener(clickListener);
	}
	
	private static class ClickListener implements OnItemClickListener
	{	
		private ClickListener()
		{
		}
		
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			if(position == 0)
			{
				//Execute the Ajax Command
				CommandContext commandContext = new CommandContext();
		     	commandContext.setTarget("/ajax/command");
		     	Services.getInstance().getCommandService().execute(commandContext);
			}
			else if(position == 1)
			{
				//Execute the Busy Command
				CommandContext commandContext = new CommandContext();
		     	commandContext.setTarget("/busy/command");
		     	Services.getInstance().getCommandService().execute(commandContext);
			}
			else if(position == 2)
			{
				//Execute the Fast Command
				CommandContext commandContext = new CommandContext();
		     	commandContext.setTarget("/fast/command");
		     	Services.getInstance().getCommandService().execute(commandContext);
			}
		}
	}
}