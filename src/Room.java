

public class Room {
	// Creates attributes of the objects
	private int roomID;
	private String roomName;
	private String roomDescription;
	private int roomToTheNorth;
	private int roomToTheSouth;
	private int roomToTheEast;
	private int roomToTheWest;
	private boolean hasBeenVisited;
	private int itemID;
	private int puzzleID;

	// Empty Constructor
	Room() {
	}

	// Room Constructor with all the attributes
	Room(int newroomID, String roomName, String newroomDescription, int newroomToTheNorth, int newroomToTheSouth, int newroomToTheEast,
			int newroomToTheWest,int itemID,int puzzleID,boolean newhasBeenVisited) {
		roomID = newroomID;
		this.roomName = roomName;
		roomDescription = newroomDescription;
		roomToTheNorth = newroomToTheNorth;
		roomToTheSouth = newroomToTheSouth;
		roomToTheEast = newroomToTheEast;
		roomToTheWest = newroomToTheWest;
		this.puzzleID = puzzleID;
		this.itemID = itemID;
		hasBeenVisited = newhasBeenVisited;
	
		

	}

	/* Setter Methods */

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setroomID(int newroomID) {
		roomID = newroomID;
	}

	public void setroomDescription(String newroomDescription) {
		roomDescription = newroomDescription;
	}

	public void setroomToTheNorth(int newroomToTheNorth) {
		roomToTheNorth = newroomToTheNorth;
	}

	public void setroomToTheSouth(int newroomToTheSouth) {
		roomToTheSouth = newroomToTheSouth;
	}

	public void setroomToTheEast(int newroomToTheEast) {
		roomToTheEast = newroomToTheEast;
	}

	public void setroomToTheWest(int newroomToTheWest) {
		roomToTheWest = newroomToTheWest;
	}

	public void sethasBeenVisited(boolean newhasBeenVisited) {
		hasBeenVisited = newhasBeenVisited;
	}
	
	

	public void setpuzzleID(int puzzleID) {
		this.puzzleID = puzzleID;
	}


	public void setitemID(int itemID) {
		this.itemID = itemID;
	}
	


	/* Getter Methods */

	public int getroomID() {
		return roomID;
	}

	public String getRoomName() {
		return roomName;
	}
	public String getroomDescription() {
		return roomDescription;
	}

	public int getroomToTheNorth() {
		return roomToTheNorth;
	}

	public int getroomToTheSouth() {
		return roomToTheSouth;
	}

	public int getroomToTheEast() {
		return roomToTheEast;
	}

	public int getroomToTheWest() {
		return roomToTheWest;
	}

	public boolean gethasBeenVisited() {
		return hasBeenVisited;
	}
	

	public int getpuzzleID() {
		return this.puzzleID;
	}
	


	public int getitemID() {
		return this.itemID;
	}


}
