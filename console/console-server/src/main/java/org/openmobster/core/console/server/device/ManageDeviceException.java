/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openmobster.core.console.server.device;

import java.util.Map;

/**
 * 
 * @author openmobster@gmail.com
 */
public class ManageDeviceException extends Exception
{	
	
	private static final long serialVersionUID = -2294095894100486056L;
	
	/*public static final int VALIDATION_ERROR = 1;	
	public static final int ACCOUNT_ALREADY_EXISTS = 2;
	public static final int ACCOUNT_INACTIVE = 3;
	public static final int LAST_ACCOUNT_CANNOT_BE_DEACTIVATED = 4;*/
	
	private Map<String, String[]> validationErrors;
	private int type;
	
	public ManageDeviceException()
	{
		this("");
	}

	
	public ManageDeviceException(String msg)
	{
		super(msg);
	}

	
	public ManageDeviceException(Throwable t)
	{
		super(t.getMessage(), t);
	}
	
	public ManageDeviceException(int type)
	{
		this("");
		this.type = type;
	}
	
	public ManageDeviceException(String msg, int type)
	{
		this(msg);
		this.type = type;
	}
	
	public ManageDeviceException(Throwable t, int type)
	{
		this(t);
		this.type = type;
	}
	//-------------------------------------------------------------------------------------------------------------------------------------------------------
	public Map<String, String[]> getValidationErrors() 
	{
		return validationErrors;
	}


	public void setValidationErrors(Map<String, String[]> validationErrors) 
	{
		this.validationErrors = validationErrors;
	}
	
	public int getType()
	{
		return this.type;
	}
	//-------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public String getMessage() 
	{	
		String message = super.getMessage();
		
		if((message == null || message.trim().length() == 0) && this.type > 0)
		{
			switch(this.type)
			{
				/*case ManageDeviceException.VALIDATION_ERROR:
					message = "validation_error";
				break;
				
				case ManageDeviceException.ACCOUNT_ALREADY_EXISTS:
					message = "identity_already_registered";
				break;
				
				case ManageDeviceException.ACCOUNT_INACTIVE:
					message = "account_inactive";
				break;
				
				case ManageDeviceException.LAST_ACCOUNT_CANNOT_BE_DEACTIVATED:
					message = "last_account_cannot_be_deactivated";
				break;*/
				
				default:
					message = this.getMessage();
				break;
			}
		}
		
		return message;
	}	
}
