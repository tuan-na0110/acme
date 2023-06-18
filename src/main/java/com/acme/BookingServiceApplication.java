package com.acme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.acme.service.BookingService;

/**
 * Booking Service Application
 */
@SpringBootApplication
public class BookingServiceApplication {
		
	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(BookingServiceApplication.class, args);
		
		// initialize a CinemaRoomModel after the application has been started
		BookingService bookingService = context.getBean(BookingService.class);
		bookingService.initCinemaRoomModel();
	}

}
