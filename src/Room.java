import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class Room {
	// Creates attributes of the objects
	//BOOKMARK
	//Add an ArrayList of exits???
	private String roomID;
	private String roomName;
	private String roomDescription;
	private String roomToTheNorth;
	private String roomToTheSouth;
	private String roomToTheEast;
	private String roomToTheWest;
	private String[] itemID;
	private String puzzleID;
	private boolean isCurrentRoom;
	private String floor;
	private HashMap<String, Item> inventory = new HashMap<String, Item>();
	//private ArrayList<Item> inventory = new ArrayList<Item>();
	private HashMap<String, Monster> monsterList = new HashMap<String, Monster>();
	private Puzzle puzzle;
	private boolean wasVisitedPreviously;
	//Group all the rooms together by TopFloor, Bottom Floor, and stuff

	// Empty Constructor
	Room() {
	}

	// Room Constructor without isCurrentRoom
	// Used when starting a new game
	Room(String newroomID, String roomName, String newroomDescription, String newroomToTheNorth, String newroomToTheSouth, String newroomToTheEast,
			String newroomToTheWest,String[] itemID,String puzzleID, String floor) {
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
		//this.inventory = new ArrayList<Item>();
		this.monsterList = new HashMap<String, Monster>();
		this.puzzle = null;
	}
	
	// Room Constructor with isCurrentRoom
	// Used when loading a saved file
	Room(String newroomID, String roomName, String newroomDescription, String newroomToTheNorth, String newroomToTheSouth, String newroomToTheEast,
			String newroomToTheWest,String[] itemID,String puzzleID,String floor, boolean isThisCurrentRoom) {
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
		//this.inventory = new ArrayList<Item>();
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

	public void setroomToTheNorth(String newroomToTheNorth) {
		roomToTheNorth = newroomToTheNorth;
	}

	public void setroomToTheSouth(String newroomToTheSouth) {
		roomToTheSouth = newroomToTheSouth;
	}

	public void setroomToTheEast(String newroomToTheEast) {
		roomToTheEast = newroomToTheEast;
	}

	public void setroomToTheWest(String newroomToTheWest) {
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

	public String getroomToTheNorth() {
		return roomToTheNorth;
	}

	public String getroomToTheSouth() {
		return roomToTheSouth;
	}

	public String getroomToTheEast() {
		return roomToTheEast;
	}

	public String getroomToTheWest() {
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
	
	public boolean isWasVisitedPreviously() {
		return wasVisitedPreviously;
	}

	public void setWasVisitedPreviously(boolean wasVisitedPreviously) {
		this.wasVisitedPreviously = wasVisitedPreviously;
	}

	public void addItem(Item item)
	{
		this.inventory.put(item.getItemName(), item);
		//this.inventory.add(item);
	}
	/*
	public void addItem(String name)
	{
		Item item = 
	}*/
	
	public void removeItem(Item item)
	{
		this.inventory.remove(item.getItemName());
	}
	
	public void addMonster(Monster monster)
	{
		this.monsterList.put(monster.getMonsterName(), monster);
	}
	
	public void removemonster(Monster monster)
	{
		this.monsterList.remove(monster.getMonsterName());
	}
	
	//public LinkedList<String> getExits()
	public HashMap<String, String> getExits()
	{
		//Getting exits so Navi Commands work
		HashMap<String, String> exits = new HashMap<String, String>();
		exits.put("N", this.getroomToTheNorth());
		exits.put("S", this.getroomToTheSouth());
		exits.put("E", this.getroomToTheEast());
		exits.put("W", this.getroomToTheWest());
		/*
		LinkedList<String> rooms = new LinkedList<String>();
		rooms.add(this.getroomToTheNorth());
		rooms.add(this.getroomToTheSouth());
		rooms.add(this.getroomToTheEast());
		rooms.add(this.getroomToTheWest());
		*/
		return exits;
	}
	
	/* examineRoom()
	 * If the user inputs "Examine Room", 
	 */
	public void examineRoom()
	{
		System.out.println(this.getRoomDescription());
	}
	
	/* examine()
	 * If the user types "Examine", the game tells the user what items are in the room's inventory.
	 */
	public void examineInventory()
	{
		if(this.inventory.size() == 0)
		{
			System.out.println("There are no items in this room.");
			return;
		}
		else if(this.inventory.size() == 1)
		{
			for(Entry<String, Item> entry : this.inventory.entrySet())
			{
				System.out.println("There is a " + entry.getValue().getItemName() + " here.");
			}
			return;
		}
		else
		{
			String items = "";
			for(Map.Entry<String, Item> entry : inventory.entrySet())
			{
				items = items + entry.getKey() + ", ";
			}
			System.out.println("The following items are in " + this.getRoomName() + ": " + items.substring(0, items.length() - 2));
		}
		
	}
	
	public void printMonsters()
	{
		if(this.monsterList.size() == 0)
		{
			System.out.println("There are no monsters in this room.");
		}
		else if(this.monsterList.size() == 1)
		{
			for(Entry<String, Monster> entry : this.monsterList.entrySet())
			{
				System.out.println("There is a " + entry.getValue().getMonsterName() + " here.");
			}
			return;
		}
		else
		{
			String monsters = "";
			for(Entry<String, Monster> entry : this.getMonsterList().entrySet())
			{
				monsters = monsters + entry.getKey() + ", ";
			}
			System.out.println("The following monsters are in " + this.getRoomName() + ": " + monsters.substring(0, monsters.length() - 2));
		}
	}
}
