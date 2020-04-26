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
	int hp;//Maximum hp is 100.
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
				System.out.println("You have a " + item);
			}
		}
		else
		{
			String items = "";
			for(Item entry : inventory)
			{
				items = items + entry + ", ";
			}
			System.out.println("You have the following items in your inventory: " + items.substring(0, items.length() - 2));
		}
	}
	
	public void attack(Monster monster)
	{
		int hitNum = (int)(Math.random() * 100);
		System.out.println("Hit Num: " + hitNum);
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
		System.out.println("Escape Roll (0 - 99): " + escChance);
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
	
	//TO DO
	//Should I put in Main for entering a Room/using Navigation commands???
	public void enterRoom(Room room, Database db, Scanner input)
	{
		this.setCurrentRoom(room);
		boolean encounter = true;
		//while(encounter)
		//{
		for(Entry<String, Monster> entry  : room.getMonsterList().entrySet())
		{
			Monster monster = entry.getValue();
			int index = 0;
			/*
				for(int i = 0; i < monster.getRoom().length; i++)
				{
					if(monster.getRoom()[i] == room.getRoomName())
					{
						index = i;
						break;
					}
				}
			 */
			int encNum = (int)(Math.random() * 100);
			System.out.println("Encounter Num: " + encNum);
			int monsterAprRate = monster.getAppearanceChance().get(index);
			if(encNum <= monsterAprRate)
			{
				//TO DO
				//FIND OUT WHAT TO DO HERE
				this.combat(monster, db, input);
				encounter = false;
				break;
			}
		}
		//}
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
	
	public void combat(Monster monster, Database db, Scanner input)
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
				//monster = db.monsterList.get(currentRoom.getRoomID());
				//monster = db.monsterList.get(currentRoom.getRoomID());
				//monster = monsterMap.get(currentRoom.getRoomNum());
				this.attack(monster);
				monster.monsterAtk(this);
				/*
				if(monster.getHp() > 0)
				{
					monster.monsterAtk(this);
				}
				*/
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
				
				if(firstWord.equalsIgnoreCase("Equip"))
				{
					if(this.getInventory().contains(secondWord))
					{
						int index = this.getInventory().indexOf(secondWord);
						Item item = this.getInventory().get(index);
						item.equipItem(this);
						successfulAction = true;
					}
					else
					{
						System.out.println("Failed to equip item. Either it is not in your inventory or was mistyped. If item was misspelled, make sure to spell it exactly\n"
								+ "as it appears when typed in the 'Examine' description for the room or the 'Examine Inventory' description.");
					}
				}
				else if(firstWord.equalsIgnoreCase("Unequip"))
				{
					if(this.getInventory().contains(secondWord))
					{
						int index = this.getInventory().indexOf(secondWord);
						Item item = this.getInventory().get(index);
						item.unequipItem(this);
						successfulAction = true;
					}
					else
					{
						System.out.println("Failed to unequip item. Either it is not in your inventory, not already equipped, or was mistyped. If item was misspelled, make\n"
								+ "sure to spell it exactly as it appears when typed in the 'Examine' description for the room or the 'Examine Inventory' description.");
					}
				}
				else if(firstWord.equalsIgnoreCase("Pickup"))
				{
					//int index = this.getInventory().indexOf(secondWord);
					//Item item = this.getInventory().get(index);
					//Item item = this.getInventory.get(secondWord);
					//Pick up from Room's inventory
					//if(this.getInventory().contains(secondWord))
					if(this.getCurrentRoom().getInventory().containsKey(secondWord))
					{
						Item item = db.itemList.get(secondWord);
						item.pickupItem(this, this.getCurrentRoom());
						successfulAction = true;
					}
					else
					{
						System.out.println("Failed to pickup item. Either it is not in the room, doesn't exist, or was mistyped. If item was misspelled, make sure to spell it\n"
								+ "exactly as it appears when typed in the 'Examine' description for the room or the 'Examine Inventory' description.");
					}
				}
				else if(firstWord.equalsIgnoreCase("Use"))
				{
					if(this.getInventory().contains(secondWord))
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
	}
}
