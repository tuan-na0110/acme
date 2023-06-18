package com.acme.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.model.ResponseModel;
import com.acme.model.SeatModel;
import com.acme.model.request.BookingPostRequestModel;
import com.acme.model.response.BookingGetResponseModel;
import com.acme.service.BookingService;

/**
 * The controller receives Restful API requests related to the booking
 */
@RestController
@RequestMapping("/api/v1")
public class BookingController {

	@Autowired
	private BookingService bookingService;


	/**
	 * function receives the get request
	 * 
	 * @param seatNum
	 * @return
	 */
	@GetMapping("/booking")
	public ResponseEntity<ResponseModel> index(
			@RequestParam(value = "num", required = false) Integer seatNum) {
		if (seatNum == null)
			seatNum = 1;
		List<SeatModel> seats = bookingService.getAvailableSeats(seatNum);
		if (seats == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(new BookingGetResponseModel(seats, bookingService.getCinemaRoomModelVersion()));
	}

	/**
	 * function receives the post request
	 * 
	 * @param bookingPostRequestModel
	 * @return
	 */
	@PostMapping("/booking")
	public ResponseEntity<ResponseModel> addTodo(@RequestBody @Valid BookingPostRequestModel bookingPostRequestModel) {
		int result = bookingService.reserve(bookingPostRequestModel);
		if (result == BookingService.RESERVATION_DIFF_VERSION || result == BookingService.RESERVATION_CONFLICT) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ResponseModel(HttpStatus.CONFLICT.value(), "please run the query again"));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(0, null));
		}
	}
}
