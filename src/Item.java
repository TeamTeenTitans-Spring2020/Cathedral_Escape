

public class Item {
	private String itemID;
	private String itemName;
	private String itemDescription;
	private String itemType;
	private String action;
	private boolean isEquipped;
	private int useValue;

	public Item(String itemID, String itemName, String itemDescription, String itemType, String action, int useValue) {
		this.itemID = itemID;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.itemType = itemType;
		this.action = action;
		this.isEquipped = false;
		this.useValue = useValue;
	}

	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public boolean isEquipped() {
		return isEquipped;
	}
	public void setEquipped(boolean isEquipped) {
		this.isEquipped = isEquipped;
	}
	
	public int getUseValue() {
		return useValue;
	}

	public void setUseValue(int useValue) {
		this.useValue = useValue;
	}

	/* examineItem()
	 * Prints out the description of an Item
	 */
	public void examineItem()
	{
		System.out.println("    " + this.getItemDescription());
	}
	
	/* pickupItem(Player player, Room room)
	 * Removes an Item from a Room's inventory and puts it into the Player's inventory
	 */
	public void pickupItem(Player player, Room room)
	{
		//this.setRoomNum(0);
		player.addItem(this);
		room.removeItem(this);
		System.out.println("    " + this.getItemName() + " has been picked up from the " + room.getRoomName() + " and successfully added to the player inventory.");
	}
	
	/* dropItem(Player player, Room room)
	 * Removes an Item from the Player's inventory and puts it into the current Room's inventory
	 */
	public void dropItem(Player player, Room room)
	{
		room.addItem(this);
		player.removeItem(this);
		System.out.println("    " + this.getItemName() + " has been dropped successfully from the player inventory and placed in " + room.getRoomName() + ".");
	}
	
	/* equipItem(Player player)
	 * Player player- The player the item is being equipped to.
	 * Allows the equipping of items. Adding the equipped item's bonus Attack Damage to the Player's total Attack Damage. (Only Sword can be equipped)
	 */
	public void equipItem(Player player)
	{
		if(!(this.itemType.equalsIgnoreCase("Weapon") || this.itemType.equalsIgnoreCase("Armor")))
		{
			System.out.println(this.itemName + " cannot be equipped. It's not a weapon or armor.");
		}
		else
		{
			if(player.getEquippedArmor() == this || player.getEquippedWeapon() == this)
			{
				System.out.println(this.itemName + " cannot be equipped. " + this.itemName + " is already equipped.");
			}
			else if(this.itemType.equalsIgnoreCase("Weapon"))
			{
				int initial = player.getAtkDmg();
				int totalDmg = initial + this.useValue;
				player.setAtkDmg(totalDmg);
				System.out.println("    " + this.itemName + " has been equipped. Your Attack Damage increased to: " + totalDmg);
				player.setEquippedWeapon(this);
			}
			else if(this.itemType.equalsIgnoreCase("Armor"))
			{
				int initial = player.getDef();
				int totalDef = initial + this.useValue;
				player.setDef(totalDef);
				System.out.println("    " + this.itemName + " has been equipped. Your Defense has increased to: " + totalDef);
				player.setEquippedArmor(this);
			}
		}
	}
	
	/* unequipItem(Player player)
	 * Player player- The player the item is currently equipped to.
	 * Allows the unequipping of items. Takes away the unequipped item's bonus Attack Damage from the Player's total Attack Damage. (Only Sword can be unequipped)
	 */
	public void unequipItem(Player player)
	{
		if(player.getEquippedArmor() != this || player.getEquippedWeapon() != this)
		{
			System.out.println(this.itemName + " cannot be unequipped because it's not currently equipped.");
		}
		else
		{
			if(this.itemType.equalsIgnoreCase("Weapon"))
			{
				int initial = player.getAtkDmg();
				int totalDmg = initial - this.useValue;
				player.setAtkDmg(totalDmg);
				System.out.println("    " + this.itemName + " has been unequipped. Your Attack Damage decreased to: " + totalDmg);
				player.setEquippedWeapon(null);
			}
			else if(this.itemType.equalsIgnoreCase("Armor"))
			{
				int initial = player.getDef();
				int totalDef = initial - this.useValue;
				player.setDef(totalDef);
				System.out.println("    " + this.itemName + " has been unequipped. Your Defense decreased to: " + totalDef);
				player.setEquippedArmor(null);
			}
		}
	}
	
	/* useItem(Player player)
	 * Player player- The player the item is used on
	 * Allows the use of items, primarily healing. Can only heal Player's HP up to 100. (Only Potion can be used)
	 */
	public boolean useItem(Player player)
	{
		//TO DO
		//Look at itemType and base legal actions off of that
		if(!(this.action.equalsIgnoreCase("Use")))
		{
			System.out.println(this.itemName + " cannot be used.");
			return false;
		}
		else if(this.itemType.equalsIgnoreCase("Consumable"))
		{
			int playerhp = player.getHp();
			if(playerhp >= 100)
			{
				System.out.println("You didn't use " + this.itemName + ". You already have 100 HP. Healing now would be a waste.");
				return false;
			}
			else
			{
				playerhp += this.useValue;
				int healed = this.useValue;
				if(playerhp > 100)
				{
					healed = healed - playerhp + 100;
					playerhp = 100;
				}
				player.setHp(playerhp);
				//player.getInventory().remove(this.itemName);
				player.removeItem(this);
				System.out.println("    You used " + this.itemName + " to heal " + healed + " HP. You now have " + player.getHp() + " HP. " + this.itemName + " has disappeared from your Inventory.");
				return true;
			}
		}
		else if(this.itemType.equalsIgnoreCase("Buff"))
		{
			if(player.getEquippedWeapon() == null)
			{
				System.out.println("Cannot use " + this.itemName + ". You have no weapon equipped to buff.");
				return false;
			}
			else
			{
				Item item = player.getEquippedWeapon();
				item.setUseValue(this.useValue + item.getUseValue());
				return true;
			}
		}
		return false;
	}
	
}
