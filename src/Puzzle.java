

public class Puzzle {
	// Creates attributes of the objects
	private String puzzleID;
	private String roomLocation;
	private String puzzlePrompt;
	private String puzzleHint;
	private String[] puzzleSolution;//HashMap???
	private int puzzleReward;
	private int puzzleDamage;
	private String itemID;
	private Item item;
	private boolean isSolved;
	
	public Puzzle(String puzzleID, String roomLocation, String puzzlePrompt, String puzzleHint, String[] puzzleSolution,
			int puzzleReward, int puzzleDamage, String itemID, boolean isSolved) {
		
		this.puzzleID = puzzleID;
		this.roomLocation = roomLocation;
		this.puzzlePrompt = puzzlePrompt;
		this.puzzleHint = puzzleHint;
		this.puzzleSolution = puzzleSolution;
		this.puzzleReward = puzzleReward;
		this.puzzleDamage = puzzleDamage;
		this.itemID = itemID;
		this.isSolved = isSolved;
	}

	public String getPuzzleID() {
		return puzzleID;
	}

	public void setPuzzleID(String puzzleID) {
		this.puzzleID = puzzleID;
	}

	public String getRoomLocation() {
		return roomLocation;
	}

	public void setRoomLocation(String roomLocation) {
		this.roomLocation = roomLocation;
	}

	public String getPuzzlePrompt() {
		return puzzlePrompt;
	}

	public void setPuzzlePrompt(String puzzlePrompt) {
		this.puzzlePrompt = puzzlePrompt;
	}

	public String getPuzzleHint() {
		return puzzleHint;
	}

	public void setPuzzleHint(String puzzleHint) {
		this.puzzleHint = puzzleHint;
	}

	public String[] getPuzzleSolution() {
		return puzzleSolution;
	}

	public void setPuzzleSolution(String[] puzzleSolution) {
		this.puzzleSolution = puzzleSolution;
	}

	public int getPuzzleReward() {
		return puzzleReward;
	}

	public void setPuzzleReward(int puzzleReward) {
		this.puzzleReward = puzzleReward;
	}

	public int getPuzzleDamage() {
		return puzzleDamage;
	}

	public void getPuzzleDamage(Player player) {//TO DO return a String instead so a JLabel can get the String for the gameDescription???
		if(this.getPuzzleDamage() == 0)
		{
			if(player.getInventory().size() > 0)
			{
				Item item = player.getInventory().get(0);
				if(player.getEquippedWeapon() == item)
				{
					item.unequipItem(player);
				}
				else if(player.getEquippedArmor() == item)
				{
					item.unequipItem(player);
				}
				System.out.println("You lost your " + item.getItemName() + "!");
				player.removeItem(player.getInventory().get(0));
			}
			else
			{
				System.out.println("You had no items to lose. The punishment has been avoided.");
			}
		}
		//return puzzleDamage;
	}

	public void setPuzzleDamage(int puzzleDamage) {
		this.puzzleDamage = puzzleDamage;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public boolean isSolved() {
		return isSolved;
	}

	public void setSolved(boolean isSolved) {
		this.isSolved = isSolved;
	}
	
}