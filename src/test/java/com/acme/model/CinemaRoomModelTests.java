package com.acme.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.acme.model.CinemaRoomModel.SeatTreeNode;

public class CinemaRoomModelTests {

	@Test
	/**
	 * test the getNeighborNodes function
	 */
	public void testGetNeighborNodes() {
		CinemaRoomModel modelTest = new CinemaRoomModel(4, 4, 4, 1);
		SeatTreeNode testCase1 = modelTest.new SeatTreeNode(0, 0, 0);
		List<SeatTreeNode> neighBorNodes = modelTest.getNeighborNodes(testCase1);
		Assertions.assertEquals(2, neighBorNodes.size());
		Assertions.assertEquals(1, neighBorNodes.get(0).getRow());
		Assertions.assertEquals(0, neighBorNodes.get(0).getColumn());
		Assertions.assertEquals(0, neighBorNodes.get(1).getRow());
		Assertions.assertEquals(1, neighBorNodes.get(1).getColumn());
		Assertions.assertEquals(1, neighBorNodes.get(0).getDistance());
		
		SeatTreeNode testCase2 = modelTest.new SeatTreeNode(0, 3, 0);
		neighBorNodes = modelTest.getNeighborNodes(testCase2);
		Assertions.assertEquals(2, neighBorNodes.size());
		Assertions.assertEquals(1, neighBorNodes.get(0).getRow());
		Assertions.assertEquals(3, neighBorNodes.get(0).getColumn());
		Assertions.assertEquals(0, neighBorNodes.get(1).getRow());
		Assertions.assertEquals(2, neighBorNodes.get(1).getColumn());
		
		SeatTreeNode testCase3 = modelTest.new SeatTreeNode(3, 0, 0);
		neighBorNodes = modelTest.getNeighborNodes(testCase3);
		Assertions.assertEquals(2, neighBorNodes.size());
		Assertions.assertEquals(2, neighBorNodes.get(0).getRow());
		Assertions.assertEquals(0, neighBorNodes.get(0).getColumn());
		Assertions.assertEquals(3, neighBorNodes.get(1).getRow());
		Assertions.assertEquals(1, neighBorNodes.get(1).getColumn());
		
		SeatTreeNode testCase4 = modelTest.new SeatTreeNode(3, 3, 0);
		neighBorNodes = modelTest.getNeighborNodes(testCase4);
		Assertions.assertEquals(2, neighBorNodes.size());
		Assertions.assertEquals(2, neighBorNodes.get(0).getRow());
		Assertions.assertEquals(3, neighBorNodes.get(0).getColumn());
		Assertions.assertEquals(3, neighBorNodes.get(1).getRow());
		Assertions.assertEquals(2, neighBorNodes.get(1).getColumn());
		
		SeatTreeNode testCase5 = modelTest.new SeatTreeNode(2, 2, 0);
		neighBorNodes = modelTest.getNeighborNodes(testCase5);
		Assertions.assertEquals(4, neighBorNodes.size());
		Assertions.assertEquals(1, neighBorNodes.get(0).getRow());
		Assertions.assertEquals(2, neighBorNodes.get(0).getColumn());
		Assertions.assertEquals(3, neighBorNodes.get(1).getRow());
		Assertions.assertEquals(2, neighBorNodes.get(1).getColumn());
		Assertions.assertEquals(2, neighBorNodes.get(2).getRow());
		Assertions.assertEquals(1, neighBorNodes.get(2).getColumn());
		Assertions.assertEquals(2, neighBorNodes.get(3).getRow());
		Assertions.assertEquals(3, neighBorNodes.get(3).getColumn());
	}
	
