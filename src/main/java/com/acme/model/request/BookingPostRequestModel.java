package com.acme.model.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.acme.model.SeatModel;

/**
 * The class defines the request model for /booking POST API
 */
public class BookingPostRequestModel {
	@NotNull
	@Valid
	private List<SeatModel> seats;
	@NotNull
	private Integer version;
	
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
