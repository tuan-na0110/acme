package com.acme.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The model stores state of the cinema's room
 */
public class CinemaRoomModel {
	private int version;
	private int rowNum;
	private int columnNum;
	private int minDistance;
	private int[][] state;
	private long numberSeatAvailabe;

	public CinemaRoomModel() {

	}

	public CinemaRoomModel(int rowNum, int columnNum, int minDistance, int version) {
		this.rowNum = rowNum;
		this.columnNum = columnNum;
		this.minDistance = minDistance;
		this.version = version;
		this.state = new int[rowNum][columnNum];
		this.numberSeatAvailabe = rowNum * columnNum;
	}

	/**
	 * the function gets a list of available seats
	 * 
	 * @param seatNum a number that specifies how many seats are needed.
	 * @return List<SeatModel> a list of available seats, null if the number of
	 *         available seats is below the request's input
	 */
	public List<SeatModel> getAvailableSeat(int seatNum) {

		if (this.numberSeatAvailabe < seatNum)
			return null;

		/**
		 * Using the greed strategy: return the first group of seats meets the criteria
		 *
		 * TODO: find a better strategy if I have more time.
		 */
		boolean[][] visited = new boolean[this.rowNum][this.columnNum];
		for (int rowIdx = 0; rowIdx < rowNum; rowIdx++) {
			for (int colIdx = 0; colIdx < columnNum; colIdx++) {
				if (visited[rowIdx][colIdx] || this.state[rowIdx][colIdx] != SeatModel.AVAILABLE) {
					continue;
				}

				List<SeatModel> foundSeatModels = new ArrayList<SeatModel>();
				Queue<SeatTreeNode> nodeQueue = new LinkedList<SeatTreeNode>();
				nodeQueue.add(new SeatTreeNode(rowIdx, colIdx, 0));
				visited[rowIdx][colIdx] = true;
				while (!nodeQueue.isEmpty()) {
					SeatTreeNode currentNode = nodeQueue.poll();
					int curRow = currentNode.row;
					int curColumn = currentNode.column;
					foundSeatModels.add(new SeatModel(curRow, curColumn));
					if (foundSeatModels.size() == seatNum)
						return foundSeatModels;

					for (SeatTreeNode neighborNode : this.getNeighborNodes(currentNode)) {
						if (!visited[neighborNode.row][neighborNode.column]) {
							visited[neighborNode.row][neighborNode.column] = true;
							if (this.state[neighborNode.row][neighborNode.column] == SeatModel.AVAILABLE) {
								nodeQueue.add(neighborNode);
							}
						}
					}
				}
			}
		}

		/**
		 * When no group meets the criteria, using the greed strategy: add each
		 * available seat to the return list until the size of the return list meets the
		 * requirement
		 *
		 * TODO: find a better strategy if I have more time.
		 */
		List<SeatModel> foundSeatModels = new ArrayList<SeatModel>();
		for (int rowIdx = 0; rowIdx < rowNum; rowIdx++) {
			for (int colIdx = 0; colIdx < columnNum; colIdx++) {
				if (this.state[rowIdx][colIdx] == SeatModel.AVAILABLE) {
					foundSeatModels.add(new SeatModel(rowIdx, colIdx));
					if (foundSeatModels.size() == seatNum)
						return foundSeatModels;
				}
			}
		}

		return null;
	}

	/**
	 * the function reserve a list of seats
	 * 
	 * @param seats list of seats which client want to reserve
	 * @return boolean success or failure
	 */
	public boolean reserve(List<SeatModel> seats) {
		// check if the seat input is available
		for (SeatModel seat : seats) {
			if (this.state[seat.getRow()][seat.getColumn()] != SeatModel.AVAILABLE) {
				return false;
			}
		}

		// reserve the seat input
		boolean[][] visited = new boolean[this.rowNum][this.columnNum];
		Queue<SeatTreeNode> nodeQueue = new LinkedList<SeatTreeNode>();
		for (SeatModel seat : seats) {
			this.state[seat.getRow()][seat.getColumn()] = SeatModel.RESERVED;
			this.numberSeatAvailabe--;
			visited[seat.getRow()][seat.getColumn()] = true;
			nodeQueue.add(new SeatTreeNode(seat.getRow(), seat.getColumn(), 0));
		}

		// propagate the restriction
		while (!nodeQueue.isEmpty()) {
			SeatTreeNode currentNode = nodeQueue.poll();
			int curRow = currentNode.row;
			int curColumn = currentNode.column;
			if (this.state[curRow][curColumn] == SeatModel.AVAILABLE) {
				this.numberSeatAvailabe--;
				this.state[curRow][curColumn] = SeatModel.RESTRICTED;
			}

			for (SeatTreeNode neighborNode : this.getNeighborNodes(currentNode)) {
				if (neighborNode.distance >= this.minDistance) {
					continue;
				}
				if (!visited[neighborNode.row][neighborNode.column]) {
					visited[neighborNode.row][neighborNode.column] = true;
					nodeQueue.add(neighborNode);
				}
			}
		}

		return true;
	}

	/**
	 * The function returns a node's list of neighbor nodes in the search tree
	 * Assumed that the diagonal seat is not a neighbor
	 * 
	 * @param maxRow    the max row in the cinema's room
	 * @param maxColumn the max column in the cinema's room
	 * @return List<SeatTreeNode> list of neighbor nodes in the search tree
	 */
	public List<SeatTreeNode> getNeighborNodes(SeatTreeNode currentNode) {
		List<SeatTreeNode> neighborNodes = new ArrayList<SeatTreeNode>();
		if (currentNode.row > 0) {
			neighborNodes.add(new SeatTreeNode(currentNode.row - 1, currentNode.column, currentNode.distance + 1));
		}
		if (currentNode.row < this.rowNum - 1) {
			neighborNodes.add(new SeatTreeNode(currentNode.row + 1, currentNode.column, currentNode.distance + 1));
		}

		if (currentNode.column > 0) {
			neighborNodes.add(new SeatTreeNode(currentNode.row, currentNode.column - 1, currentNode.distance + 1));
		}
		if (currentNode.column < this.columnNum - 1) {
			neighborNodes.add(new SeatTreeNode(currentNode.row, currentNode.column + 1, currentNode.distance + 1));
		}
		return neighborNodes;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}

	public int getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(int minDistance) {
		this.minDistance = minDistance;
	}

	public int[][] getState() {
		return state;
	}

	public void setState(int[][] state) {
		this.state = state;
	}

	public long getNumberSeatAvailabe() {
		return numberSeatAvailabe;
	}

	public void setNumberSeatAvailabe(long numberSeatAvailabe) {
		this.numberSeatAvailabe = numberSeatAvailabe;
	}

	/**
	 * The inner class stores information of a node in the search tree Only used in
	 * CinemaRoomModel class
	 */
	public class SeatTreeNode {
		private int row;
		private int column;
		private int distance;

		public SeatTreeNode(int row, int column, int distance) {
			this.row = row;
			this.column = column;
			this.distance = distance;
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

		public int getDistance() {
			return distance;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}
	}
}
