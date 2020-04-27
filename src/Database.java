import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Database {
	//public HashMap<String, Item> itemList = new HashMap<String, Item>();
	private HashMap<String, Item> itemList = new HashMap<String, Item>();
	public HashMap<String, Item> itemListNum = new HashMap<String, Item>();
	public HashMap<String, Monster> monsterList = new HashMap<String, Monster>();
	public HashMap<String, Puzzle> puzzleList = new HashMap<String, Puzzle>();//0 Puzzle doesn't exist
	private HashMap<String, Room> roomList = new HashMap<String, Room>();
	
	private ArrayList<Monster> topRoom = new ArrayList<Monster>();
	private ArrayList<Monster> secondRoom = new ArrayList<Monster>();
	private ArrayList<Monster> firstRoom = new ArrayList<Monster>();
	private ArrayList<Monster> baseRoom = new ArrayList<Monster>();
	private ArrayList<Monster> hallRoom = new ArrayList<Monster>();
	private ArrayList<Monster> cathRoom = new ArrayList<Monster>();
	public Player player;//If an Item's Room is 0, add it to player.addInventory()
	public Scanner scan;
	
	
	//BOOKMARK
	//When loading a save, then create a new Database???
	//public Database(Room room)
	public Database() {
		this.itemList = itemList;
		this.itemListNum = itemListNum;
		this.monsterList = monsterList;
		this.puzzleList = puzzleList;
		this.roomList = roomList;
		this.topRoom = topRoom;
		this.secondRoom = secondRoom;
		this.firstRoom = firstRoom;
		this.baseRoom = baseRoom;
		this.hallRoom = hallRoom;
		this.cathRoom = cathRoom;
		this.player = new Player(roomList.get("1"));
	}


	public void addItem(Item item)
	{
		itemList.put(item.getItemName(), item);
	}
	
	public void removeItem(Item item)
	{
		itemList.remove(item.getItemName());
	}
	
	public void addmonster(Monster monster)
	{
		monsterList.put(monster.getMonsterName(), monster);
	}
	
	public void removemonster(Monster monster)
	{
		monsterList.remove(monster.getMonsterName());
	}
	
	public void addpuzzle(Puzzle puzzle)
	{
		puzzleList.put(puzzle.getPuzzleID(), puzzle);
	}
	
	public void removepuzzle(Puzzle puzzle)
	{
		puzzleList.remove(puzzle.getPuzzleID());
	}
	
	public void addroom(Room room)
	{
		roomList.put(room.getRoomName(), room);
	}
	
	public void removeroom(Room room)
	{
		roomList.remove(room.getRoomName());
	}
	
	public HashMap<String, Item> getItemList() {
		return itemList;
	}

	public void setItemList(HashMap<String, Item> itemList) {
		this.itemList = itemList;
	}

	public HashMap<String, Item> getItemListNum() {
		return itemListNum;
	}

	public void setItemListN(HashMap<String, Item> itemListNum) {
		this.itemListNum = itemListNum;
	}

	public HashMap<String, Monster> getMonsterList() {
		return monsterList;
	}

	public void setMonsterList(HashMap<String, Monster> monsterList) {
		this.monsterList = monsterList;
	}

	public HashMap<String, Puzzle> getPuzzleList() {
		return puzzleList;
	}

	public void setPuzzleList(HashMap<String, Puzzle> puzzleList) {
		this.puzzleList = puzzleList;
	}

	public HashMap<String, Room> getRoomList() {
		return roomList;
	}

	public void setRoomList(HashMap<String, Room> roomList) {
		this.roomList = roomList;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void readFiles(String fileNum)
	//public Database readFiles(String fileNum)
	{
		Database db = new Database();
		itemList = new HashMap<String, Item>();
		itemListNum = new HashMap<String, Item>();
		monsterList = new HashMap<String, Monster>();
		puzzleList = new HashMap<String, Puzzle>();//0 Puzzle doesn't exist
		roomList = new HashMap<String, Room>();
		//player = new Player(roomList.get("1"));
		//readItems int itemID[0], name[1], desc[2], type[3], action[4], int value[5]
		//Integer.parseInt(text[0])
		//for(Map.Entry<String, Item> entry : itemList.entrySet())
		if(fileNum.equals("") || fileNum.equals("1") || fileNum.equals("2") || fileNum.equals("3"))
		{
			try
			{
				String file = "items" + fileNum;
				FileReader fr = new FileReader(file);
				scan = new Scanner(fr);
				while(scan.hasNextLine())
				{
					String[] text = scan.nextLine().split(";");
					Item item = new Item(text[0], text[1], text[2], text[3], text[4], Integer.parseInt(text[5]));
					itemList.put(item.getItemName(), item);
					itemListNum.put(item.getItemID(), item);
					//itemList.put(item.getItemName(), item);
				}
			}
			catch(FileNotFoundException fr)
			{
				System.out.println("Failed to read Items");
			}

			

			//readPuzzle
			try
			{
				String file = "puzzles" + fileNum;
				FileReader fr = new FileReader(file);
				scan = new Scanner(fr);
				while(scan.hasNextLine())
				{
					String[] text = scan.nextLine().split(";");
					String id = text[0];
					String room = text[1];
					String prompt = text[2];
					String hint = text[3];
					String sol = text[4];
					int reward = Integer.parseInt(text[5]);
					int dmg = Integer.parseInt(text[6]);
					String itemID = text[7];
					boolean isSolved = Boolean.parseBoolean(text[8]);

					String[] solutions = sol.split(",");
					
					Puzzle puzzle = new Puzzle(id, room, prompt, hint, solutions, reward, dmg, itemID, isSolved);
					puzzle.setItem(itemListNum.get(itemID));
					puzzleList.put(puzzle.getPuzzleID(), puzzle);
				}
			}
			catch(FileNotFoundException fr)
			{
				System.out.println("Failed to read Puzzles");
			}
			
			/*
			for(Map.Entry<String, Item> entry : itemList.entrySet())
			{
				System.out.println("Got " + entry.getValue().getItemName());
			}
			*/
			
			//readMonster int ID[0], name[1], desc[2], String[] room[3], int[] aprRate[4], droppedItem[5], int dropChance[6], int hp[7], int atk[8]
			try
			{
				String file = "monsters" + fileNum;
				FileReader fr = new FileReader(file);
				scan = new Scanner(fr);
				while(scan.hasNextLine())
				{
					String[] text = scan.nextLine().split(";");
					String id = text[0];
					String name = text[1];
					String desc = text[2];
					String floors = text[3];
					String apr = text[4];
					String itemID = text[5];
					int dropRate = Integer.parseInt(text[6]);
					int hp = Integer.parseInt(text[7]);
					int atk = Integer.parseInt(text[8]);

					String[] rooms = {""};

					if(floors.contains(","))
					{
						rooms = floors.split(",");
					}
					else
					{
						rooms[0] = floors;
					}

					String[] aprRateS = apr.split(",");
					ArrayList<Integer> aprRateI = new ArrayList<Integer>();
					for(int i = 0; i < aprRateS.length; i++)
					{
						aprRateI.add(Integer.parseInt(aprRateS[i]));
					}
					
					//System.out.println(itemID);
					Item item = itemList.get(itemID);
					//System.out.println(name);
					
					Monster monster = new Monster(id, name, desc, rooms, aprRateI, item, dropRate, hp, atk);
					monsterList.put(monster.getMonsterName(), monster);
					
					//Iterating though floors to put the Monsters in the FloorList
					/*
					private ArrayList<Monster> topRoom = new ArrayList<Monster>();
					private ArrayList<Monster> secondRoom = new ArrayList<Monster>();
					private ArrayList<Monster> firstRoom = new ArrayList<Monster>();
					private ArrayList<Monster> baseRoom = new ArrayList<Monster>();
					*/
					for(String line : rooms)
					{
						if(line.equalsIgnoreCase("Top Floor"))
						{
							topRoom.add(monster);
						}
						else if(line.equalsIgnoreCase("1st Floor"))
						{
							firstRoom.add(monster);
						}
						else if(line.equalsIgnoreCase("2nd Floor"))
						{
							secondRoom.add(monster);
						}
						else if(line.equalsIgnoreCase("Basement"))
						{
							baseRoom.add(monster);
						}
						else if(line.equalsIgnoreCase("Hallway"))
						{
							hallRoom.add(monster);
						}
						else if(line.equalsIgnoreCase("Cathedral"))
						{
							cathRoom.add(monster);
						}
					}
					/*
					for(Map.Entry<String, Room> entry : roomList.entrySet())
					{
						Room room = entry.getValue();
						for(int i = 0; i < rooms.length; i++)
						{
							if(entry.getKey().equalsIgnoreCase(rooms[i]))
							{
								room.addMonster(monster);
							}
						}
						roomList.put(room.getRoomName(), room);
					}
					*/
				}
			}
			catch(FileNotFoundException fr)
			{
				System.out.println("Failed to read Monsters");
			}
			
			//TO DO
			//Properly set all the Arrays[]
			//readRooms int index[0], name[1], desc[2], int N[3], int S[4], int E[5], int W[6], int itemID[7], int puzzleID[8], floor[9]
			try
			{
				String file = "rooms" + fileNum;
				FileReader fr = new FileReader(file);
				scan = new Scanner(fr);
				while(scan.hasNextLine())
				{
					String[] text = scan.nextLine().split(";");
					String id = text[0];
					String name = text[1];
					String desc = text[2];
					String north = text[3];
					String south = text[4];
					String east = text[5];
					String west = text[6];
					String itemIDList = text[7];
					String puzzleID = text[8];
					String floor = text[9];

					String[] itemID = {"0"};

					if(itemIDList.contains(","))
					{
						itemID = itemIDList.split(",");
					}
					else
					{
						itemID[0] = itemIDList;
					}


					Room room = new Room(id, name, desc, north, south, east, west, itemID, puzzleID, floor);

					//set Room's Inventory(Multiple Items seperated by a comma), Monsters (Multiple Monsters seperate by a comma), Puzzle
					//SET MONSTERS AT MONSTER???

					//room.setInventory();
					for(String key : itemID)
					{
						if(!(key.equalsIgnoreCase("0")))
						{
							Item item = itemListNum.get(key);
							room.addItem(item);
						}
					}

					if(!(puzzleID.equalsIgnoreCase("0")))
					{
						Puzzle puzzle = puzzleList.get(puzzleID);
						room.setPuzzle(puzzle);
					}
					
					//Iterating though floors to put the ArrayList of Monsters to Room.addMosnter(monster) them
					/*
					private ArrayList<Monster> topRoom = new ArrayList<Monster>();
					private ArrayList<Monster> secondRoom = new ArrayList<Monster>();
					private ArrayList<Monster> firstRoom = new ArrayList<Monster>();
					private ArrayList<Monster> baseRoom = new ArrayList<Monster>();
					*/
					if(floor.equalsIgnoreCase("Top Floor"))
					{
						for(Monster monster : topRoom)
						{
							room.addMonster(monster);
						}
					}
					else if(floor.equalsIgnoreCase("1st Floor"))
					{
						for(Monster monster : firstRoom)
						{
							room.addMonster(monster);
						}
					}
					else if(floor.equalsIgnoreCase("2nd Floor"))
					{
						for(Monster monster : secondRoom)
						{
							room.addMonster(monster);
						}
					}
					else if(floor.equalsIgnoreCase("Basement"))
					{
						for(Monster monster : baseRoom)
						{
							room.addMonster(monster);
						}
					}
					else if(floor.equalsIgnoreCase("Hallway"))
					{
						for(Monster monster : hallRoom)
						{
							room.addMonster(monster);
						}
					}
					else if(floor.equalsIgnoreCase("Cathedral"))
					{
						for(Monster monster : cathRoom)
						{
							room.addMonster(monster);
						}
					}

					roomList.put(room.getRoomID(), room);
				}
			}
			catch(FileNotFoundException fr)
			{
				System.out.println("Failed to read Rooms");
			}
			
			
			
			//readPlayer
			//ArrayList[0], String Room currentRoom[1], int hp[2], int atkDmg[3], int def[4], ;;;String[] Item equippedWeapon[5];;;, String<Item> equippedArmor[6],
			//boolean inCombat[7], ;;;String[] Monster monster[8];;;
			try
			{
				String file = "Player" + fileNum;
				FileReader fr = new FileReader(file);
				scan = new Scanner(fr);
				while(scan.hasNextLine())
				{
					String items = scan.nextLine();
					String roomNum = scan.nextLine();
					int hp = Integer.parseInt(scan.nextLine());
					int atk = Integer.parseInt(scan.nextLine());
					int def = Integer.parseInt(scan.nextLine());
					String weaponName = scan.nextLine();
					String armorName = scan.nextLine();
					boolean inCombat = Boolean.parseBoolean(scan.nextLine());
					String monsterName = scan.nextLine();
					
					/*
					System.out.println("Item: " + items);
					System.out.println("Room num: " + roomNum);
					System.out.println("HP: " + hp);
					System.out.println("Atk: " + atk);
					System.out.println("Def: " + def);
					System.out.println("Weapon: " + weaponName);
					System.out.println("Armor: " + armorName);
					System.out.println("Is in combat: " + inCombat);
					System.out.println("Monster: " + monsterName);
					*/
					
					//HashMap<String, Item> inventory = new HashMap<String, Item>();
					ArrayList<Item> inventory = new ArrayList<Item>();
					
					if(items.contains(";"))
					{
						String[] itemArray = items.split(";");
						for(String line : itemArray)
						{
							if(!(items.contains(",")))
							{
								Item item = db.getItemList().get(line);
								//inventory.add(item);
								if(db.getItemList().containsKey(line))
								{
									inventory.add(item);
								}
								//inventory.put(items, item);
							}
							else
							{
								String[] lineArray2 = line.split(",");
								//Item item = db.getItemList().get(lineArray2[0]);
								//item.setUseValue(Integer.parseInt(lineArray2[1]));
								//inventory.add(item);
								if(db.getItemList().containsKey(items))
								{
									Item item = db.getItemList().get(lineArray2[0]);
									item.setUseValue(Integer.parseInt(lineArray2[1]));
									inventory.add(item);
								}
								//inventory.put(item.getItemName(), item);
							}
						}
					}
					else
					{
						if(!(items.contains(",")))
						{
							Item item = db.getItemList().get(items);
							//inventory.add(item);
							if(db.getItemList().containsKey(items))
							{
								inventory.add(item);
							}
							//inventory.put(item.getItemName(), item);
						}
						else
						{
							String[] lineArray2 = items.split(",");
							//Item item = db.getItemList().get(lineArray2[0]);
							//item.setUseValue(Integer.parseInt(lineArray2[1]));
							//inventory.add(item);
							if(db.getItemList().containsKey(items))
							{
								Item item = db.getItemList().get(lineArray2[0]);
								item.setUseValue(Integer.parseInt(lineArray2[1]));
								inventory.add(item);
							}
							//inventory.put(item.getItemName(), item);
						}
					}
					
					Room room = roomList.get(roomNum);

					Item weapon = null;
					if(weaponName.contains(","))
					{
						String[] weaponArray = items.split(";");
						for(int i = 0; i < inventory.size(); i++)
						{
							if(inventory.get(i).getItemName().equals(weaponArray[0]))
							{
								weapon = inventory.get(i);
								/*
								weapon.setUseValue(Integer.parseInt(weaponArray[1]));
								inventory.remove(i);
								inventory.add(weapon);
								*/
								break;
							}
						}
					}
					
					Item armor = null;
					if(armorName.contains(","))
					{
						String[] armorArray = items.split(";");
						for(int i = 0; i < inventory.size(); i++)
						{
							if(inventory.get(i).getItemName().equals(armorArray[0]))
							{
								armor = inventory.get(i);
								/*
								armor.setUseValue(Integer.parseInt(armorArray[1]));
								inventory.remove(i);
								inventory.add(armor);
								*/
								break;
							}
						}
					}
					
					Monster monster = null;
					if(monsterList.containsKey(monsterName))
					{
						monster = monsterList.get(monsterName);
					}
					
					//this.player = new Player(inventory, room, hp, atk, def, weapon, armor, inCombat, monster);
					player = new Player(inventory, room, hp, atk, def, weapon, armor, inCombat, monster);
				}
			}
			catch(FileNotFoundException fr)
			{
				System.out.println("Failed to read Rooms");
			}
			
			//System.out.println("Files read");
		}
		else
		{
			System.out.println("Unacceptable Load command. Please use command 'Load1', 'Load2', or 'Load3' to properly load saved data.");
		}
		
		//return db;
	}
	
	public void saveFiles(String fileNum)
	{
		if(fileNum.equals("1") || fileNum.equals("2") || fileNum.equals("3"))
		{
			
		}
		else
		{
			System.out.println("Unacceptable Save command. Please use command 'Save1', 'Save2', or 'Save3' to properly save data.");
		}
	}
	
	public void eraseLists()
	{
		//erase;
	}
}
