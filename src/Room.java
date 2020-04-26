import java.util.HashMap;

public class Room {
	// Creates attributes of the objects
	private String roomID;
	private String roomName;
	private String roomDescription;
	private int roomToTheNorth;
	private int roomToTheSouth;
	private int roomToTheEast;
	private int roomToTheWest;
	private String[] itemID;
	private String puzzleID;
	private boolean isCurrentRoom;
	private String floor;
	private HashMap<String, Item> inventory = new HashMap<String, Item>();
	private HashMap<String, Monster> monsterList = new HashMap<String, Monster>();
	private Puzzle puzzle;
	//Group all the rooms together by TopFloor, Bottom Floor, and stuff

	// Empty Constructor
	Room() {
	}

	// Room Constructor without isCurrentRoom
	// Used when starting a new game
	Room(String newroomID, String roomName, String newroomDescription, int newroomToTheNorth, int newroomToTheSouth, int newroomToTheEast,
			int newroomToTheWest,String[] itemID,String puzzleID, String floor) {
		this.roomID = newroomID;
		this.roomName = roomName;
		this.roomDescription = newroomDescription;
		this.roomToTheNorth = newroomToTheNorth;
		this.roomToTheSouth = newroomToTheSouth;
		this.roomToTheEast = newroomToTheEast;
		this.roomToTheWest = newroomToTheWest;
		this.puzzleID = puzzleID;
		this.itemID = itemID;
		if(newroomID.equalsIgnoreCase("1"))
		{
			this.isCurrentRoom = false;
		}
		else
		{
			this.isCurrentRoom = true;
		}
		this.floor = floor;
		this.inventory = new HashMap<String, Item>();
		this.monsterList = new HashMap<String, Monster>();
		this.puzzle = null;
	}
	
	// Room Constructor with isCurrentRoom
	// Used when loading a saved file
	Room(String newroomID, String roomName, String newroomDescription, int newroomToTheNorth, int newroomToTheSouth, int newroomToTheEast,
			int newroomToTheWest,String[] itemID,String puzzleID,String floor, boolean isThisCurrentRoom) {
		this.roomID = newroomID;
		this.roomName = roomName;
		this.roomDescription = newroomDescription;
		this.roomToTheNorth = newroomToTheNorth;
		this.roomToTheSouth = newroomToTheSouth;
		this.roomToTheEast = newroomToTheEast;
		this.roomToTheWest = newroomToTheWest;
		this.puzzleID = puzzleID;
		this.itemID = itemID;
		this.isCurrentRoom = isThisCurrentRoom;
		this.floor = floor;
		this.inventory = new HashMap<String, Item>();
		this.monsterList = new HashMap<String, Monster>();
		this.puzzle = null;
	}
	
	/* Setter Methods */

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setRoomID(String newroomID) {
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
	
	public void setpuzzleID(String puzzleID) {
		this.puzzleID = puzzleID;
	}


	public void setitemID(String[] itemID) {
		this.itemID = itemID;
	}

	/* Getter Methods */

	public String getRoomID() {
		return roomID;
	}

	public String getRoomName() {
		return roomName;
	}
	public String getRoomDescription() {
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
	
	public String getpuzzleID() {
		return this.puzzleID;
	}

	public String[] getitemID() {
		return this.itemID;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public HashMap<String, Item> getInventory() {
		return inventory;
	}

	public void setInventory(HashMap<String, Item> inventory) {
		this.inventory = inventory;
	}
	
	public HashMap<String, Monster> getMonsterList() {
		return monsterList;
	}

	public void setMonsterList(HashMap<String, Monster> monsterList) {
		this.monsterList = monsterList;
	}
	
	public Puzzle getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	public void addItem(Item item)
	{
		this.inventory.put(item.getItemID(), item);
	}
	
	public void removeItem(Item item)
	{
		this.inventory.remove(item.getItemID());
	}
	
	public void addMonster(Monster monster)
	{
		this.monsterList.put(monster.getMonsterName(), monster);
	}
	
	public void removemonster(Monster monster)
	{
		this.monsterList.remove(monster.getMonsterName());
	}
}
