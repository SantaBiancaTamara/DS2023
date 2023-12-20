package com.example.communication;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class CommunicationApplication {
//
//
//
//	public static void main(String[] args) throws Exception {
//
//		SpringApplication.run(CommunicationApplication.class, args);
//
//
//
//		//mai trebe sa fac un device entity exact ca dincolo
//		//make request devices api to get all devices
//		//listen for device changes with rabbitmq topic
//		// listen for device measurements ( cu message receiver)
//
//	}
//
//
//}
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CommunicationApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CommunicationApplication.class, args);

		// Retrieve the MessageReceiver bean and start listening to messages
		MessageReceiver messageReceiver = context.getBean(MessageReceiver.class);
		try {
		messageReceiver.startListening();
		} catch (Exception e) {
			// Handle exceptions appropriately
			e.printStackTrace();
		}
	}
}