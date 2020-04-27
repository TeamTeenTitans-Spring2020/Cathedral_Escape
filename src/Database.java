import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Database {
	
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
	public Player player;
	public Scanner scan;
	
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
	{
		Database db = new Database();
		this.itemList = new HashMap<String, Item>();
		this.itemListNum = new HashMap<String, Item>();
		this.monsterList = new HashMap<String, Monster>();
		this.puzzleList = new HashMap<String, Puzzle>();
		this.roomList = new HashMap<String, Room>();

		if(fileNum.equals("") || fileNum.equals("1") || fileNum.equals("2") || fileNum.equals("3"))
		{
			try
			{
				String file = "items";
				FileReader fr = new FileReader(file);
				scan = new Scanner(fr);
				while(scan.hasNextLine())
				{
					String[] text = scan.nextLine().split(";");
					Item item = new Item(text[0], text[1], text[2], text[3], text[4], Integer.parseInt(text[5]));
					itemList.put(item.getItemName(), item);
					itemListNum.put(item.getItemID(), item);
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

					String[] solArray = sol.split(",");
					
					ArrayList<String> solutions = new ArrayList<String>();
					for(String line : solArray)
					{
						solutions.add(line);
					}
					
					Puzzle puzzle = new Puzzle(id, room, prompt, hint, solutions, reward, dmg, itemID, isSolved);
					if(!(itemID.equalsIgnoreCase("0")))
					{
						puzzle.setItem(itemListNum.get(itemID));
					}
					puzzleList.put(puzzle.getPuzzleID(), puzzle);
				}
			}
			catch(FileNotFoundException fr)
			{
				System.out.println("Failed to read Puzzles");
			}
			
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
					
					Item item = itemList.get(itemID);
					
					Monster monster = new Monster(id, name, desc, rooms, aprRateI, item, dropRate, hp, atk);
					monsterList.put(monster.getMonsterName(), monster);
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
				}
			}
			catch(FileNotFoundException fr)
			{
				System.out.println("Failed to read Monsters");
			}
			
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
					
					ArrayList<Item> inventory = new ArrayList<Item>();
					
					if(items.contains(";"))
					{
						String[] itemArray = items.split(";");
						for(String line : itemArray)
						{
							if(!(items.contains(",")))
							{
								Item item = db.getItemList().get(line);
								if(db.getItemList().containsKey(line))
								{
									inventory.add(item);
								}
							}
							else
							{
								String[] lineArray2 = line.split(",");
								if(db.getItemList().containsKey(items))
								{
									Item item = db.getItemList().get(lineArray2[0]);
									item.setUseValue(Integer.parseInt(lineArray2[1]));
									inventory.add(item);
								}
							}
						}
					}
					else
					{
						if(!(items.contains(",")))
						{
							Item item = db.getItemList().get(items);
							if(db.getItemList().containsKey(items))
							{
								inventory.add(item);
							}
						}
						else
						{
							String[] lineArray2 = items.split(",");
							if(db.getItemList().containsKey(items))
							{
								Item item = db.getItemList().get(lineArray2[0]);
								item.setUseValue(Integer.parseInt(lineArray2[1]));
								inventory.add(item);
							}
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
								break;
							}
						}
					}
					this.player = new Player(inventory, room, hp, atk, def, weapon, armor);
				}
			}
			catch(FileNotFoundException fr)
			{
				System.out.println("Failed to read Rooms");
			}
		}
		else
		{
			System.out.println("Unacceptable Load command. Please use command 'Load1', 'Load2', or 'Load3' to properly load saved data.");
		}
	}
	
	public void saveFiles(String fileNum)
	{
		if(fileNum.equals("1") || fileNum.equals("2") || fileNum.equals("3"))
		{
			System.out.println("Saving puzzles");
			String file = "puzzles" + fileNum;

			for(Map.Entry<String, Puzzle> entry : this.getPuzzleList().entrySet())
			{
				try
				{
					PrintWriter pw = new PrintWriter(file);

					//6;															String ID
					//19;															String RoomID
					//What do you call a bear without an ear?;						String Prompt
					//This has more to do with the word itself than the animal.;	String Hint
					//b,a b,a "b";													String[] solution --> String
					//2;															int reward --> String
					//2;															int dmg --> String
					//14;															String itemID
					//false															boolean isSolved --> String

					int filesScanned = 0;
					while(filesScanned < this.getPuzzleList().size())
					{
						Puzzle puzzle = entry.getValue();
						String id = puzzle.getItemID();
						String roomID = puzzle.getRoomLocation();
						String prompt = puzzle.getPuzzlePrompt();
						String hint = puzzle.getPuzzleHint();
						ArrayList<String> sol = puzzle.getPuzzleSolution();
						String reward = String.valueOf(puzzle.getPuzzleReward());
						String dmg = String.valueOf(puzzle.getPuzzleDamage());

						String itemID = puzzle.getItem().getItemID();


						String isSolved = String.valueOf(puzzle.isSolved());

						pw.print(id + ";");
						pw.print(roomID + ";");
						pw.print(prompt + ";");
						pw.print(hint + ";");

						String solutions = "";
						for(String line : sol)
						{
							solutions = solutions + sol + ",";
						}
						solutions = solutions.substring(0, solutions.length() - 1);
						pw.println(solutions + ";");

						pw.print(reward + ";");
						pw.print(dmg + ";");
						pw.print(itemID + ";");
						pw.println(isSolved);
						filesScanned++;
					}
					pw.close();
				}
				catch(FileNotFoundException fe)
				{
					System.out.println("File " + file + " failed to save");
				}
			}


			System.out.println("Saving rooms");
			file = "rooms" + fileNum;
			for(Map.Entry<String, Room> entry : this.getRoomList().entrySet())
			{
				try
				{
					PrintWriter pw = new PrintWriter(file);
					//26;				String id
					//Servant Quarters;	String name
					//Burning torches;	String desc
					//0;				String n
					//31;				String s
					//25;				String e
					//28;				String w
					//0;				String[] itemIDs --> String
					//7;				String puzzleID
					//Basement			String floor

					int filesScanned = 0;
					while(filesScanned < this.getRoomList().size())
					{
						Room room = entry.getValue();
						String id = room.getRoomID();
						String name = room.getRoomName();
						String n = room.getroomToTheNorth();
						String s = room.getroomToTheSouth();
						String e = room.getroomToTheEast();
						String w = room.getroomToTheWest();
						String[] itemIDs = room.getItemID();
						String puzzleID = room.getpuzzleID();
						String floor = room.getFloor();

						pw.print(id + ";");
						pw.print(name + ";");
						pw.print(n + ";");
						pw.print(s + ";");
						pw.print(e + ";");
						pw.print(w + ";");

						String items = "";
						for(String line : itemIDs)
						{
							items = items + line + ",";
						}
						items = items.substring(0, items.length() - 1);
						pw.print(items);

						pw.print(puzzleID + ";");
						pw.println(floor);
						filesScanned++;
					}

					pw.close();
				}
				catch(FileNotFoundException fe)
				{
					System.out.println("File " + file + " failed to save");
				}
			}

			file = "Player" + fileNum;
			System.out.println("Saving Player");

			try
			{
				System.out.println("File name: " + file);
				PrintWriter pw = new PrintWriter(file);

				//Inventory		ArrayList<Item> --> String
				//curRoom		Room.getID --> String
				//hp			int --> String
				//atk			int --> String
				//def			int --> String
				//weapon,atk	Item --> String
				//armor,def		Item --> String
				//inCombat		boolean --> String
				//monster		Monster --> String

				ArrayList<Item> inv = player.getInventory();
				String curRoom = player.getCurrentRoom().getRoomID();
				String hp = String.valueOf(player.getHp());
				String atk = String.valueOf(player.getAtkDmg());
				String def = String.valueOf(player.getDef());
				String weapon = player.getWeaponName() + "," + player.getWeaponValue();
				String armor = player.getArmorName() + "," + player.getArmorValue();

				if(weapon.length() == 1)
				{
					weapon = "";
				}
				if(armor.length() == 1)
				{
					armor = "";
				}

				String inventory = "";
				if(inv.size() > 0)
				{
					for(Item item : inv)
					{
						String line = item.getItemName();
						inventory = inventory + line + ",";
					}
				}

				pw.println(inventory);
				pw.println(curRoom);
				pw.println(hp);
				pw.println(atk);
				pw.println(def);
				pw.println(weapon);
				pw.println(armor);
				pw.close();

			}
			catch(FileNotFoundException fe)
			{
				System.out.println("File " + file + " failed to save");
			}
		}
		else
		{
			System.out.println("Unacceptable Save command. Please use command 'Save1', 'Save2', or 'Save3' to properly save data.");
		}
	}
}
