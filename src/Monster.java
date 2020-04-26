import java.util.ArrayList;

//Take out the Vampire Queen's Dropped item

public class Monster {
	// Creates attributes of the objects
	private String monsterID;
	private String monsterName;
	private String monsterDescription;
	private String[] room;
	private ArrayList<Integer> appearanceChance;
	private Item itemDropped;
	private int itemDropChance;
	private int hp;
	private int atk;
	
	public Monster(String monsterID, String monsterName, String monsterDescription, String[] room, ArrayList<Integer> appearanceChance,
			Item itemDropped, int itemDropChance, int hp, int atk) {
		this.monsterID = monsterID;
		this.monsterName = monsterName;
		this.monsterDescription = monsterDescription;
		this.appearanceChance = appearanceChance;
		this.itemDropped = itemDropped;
		this.itemDropChance = itemDropChance;
		this.hp = hp;
		this.atk = atk;
	}
	public String getMonsterID() {
		return monsterID;
	}
	public void setMonsterID(String monsterID) {
		this.monsterID = monsterID;
	}
	public String getMonsterName() {
		return monsterName;
	}
	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}
	public String getMonsterDescription() {
		return monsterDescription;
	}
	public void setMonsterDescription(String monsterDescription) {
		this.monsterDescription = monsterDescription;
	}
	public ArrayList<Integer> getAppearanceChance() {
		return appearanceChance;
	}
	public void setAppearanceChance(ArrayList<Integer> appearanceChance) {
		this.appearanceChance = appearanceChance;
	}
	public Item getItemDropped() {
		return itemDropped;
	}
	public void setItemDropped(Item itemDropped) {
		this.itemDropped = itemDropped;
	}
	public int getItemDropChance() {
		return itemDropChance;
	}
	public void setItemDropChance(int itemDropChance) {
		this.itemDropChance = itemDropChance;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	
	public String[] getRoom() {
		return room;
	}
	public void setRoom(String[] room) {
		this.room = room;
	}
	/* monsterAtk(Player player)
	 * Player player- The Player the Monster is attacking
	 * Allows the Monster to attack the Player. Looks at the Monster's atkDmg and subtracts it from the Player's current HP to determine the Player's remaining HP.
	 */
	//TO DO
	//Return a boolean that shows if the player is dead/Alive???
	public void monsterAtk(Player player)
	{
		if(this.hp > 0)
		{
			int hitNum = (int)(Math.random() * 100);
			if(hitNum > 9)
			{
				int dmg = this.atk;
				System.out.println("The monster attacks you for " + dmg + " damage!");
				player.setHp(player.getHp() - dmg);
			}
			else
			{
				System.out.println("The monster missed!");
			}
			if(this.getMonsterName().equalsIgnoreCase("Vampire Queen"))
			{
				this.setHp(this.getHp() + 10);
				if(this.getHp() > 150)
				{
					this.setHp(150);
				}
				System.out.println("The Vampire Queen is regenerating her HP!");
			}
		}
		else
		{
			int lootNum = (int)(Math.random() * 100);
			System.out.println("Item Drop Number (0 - 99): " + lootNum);
			if(lootNum < this.getItemDropChance())
			{
				System.out.println("You got a " + this.getItemDropped());
				player.addItem(this.getItemDropped());
			}
		}
	}
}