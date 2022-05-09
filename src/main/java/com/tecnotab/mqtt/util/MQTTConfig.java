package com.tecnotab.mqtt.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.iotdata.model.PublishRequest;
import com.tecnotab.mqtt.dto.AppConfig;

@Configuration
public class MQTTConfig {

	@Autowired
	private AwsConfig iotClient;
	
	@Autowired
	private AppConfig appConfig;
	
	public void publishToShadow() throws IOException {
		
		String topic = "$aws/things/weather_monitor/shadow/name/local_weather/update";
		
		String payload = "{\"state\":{\"reported\":{\"sensor\":3.0}}}";
		ByteBuffer bb = StandardCharsets.UTF_8.encode(payload);
		PublishRequest publishRequest = new PublishRequest();
		publishRequest.withPayload(bb);
		publishRequest.withTopic(topic);
		publishRequest.setQos(0);
		iotClient.getIotDataClient(appConfig).publish(publishRequest);
		System.out.println("message Published successfully");
		}

}
