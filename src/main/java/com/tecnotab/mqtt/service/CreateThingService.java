package com.tecnotab.mqtt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.iot.model.CreateThingRequest;
import com.amazonaws.services.iot.model.CreateThingResult;
import com.amazonaws.services.iot.model.DescribeThingRequest;
import com.amazonaws.services.iot.model.DescribeThingResult;
import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.tecnotab.mqtt.dto.AppConfig;
import com.tecnotab.mqtt.util.AwsConfig;

@Service
public class CreateThingService {
	
	@Autowired
	private AwsConfig iotClient;
	
	@Autowired
	private AppConfig appConfig;

	public String createThingAutomatically(String thingName) {
		
		//Check If thing Already Exists
		if(!describeThing(thingName)) {
			
			//Cretae Thing
			CreateThingResult response = iotClient.getIotClient(appConfig)
					.createThing(new CreateThingRequest()
							.withThingName(thingName));
			System.out.print("Thing Created Successsfully");
			return "Thing Created Successfully";
		}
				
		return "Thing Already Exists on IOT Console";
		
	}

	private boolean describeThing(String thingName) {
		if(thingName == null) {
			return false;
		}
		
		try {
			DescribeThingResponse(thingName);
			return true;
		} catch (ResourceNotFoundException e) {
			return false;
		}
		
	}

	 DescribeThingResult DescribeThingResponse(String thingName){
		DescribeThingRequest describeThingRequest = new DescribeThingRequest();
		describeThingRequest.setThingName( thingName);
		return iotClient.getIotClient(appConfig).describeThing(describeThingRequest);
		}
	 
}
