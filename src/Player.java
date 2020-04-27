import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Player {
	//Create a Player.txt??? Allows you to see what the Player's Inventory is
	
	//HashMap<String, Item> inventory;
	ArrayList<Item> inventory;//Read the String names. If the Weapon/Sword has a "," then setAtk to the int
	Room currentRoom;//Read this as an integer. It'll get the Room based on the Room Number
	int hp;//Maximum hp Items can heal to is 100. However, Puzzles can give hp above 100.
	int atkDmg;
	int def;
	Item equippedWeapon;//Read this as a String name. If the Weapon/Sword has a "," then setAtk to the int
	Item equippedArmor;
	boolean inCombat;
	Monster monster;
	
	/* Room(Room currentRoom)
	 * Room currentRoom- The Room the player is currently in.
	 */
	Player(Room room)
	{
		this.inventory = new ArrayList<Item>();
		this.currentRoom = room;
		this.hp = 100;
		this.atkDmg = 10;
		this.def = 0;
		this.equippedWeapon = null;
		this.equippedArmor = null;
		this.inCombat = false;
	}
	

	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAtkDmg() {
		return atkDmg;
	}
	
	public void setAtkDmg(int atkDmg) {
		this.atkDmg = atkDmg;
	}
	
	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}
	
	public Item getEquippedWeapon() {
		return equippedWeapon;
	}


	public void setEquippedWeapon(Item equippedWeapon) {
		this.equippedWeapon = equippedWeapon;
	}


	public Item getEquippedArmor() {
		return equippedArmor;
	}


	public void setEquippedArmor(Item equippedArmor) {
		this.equippedArmor = equippedArmor;
	}


	public boolean isInCombat() {
		return inCombat;
	}
	
	public void addItem(Item item)
	{
		inventory.add(item);
	}
	
	public void removeItem(Item item)
	{
		inventory.remove(item);
	}

	public void setInCombat(boolean inCombat) {
		this.inCombat = inCombat;
	}

	public void examineInventory() {
		if(inventory.size() == 0)
		{
			System.out.println("You have no items in your inventory.");
		}
		else if(inventory.size() == 1)
		{
			//for(Map.Entry<String, Item> entry : inventory.entrySet())
			for(Item item : inventory)
			{
				System.out.println("You have a " + item.getItemName());
			}
		}
		else
		{
			String items = "";
			for(Item entry : inventory)
			{
				items = items + entry.getItemName() + ", ";
			}
			System.out.println("You have the following items in your inventory: " + items.substring(0, items.length() - 2));
		}
	}
	
	public void attack(Monster monster)
	{
		int hitNum = (int)(Math.random() * 100);
		//System.out.println("Hit Num: " + hitNum);
		if(hitNum > 9)
		{
			System.out.println("You attack the monster for " + this.atkDmg + " damage!");
			monster.setHp(monster.getHp() - this.atkDmg);
			if(monster.getHp() <= 0)
			{
				System.out.println("You have defeated the " + monster.getMonsterName() + "!");
				this.setInCombat(false);
			}
		}
		else
		{
			System.out.println("You missed!");
		}
	}
	
	public boolean escape()
	{
		int escChance = (int)(Math.random() * 100);
		//System.out.println("Escape Roll (0 - 99): " + escChance);
		if(escChance > 49)
		{
			System.out.println("You escaped the monster!");
			inCombat = false;
			return false;
		}
		else
		{
			System.out.println("You failed to escape!");
			return true;
		}
	}
	
	//public void enterRoom(Room room, Database db, Scanner input)
	public Monster enterRoom(Room room, Database db, Scanner input)
	{
		System.out.println(currentRoom.getRoomName());
		System.out.println(currentRoom.getRoomDescription());
		//this.setCurrentRoom(room);
		Monster monster = db.getMonsterList().get("Human Skeleton");
		for(Entry<String, Monster> entry  : room.getMonsterList().entrySet())
		{
			monster = entry.getValue();
			int index = 0;
			int encNum = (int)(Math.random() * 100);
			//System.out.println("Encounter Num: " + encNum);
			int monsterAprRate = monster.getAppearanceChance().get(index);
			if(encNum <= monsterAprRate)
			{
				this.combat(monster, db, input);
				return monster;
			}
		}
		return monster;
	}
	
	public void printPlayerStats()
	{
		System.out.println("Player HP : " + this.getHp() + "        Player ATK: " + this.getAtkDmg() + "        Player DEF: " + this.getDef());
		String weapon = "None";
		String armor = "None";
		String wAtk = "0";
		String aDef = "0";
		if(this.getEquippedWeapon() != null)
		{
			weapon = this.getEquippedWeapon().getItemName();
			wAtk = String.valueOf(this.getEquippedWeapon().getUseValue());
		}
		if(this.getEquippedArmor() != null)
		{
			armor = this.getEquippedArmor().getItemName();
			aDef = String.valueOf(this.getEquippedArmor().getUseValue());
		}
		System.out.println("Equipped Weapon: " + weapon + "        Equipped Armor: " + armor);
		System.out.println("Weapon ATK: " + wAtk + "        Armor DEF: " + aDef);
	}
	
	//public void combat(Monster monster, Database db, Scanner input)
	public Monster combat(Monster monster, Database db, Scanner input)
	{
		boolean inCombat = true;
		System.out.println("You encountered a " + monster.getMonsterName() + "!");
		System.out.println(monster.getMonsterDescription());
		while(inCombat)
		{
			boolean successfulAction = false;
			System.out.println("Player HP: " + this.getHp() + "        Player Atk: " + this.getAtkDmg());
			System.out.println(monster.getMonsterName() + " HP: " + monster.getHp() + "        " + monster.getMonsterName() + " Atk: " + monster.getAtk());
			System.out.print("> ");
			String command = input.nextLine();
			if (command.equalsIgnoreCase("Attack"))
			{
				this.attack(monster);
				if(monster.getHp() <= 0)
				{
					inCombat = false;
				}
				successfulAction = true;
			}
			else if (command.equalsIgnoreCase("Escape"))
			{
				inCombat = this.escape();
			}
			else if (command.equalsIgnoreCase("Stat"))
			{
				//m.printPlayerStats(player);
				this.printPlayerStats();
			}
			else if(command.contains(" "))
			{
				int spaceIndex = command.indexOf(" ");
				String firstWord = command.substring(0, spaceIndex);
				String secondWord = command.substring(spaceIndex + 1, command.length());
				if(firstWord.equalsIgnoreCase("Use"))
				{
					Item itemObject = db.getItemList().get(secondWord);
					//if(this.getInventory().contains(secondWord))
					if(this.getInventory().contains(itemObject))
					{
						int index = this.getInventory().indexOf(secondWord);
						Item item = this.getInventory().get(index);
						item.useItem(this);
						successfulAction = true;
					}
					else
					{
						System.out.println("Failed to use item. Either it is not in your inventory, cannot be used, or was mistyped. If item was misspelled, make sure\n"
								+ "to spell it exactly as it appears when typed in the 'Examine' description for the room or the 'Examine Inventory' description.");
					}
				}
			}
			
			else
			{
				System.out.println("Invalid input. You are in combat with " + monster.getMonsterName());
			}
			if(successfulAction)
			{
				monster.monsterAtk(this);
			}
		}
		return monster;
	}
}