	@Test
	/**
	 * test the algorithm which used to find and reserve seats
	 */
	public void testReserveAndGetAvailablesSeats() {
		CinemaRoomModel modelTest = new CinemaRoomModel(10, 10, 3, 1);
		List<SeatModel> testCase1 = new ArrayList<SeatModel>();
		testCase1.add(new SeatModel(4, 4));
		testCase1.add(new SeatModel(5, 5));
		testCase1.add(new SeatModel(6, 6));
		modelTest.reserve(testCase1);
		Assertions.assertEquals(77, modelTest.getNumberSeatAvailabe());
		
		List<SeatModel> availableSeats = modelTest.getAvailableSeat(15);
		Assertions.assertEquals(15, availableSeats.size());
		Assertions.assertEquals(0, availableSeats.get(0).getRow());
		Assertions.assertEquals(0, availableSeats.get(0).getColumn());
		Assertions.assertEquals(1, availableSeats.get(1).getRow());
		Assertions.assertEquals(0, availableSeats.get(1).getColumn());
		Assertions.assertEquals(0, availableSeats.get(2).getRow());
		Assertions.assertEquals(1, availableSeats.get(2).getColumn());
		Assertions.assertEquals(2, availableSeats.get(3).getRow());
		Assertions.assertEquals(0, availableSeats.get(3).getColumn());
		Assertions.assertEquals(1, availableSeats.get(4).getRow());
		Assertions.assertEquals(1, availableSeats.get(4).getColumn());
		Assertions.assertEquals(0, availableSeats.get(5).getRow());
		Assertions.assertEquals(2, availableSeats.get(5).getColumn());
		Assertions.assertEquals(3, availableSeats.get(6).getRow());
		Assertions.assertEquals(0, availableSeats.get(6).getColumn());
		Assertions.assertEquals(2, availableSeats.get(7).getRow());
		Assertions.assertEquals(1, availableSeats.get(7).getColumn());
		Assertions.assertEquals(1, availableSeats.get(8).getRow());
		Assertions.assertEquals(2, availableSeats.get(8).getColumn());
		Assertions.assertEquals(0, availableSeats.get(9).getRow());
		Assertions.assertEquals(3, availableSeats.get(9).getColumn());
		Assertions.assertEquals(4, availableSeats.get(10).getRow());
		Assertions.assertEquals(0, availableSeats.get(10).getColumn());
		Assertions.assertEquals(3, availableSeats.get(11).getRow());
		Assertions.assertEquals(1, availableSeats.get(11).getColumn());
		Assertions.assertEquals(2, availableSeats.get(12).getRow());
		Assertions.assertEquals(2, availableSeats.get(12).getColumn());
		Assertions.assertEquals(1, availableSeats.get(13).getRow());
		Assertions.assertEquals(3, availableSeats.get(13).getColumn());
		Assertions.assertEquals(0, availableSeats.get(14).getRow());
		Assertions.assertEquals(4, availableSeats.get(14).getColumn());
		
		modelTest.reserve(availableSeats);
		Assertions.assertEquals(52, modelTest.getNumberSeatAvailabe());
		
		List<SeatModel> testCase2 = new ArrayList<SeatModel>();
		testCase2.add(new SeatModel(6, 9));
		modelTest.reserve(testCase2);
		Assertions.assertEquals(45, modelTest.getNumberSeatAvailabe());
		
		availableSeats = modelTest.getAvailableSeat(25);
		Assertions.assertEquals(25, availableSeats.size());
		Assertions.assertEquals(5, availableSeats.get(0).getRow());
		Assertions.assertEquals(2, availableSeats.get(0).getColumn());
		Assertions.assertEquals(8, availableSeats.get(19).getRow());
		Assertions.assertEquals(5, availableSeats.get(19).getColumn());
		Assertions.assertEquals(9, availableSeats.get(24).getRow());
		Assertions.assertEquals(8, availableSeats.get(24).getColumn());
		
		modelTest.reserve(availableSeats);
		Assertions.assertEquals(18, modelTest.getNumberSeatAvailabe());
		
		List<SeatModel> testCase3 = new ArrayList<SeatModel>();
		testCase3.add(new SeatModel(2, 7));
		modelTest.reserve(testCase3);
		Assertions.assertEquals(5, modelTest.getNumberSeatAvailabe());
		
		availableSeats = modelTest.getAvailableSeat(4);
		Assertions.assertEquals(4, availableSeats.size());
		Assertions.assertEquals(0, availableSeats.get(0).getRow());
		Assertions.assertEquals(8, availableSeats.get(0).getColumn());
		Assertions.assertEquals(0, availableSeats.get(1).getRow());
		Assertions.assertEquals(9, availableSeats.get(1).getColumn());
		Assertions.assertEquals(1, availableSeats.get(2).getRow());
		Assertions.assertEquals(9, availableSeats.get(2).getColumn());
		Assertions.assertEquals(3, availableSeats.get(3).getRow());
		Assertions.assertEquals(9, availableSeats.get(3).getColumn());
		
		modelTest.reserve(availableSeats);
		List<SeatModel> testCase4 = new ArrayList<SeatModel>();
		testCase4.add(new SeatModel(4, 8));
		Assertions.assertFalse(modelTest.reserve(testCase4));
		Assertions.assertNull(modelTest.getAvailableSeat(1));
	}
}
