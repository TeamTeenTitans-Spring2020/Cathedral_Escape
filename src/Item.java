
public class Item {
private int itemID;
private int itemName;
private int itemDescription;
private int itemType;
private int action;
public Item(int itemID, int itemName, int itemDescription, int itemType, int action) {

	this.itemID = itemID;
	this.itemName = itemName;
	this.itemDescription = itemDescription;
	this.itemType = itemType;
	this.action = action;
}

public int getItemID() {
	return itemID;
}
public void setItemID(int itemID) {
	this.itemID = itemID;
}
public int getItemName() {
	return itemName;
}
public void setItemName(int itemName) {
	this.itemName = itemName;
}
public int getItemDescription() {
	return itemDescription;
}
public void setItemDescription(int itemDescription) {
	this.itemDescription = itemDescription;
}
public int getItemType() {
	return itemType;
}
public void setItemType(int itemType) {
	this.itemType = itemType;
}
public int getAction() {
	return action;
}
public void setAction(int action) {
	this.action = action;
}
}
