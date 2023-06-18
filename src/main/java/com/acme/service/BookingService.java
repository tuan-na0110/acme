package com.acme.service;

import org.slf4j.LoggerFactory;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.acme.model.CinemaRoomModel;
import com.acme.model.SeatModel;
import com.acme.model.request.BookingPostRequestModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * The service handles business logics related to booking
 */
@Service
public class BookingService {

	Logger logger = LoggerFactory.getLogger(BookingService.class);

	CinemaRoomModel cinemaRoomModel;

	@Autowired
	private Environment env;

	/**
	 * create a CinemaRoomModel with properties
	 */
	public void initCinemaRoomModel() {
		Integer cinemaRoomRowNum = Integer.parseInt(env.getProperty("cinema.room.rows"));
		Integer cinemaRoomColNum = Integer.parseInt(env.getProperty("cinema.room.columns"));
		Integer cinemaRoomMinDistance = Integer.parseInt(env.getProperty("cinema.room.min_distance"));
		Integer cinemaRoomVersion = Integer.parseInt(env.getProperty("cinema.room.version"));
		this.cinemaRoomModel = new CinemaRoomModel(cinemaRoomRowNum, cinemaRoomColNum, cinemaRoomMinDistance,
				cinemaRoomVersion);
	}

	/**
	 * return the version of the model of the cinema's room
	 * 
	 * @return int version of the model of the cinema's room
	 */
	public int getCinemaRoomModelVersion() {
		return this.cinemaRoomModel.getVersion();
	}

	/**
	 * the function gets a list of available seats
	 * 
	 * @param seatNum a number that specifies how many seats are needed.
	 * @return List<SeatModel> a list of available seats, null if the number of
	 *         available seats is below the request's input
	 */
	public List<SeatModel> getAvailableSeats(int seatNum) {
		return this.cinemaRoomModel.getAvailableSeat(seatNum);
	}

	public static final int RESERVATION_SUCCESS = 0;
	public static final int RESERVATION_DIFF_VERSION = 1;
	public static final int RESERVATION_CONFLICT = 2;

	/**
	 * the function reserves a list of seats
	 * 
	 * @param BookingPostRequestModel request's body
	 * @return int 0: success, 1: the version in the request is not equal to the
	 *         version in model, 2: seat is not available
	 */
	public int reserve(BookingPostRequestModel bookingPostRequestModel) {
		if (bookingPostRequestModel.getVersion() != this.cinemaRoomModel.getVersion()) {
			return RESERVATION_DIFF_VERSION;
		}

		synchronized (this.cinemaRoomModel) {
			if (this.cinemaRoomModel.reserve(bookingPostRequestModel.getSeats())) {
				if (logger.isInfoEnabled()) {
					// log the state of cinema's room if log's level is info
					ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
					String stateJson;
					try {
						stateJson = ow.writeValueAsString(this.cinemaRoomModel.getState());
						logger.info("The state of cinema's room");
						logger.info(stateJson);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				}
				return RESERVATION_SUCCESS;
			} else {
				return RESERVATION_CONFLICT;
			}
		}
	}
}
