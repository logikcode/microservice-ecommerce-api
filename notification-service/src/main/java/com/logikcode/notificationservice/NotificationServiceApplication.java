package com.logikcode.notificationservice;

import com.logikcode.notificationservice.event.OrderPlacementEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@KafkaListener(topics="notificationTopic")
	public void handleNotification(OrderPlacementEvent orderPlacementEvent){
		// send out an email notification
		log.info("Received notification for order - {}", orderPlacementEvent.getOrderNumber());
	}
}
