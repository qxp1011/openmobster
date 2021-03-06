/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package test.openmobster.device.agent.sync;

import test.openmobster.device.agent.sync.server.ServerRecord;

import org.openmobster.device.agent.frameworks.mobileObject.MobileObject;

/**
 * @author openmobster@gmail.com
 */
public class TestOneWayServerSyncMapping extends AbstractSyncMapping 
{
	public void testAdd() throws Exception
	{
		ServerRecord newServerRecord = this.createNewServerRecord();
		MobileObject newDeviceRecord = this.createNewDeviceRecord();
				
		this.performOneWayServerSync();
		
		//Assert State of the Server
		this.assertServerPresence("1");
		this.assertServerPresence("2");
		this.assertServerPresence("3");
		this.assertServerAbsence("4");
		
		//Assert State of the Device
		this.assertDevicePresence("1-luid");
		this.assertDevicePresence("2-luid");
		this.assertDevicePresence("3-luid");
		this.assertDevicePresence(this.newDeviceRecordId+"-luid");
		
		assertEquals(this.mapEngine.mapFromLocalToServer("1-luid"), "1");
		assertEquals(this.mapEngine.mapFromLocalToServer("2-luid"), "2");
		assertEquals(this.mapEngine.mapFromLocalToServer("3-luid"), "3");
		
		//No mapping should exist since this is not synced
		assertEquals(this.mapEngine.mapFromLocalToServer(this.newDeviceRecordId+"-luid"), this.newDeviceRecordId+"-luid");
		
		assertEquals(this.mapEngine.mapFromServerToLocal("1"), "1-luid");
		assertEquals(this.mapEngine.mapFromServerToLocal("2"), "2-luid");
		assertEquals(this.mapEngine.mapFromServerToLocal("3"), "3-luid");
		
		//No mapping should exist since this is not synced
		assertEquals(this.mapEngine.mapFromServerToLocal("4"), "4");
		
		//TODO: Assert the State of the Server Sync Engine
		
		//TODO: Assert the State of teh Device Sync Engine
	}
	
	public void testReplace() throws Exception
	{
		MobileObject deviceRecord = this.updateDeviceRecord("1-luid");
		ServerRecord serverRecord = this.updateServerRecord("2");
		
		this.performOneWayServerSync();
		
		//Assert Server State
		this.assertServerPresence("1");
		this.assertServerPresence("2");
		this.assertServerMessage("1", this.getServerRecord("1").getMessage());
		this.assertServerMessage("2", serverRecord.getMessage());
		
		//Assert Device State
		this.assertDevicePresence("1-luid");
		this.assertDevicePresence("2-luid");
		this.assertDeviceMessage("1-luid", deviceRecord.getValue("message"));
		this.assertDeviceMessage("2-luid", serverRecord.getMessage());
		
		this.assertMapping();
		
		//TODO: Assert Server Sync State
		
		//TODO: Assert Device Sync State
	}
	
	public void testDelete() throws Exception
	{
		this.deleteDeviceRecord("1-luid");
		this.deleteServerRecord("2");
		
		this.performOneWayServerSync();
		
		//Assert Server State
		this.assertServerPresence("1");
		this.assertServerAbsence("2");
				
		//Assert Device State
		this.assertDeviceAbsence("1-luid");
		this.assertDeviceAbsence("2-luid");		
		
		this.assertMapping();
		
		//TODO: Assert Server Sync State
		
		//TODO: Assert Device Sync State
	}
	
	public void testConflict() throws Exception
	{
		MobileObject deviceRecord = this.updateDeviceRecord("1-luid");
		ServerRecord serverRecord = this.updateServerRecord("1");
		
		this.performOneWayServerSync();
		
		//Rule for OneWayServerSync: The server state wins over the device state
		
		//Assert Server State
		this.assertServerPresence("1");
		this.assertServerPresence("2");
		this.assertServerMessage("1", serverRecord.getMessage());
		
		//Assert Device State
		this.assertDevicePresence("1-luid");
		this.assertDevicePresence("2-luid");
		this.assertDeviceMessage("1-luid", serverRecord.getMessage());
		
		this.assertMapping();
	}
}
