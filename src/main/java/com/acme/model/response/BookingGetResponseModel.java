package com.acme.model.response;

import java.util.List;

import com.acme.model.ResponseModel;
import com.acme.model.SeatModel;

/**
 * The class defines the response model for /booking GET API
 */
public class BookingGetResponseModel extends ResponseModel {
	
	public BookingGetResponseModel(List<SeatModel> seats, int version) {
		super(0, null);
		this.seats = seats;
		this.version = version;
	}
	
	private List<SeatModel> seats;
	private int version;
	public List<SeatModel> getSeats() {
		return seats;
	}
	public void setSeats(List<SeatModel> seats) {
		this.seats = seats;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}
