/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package test.openmobster.device.agent.api.device;

import org.openmobster.device.agent.frameworks.mobileObject.MobileObject;

import test.openmobster.device.agent.api.Ticket;

/**
 * Usecase being tested:
 * 
 * a/ Device Database and Server Database in sync
 * 
 * b/ A new object is created on the device and "not-synced"
 * 
 * c/ The new object is updated on the device and "synced"
 * 
 * d/ The new object is deleted on the device and "synced" 
 * 
 * @author openmobster@gmail.com
 */
public class TestDeviceObjectLifeCycle3 extends TestMobileBeanSpec 
{
	public void test() throws Exception
	{
		this.bootService();
		String objectId = this.newTickets();
		this.updateTickets(objectId);
		this.deleteTickets(objectId);				
	}
	
	private String newTickets() throws Exception
	{
		String objectId = this.createNewDeviceObject();																	
		return objectId;
	}
	
	private void updateTickets(String objectId) throws Exception
	{
		this.updateDeviceObject(objectId);
		
		MobileObject bean = this.runner.getDeviceDatabase().read(this.runner.getService(), objectId);		
								
		this.runner.syncService();
		
		this.assertBean(bean, (Ticket)this.ticketConnector.read(this.getNewTicketId()));
	}
	
	private void deleteTickets(String objectId) throws Exception
	{
		this.deleteDeviceObject(objectId);
		
		this.runner.syncService();
		
		Ticket ticket = (Ticket)this.ticketConnector.read(this.getNewTicketId());
		MobileObject bean = this.runner.getDeviceDatabase().read(this.runner.getService(), objectId);
		
		assertNull("Ticket must be deleted on the server!!!", ticket);
		assertNull("Ticket must be deleted on the device!!!", bean);
	}
}
