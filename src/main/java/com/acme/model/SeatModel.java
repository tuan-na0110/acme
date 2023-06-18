package com.acme.model;

import javax.validation.constraints.NotNull;

/**
 * The model stores state of a seat
 */
public class SeatModel {
	@NotNull
	private Integer row;
	@NotNull
	private Integer column;
	
	public static final int AVAILABLE = 0;
	public static final int RESERVED = 1;
	public static final int RESTRICTED = 2;
	
	public SeatModel() {
		super();
	}
	
	public SeatModel(int row, int column) {
		this.row = row;
		this.column = column;
	}
		
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
}
