

public class Puzzle {
	// Creates attributes of the objects
	private int puzzleID;
	private int roomLocation;
	private String puzzlePrompt;
	private int puzzleHint;
	private int puzzleSolution;
	private int puzzleReward;
	private int puzzleDamage;
	private int itemID;
	
	public Puzzle(int puzzleID, int roomLocation, String puzzlePrompt, int puzzleHint, int puzzleSolution,
			int puzzleReward, int puzzleDamage, int itemID) {
		
		this.puzzleID = puzzleID;
		this.roomLocation = roomLocation;
		this.puzzlePrompt = puzzlePrompt;
		this.puzzleHint = puzzleHint;
		this.puzzleSolution = puzzleSolution;
		this.puzzleReward = puzzleReward;
		this.puzzleDamage = puzzleDamage;
		this.itemID = itemID;
	}

	public int getPuzzleID() {
		return puzzleID;
	}

	public void setPuzzleID(int puzzleID) {
		this.puzzleID = puzzleID;
	}

	public int getRoomLocation() {
		return roomLocation;
	}

	public void setRoomLocation(int roomLocation) {
		this.roomLocation = roomLocation;
	}

	public String getPuzzlePrompt() {
		return puzzlePrompt;
	}

	public void setPuzzlePrompt(String puzzlePrompt) {
		this.puzzlePrompt = puzzlePrompt;
	}

	public int getPuzzleHint() {
		return puzzleHint;
	}

	public void setPuzzleHint(int puzzleHint) {
		this.puzzleHint = puzzleHint;
	}

	public int getPuzzleSolution() {
		return puzzleSolution;
	}

	public void setPuzzleSolution(int puzzleSolution) {
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

	public void setPuzzleDamage(int puzzleDamage) {
		this.puzzleDamage = puzzleDamage;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
}