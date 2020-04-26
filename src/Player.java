import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public void escape()
	{
		int escChance = (int)(Math.random() * 100);
		System.out.println("Escape Roll (0 - 99): " + escChance);
		if(escChance > 49)
		{
			System.out.println("You escaped the monster!");
			inCombat = false;
		}
	}
	
	//TO DO
	//Should I put in Main for entering a Room/using Navigation commands???
	public void enterRoom(Room room)
	{
		this.setCurrentRoom(room);
		boolean encounter = true;
		while(encounter)
		{
			for(Entry<String, Monster> entry  : room.getMonsterList().entrySet())
			{
				Monster monster = entry.getValue();
				int index = 0;
				for(int i = 0; i < monster.getRoom().length; i++)
				{
					if(monster.getRoom()[i] == room.getRoomName())
					{
						index = i;
						break;
					}
				}
				int encNum = (int)(Math.random() * 100);
				int monsterAprRate = monster.getAppearanceChance().get(index);
				if(encNum <= monsterAprRate)
				{
					//TO DO
					//FIND OUT WHAT TO DO HERE
					this.combat(monster);
					encounter = false;
				}
			}
		}
	}
	
	
	//TO DO
	//Should I put in Main as entering a room???
	/*
	public void combat(Monster monster)
	{
		boolean monsterAlive = true;
		System.out.println("You were attacked by a " + monster.getMonsterName() + "!");
		while(monsterAlive)
		{
			
		}
		fail;
	}
	*/
}
