package com.acme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.model.CinemaRoomModel;
import com.acme.model.SeatModel;
import com.acme.model.request.BookingPostRequestModel;

public class BookingServiceTests {

	List<Integer> writeResults;
	CinemaRoomModel cinemaRoomModel;
	BookingService bookingService;

	Logger logger = LoggerFactory.getLogger(BookingServiceTests.class);
	
	@BeforeEach
	public void init() {
		writeResults = new ArrayList<Integer>();
		for (int i = 0; i < 100; i ++) {
			writeResults.add(-1);
		}
		cinemaRoomModel = new CinemaRoomModel(100, 100, 50, 1);
		bookingService = new BookingService();
		bookingService.cinemaRoomModel = cinemaRoomModel;
	}

	@Test
	/**
	 * test running concurrently 100 write thread and 100 read thread
	 */
	public void testConcurrency() throws InterruptedException {
		int numberOfTasks = 100;
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		try {
			for (int i = 0; i < numberOfTasks; i++) {
				executor.execute(new WriteRunnable(i));
				executor.execute(new ReadRunnable(i));
			}
		} catch (Exception err) {
			err.printStackTrace();
		}

		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.SECONDS);
		if (executor.isTerminated()) 
		{
			Assertions.assertEquals(1,
					writeResults.stream().filter((result) -> result == BookingService.RESERVATION_SUCCESS).count());
			Assertions.assertEquals(99,
					writeResults.stream().filter((result) -> result == BookingService.RESERVATION_CONFLICT).count());
		}

	}

	class WriteRunnable implements Runnable {
		int id;

		public WriteRunnable(int i) {
			this.id = i;
		}

		public void run() {
			try {
				System.out.println("Write runnable started id:" + id);
				System.out.println("Run: " + Thread.currentThread().getName());
				BookingPostRequestModel bookingPostRequestModel = new BookingPostRequestModel();
				bookingPostRequestModel.setVersion(1);
				SeatModel seatModel = new SeatModel(id % 10, id % 10);
				List<SeatModel> seats = new ArrayList<SeatModel>();
				seats.add(seatModel);
				bookingPostRequestModel.setSeats(seats);
				writeResults.set(id, bookingService.reserve(bookingPostRequestModel));
				System.out.println("Write runnable ended id:" + id);

			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}

	class ReadRunnable implements Runnable {
		int id;

		public ReadRunnable(int i) {
			this.id = i;
		}

		public void run() {
			try {
				System.out.println("Read runnable started id:" + id);
				System.out.println("Run: " + Thread.currentThread().getName());
				bookingService.getAvailableSeats(id % 20 + 1);
				System.out.println("Read runnable ended id:" + id);
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}
}
