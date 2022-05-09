package com.tecnotab.mqtt.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tecnotab.mqtt.dto.WeatherPayload;
import com.tecnotab.mqtt.service.CreateThingService;
import com.tecnotab.mqtt.util.MQTTConfig;

@RestController
public class MQTTController {
	
	@Autowired 
	MQTTConfig mqttConfig;
	
	@Autowired
	CreateThingService createThingService;
	
	@PostMapping("/publish")
	public String publishMessaged(@RequestBody WeatherPayload payload) throws IOException {
		
		mqttConfig.publishToShadow();	
		
		return "message Published Successfully";
	}

	@PostMapping("/register/{thingName}")
	public String createThing(@PathVariable String thingName) {
		return createThingService.createThingAutomatically(thingName);
	}
}
