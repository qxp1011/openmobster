/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openmobster.device.agent.location;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.openmobster.cloud.api.location.LocationContext;
import org.openmobster.cloud.api.location.LocationServiceBean;
import org.openmobster.cloud.api.location.BeanURI;
import org.openmobster.cloud.api.location.Request;
import org.openmobster.cloud.api.location.Response;
import org.openmobster.cloud.api.location.Place;

import org.openmobster.core.common.Utilities;

/**
 *
 * @author openmobster@gmail.com
 */
@BeanURI(uri="friends")
public class FriendBean implements LocationServiceBean
{
	private static Logger log = Logger.getLogger(LocationServiceBean.class);

	@Override
	public Response invoke(LocationContext locationContext, Request request)
	{
		Response response = new Response();
		
		log.info("*******************************************");
		log.info("FriendBean invoked................");
		log.info("*******************************************");
		
		List<Place> nearbyPlaces = locationContext.getNearbyPlaces();
		if(nearbyPlaces != null && !nearbyPlaces.isEmpty())
		{
			Map<String,String> friends = new HashMap<String,String>();
			for(Place place:nearbyPlaces)
			{
				String placeId = place.getId();
				
				//In a real implementation, you can lookup the coupon in the database based on the Place object
				String friend = "friend://"+Utilities.generateUID();
				
				friends.put(placeId, friend);
			}
			response.setMapAttribute("friends", friends);
		}
		
		
		return response;
	}
}
