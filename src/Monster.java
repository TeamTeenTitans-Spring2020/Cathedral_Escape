
public class Monster {
	// Creates attributes of the objects
	private int monsterID;
	private String monsterName;
	private String monsterDescription;
	private String appearanceChance;
	private int itemDropped;
	private float itemDropChance;
	public Monster(int monsterID, String monsterName, String monsterDescription, String appearanceChance,
			int itemDropped, float itemDropChance) {
		super();
		this.monsterID = monsterID;
		this.monsterName = monsterName;
		this.monsterDescription = monsterDescription;
		this.appearanceChance = appearanceChance;
		this.itemDropped = itemDropped;
		this.itemDropChance = itemDropChance;
	}
	public int getMonsterID() {
		return monsterID;
	}
	public void setMonsterID(int monsterID) {
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
	public String getAppearanceChance() {
		return appearanceChance;
	}
	public void setAppearanceChance(String appearanceChance) {
		this.appearanceChance = appearanceChance;
	}
	public int getItemDropped() {
		return itemDropped;
	}
	public void setItemDropped(int itemDropped) {
		this.itemDropped = itemDropped;
	}
	public float getItemDropChance() {
		return itemDropChance;
	}
	public void setItemDropChance(float itemDropChance) {
		this.itemDropChance = itemDropChance;
	}
}