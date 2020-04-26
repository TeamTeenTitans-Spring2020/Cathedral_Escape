import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Database {
	public HashMap<String, Item> itemList = new HashMap<String, Item>();//0 Item is in Player inventory. -1 Item is removed??? (Or is already removed)
	public HashMap<String, Monster> monsterList = new HashMap<String, Monster>();
	public HashMap<String, Puzzle> puzzleList = new HashMap<String, Puzzle>();//0 Puzzle doesn't exist
	public HashMap<String, Room> roomList = new HashMap<String, Room>();
	public Player player;//If an Item's Room is 0, add it to player.addInventory()
	public Scanner scan;
	
	
	//BOOKMARK
	//When loading a save, then create a new Database???
	//public Database(Room room)
	public Database() {
		this.itemList = itemList;
		this.monsterList = monsterList;
		this.puzzleList = puzzleList;
		this.roomList = roomList;
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
					itemList.put(item.getItemID(), item);
				}
			}
			catch(FileNotFoundException fr)
			{
				System.out.println("Failed to read Items");
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
				}
			}
			catch(FileNotFoundException fr)
			{
				System.out.println("Failed to read Monsters");
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
					puzzleList.put(puzzle.getPuzzleID(), puzzle);
				}
			}
			catch(FileNotFoundException fr)
			{
				System.out.println("Failed to read Puzzles");
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
							Item item = itemList.get(key);
							room.addItem(item);
						}
					}

					if(!(puzzleID.equalsIgnoreCase("0")))
					{
						Puzzle puzzle = puzzleList.get(puzzleID);
						room.setPuzzle(puzzle);
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


			System.out.println("Files read");
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
			
		}
		else
		{
			System.out.println("Unacceptable Save command. Please use command 'Save1', 'Save2', or 'Save3' to properly save data.");
		}
	}
}
