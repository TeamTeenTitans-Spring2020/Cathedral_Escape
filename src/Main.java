import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

	private Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		Main m = new Main();
		Database db = new Database();
		
		db.readFiles("");
		int roomInt = 1;
		int prevRoomInt = 0;
		boolean loop = true;
		Player player = db.getPlayer();
		boolean restarted = false;
		
		do
		{
			
			
			
			if(restarted)
			{
				roomInt = 1;
				//player = new Player(roomMap.get(roomInt));
				
				player = db.getPlayer();
				
				restarted = false;
			}
			//player.setCurrentRoom(roomMap.get(roomInt));
			//player.setCurrentRoom(db.getRoomList().get(roomInt));
			/*
			System.out.println(roomInt);
			for(Map.Entry<String, Room> entry : db.getRoomList().entrySet())
			{
				System.out.println(entry.getValue().getRoomName());
			}
			*/
			player.setCurrentRoom(db.getRoomList().get(String.valueOf(roomInt)));
			
			Room currentRoom = player.getCurrentRoom();
			Monster monster;
			boolean itemUsed = false;
			
			player.enterRoom(currentRoom, db, m.input);
			//BOOKMARK TO DO
			//Get rid of this??? Already have it in player.EnterRoom???
			if(roomInt != prevRoomInt)
			{
				//System.out.println(currentRoom.getRoomDescription());
				if(currentRoom.isWasVisitedPreviously())
				{
					System.out.println("This room seems familiar.");
				}
			}
			System.out.println(currentRoom.getRoomName());
			System.out.println(currentRoom.getRoomDescription());
			
			//The input for Solving Puzzles
			//Puzzles will automatically ask for the user to solve them once they enter a Room with a puzzle that has more than 0 attempts.
			//if(!currentRoom.getPuzzle().isSolved())
			if(!(currentRoom.getPuzzle() == null))
			{
				if(!currentRoom.getPuzzle().isSolved())
				{
					Puzzle puzzle = currentRoom.getPuzzle();
					//System.out.println(puzzle.getDescription());
					System.out.println(puzzle.getPuzzlePrompt());
					int attempts = 3;
					String[] solution = puzzle.getPuzzleSolution();
					HashMap<String, String> solMap = new HashMap<String, String>();
					for(String line : solution)
					{
						solMap.put(line, line);
					}
					do
					{
						System.out.print("> ");
						String puzzleCommand = m.input.nextLine();
						if(puzzleCommand.equalsIgnoreCase(solMap.get(puzzleCommand)))
						{
							System.out.println("You are right! You have been rewarded.");
							attempts = 0;
							puzzle.setSolved(true);
							db.getPuzzleList().put(puzzle.getItemID(), puzzle);
							db.getRoomList().put(currentRoom.getRoomID(), currentRoom);
							if(puzzle.getPuzzleReward() != 0)
							{
								player.setHp(player.getHp() + puzzle.getPuzzleReward());
								System.out.println("You have been healed for " + puzzle.getPuzzleReward() + " HP. You now have " + player.getHp());
							}
							else
							{
								System.out.println("A " + puzzle.getItem().getItemName() + " has been added to your inventory!");
								player.addItem(puzzle.getItem());
							}
						}
						else if(puzzleCommand.equalsIgnoreCase("ignore"))
						{
							System.out.println("You ignored the puzzle");
							puzzle.setSolved(true);
							attempts = 0;
							db.getPuzzleList().put(puzzle.getItemID(), puzzle);
							db.getRoomList().put(currentRoom.getRoomID(), currentRoom);
						}
						else
						{
							
							attempts -= 1;
							if(attempts > 0)
							{
								System.out.println("The answer you provided is wrong. Try again or 'Ignore'. You still have " + attempts + " attempts left.");
								if(attempts == 1)
								{
									System.out.println(puzzle.getPuzzleHint());
								}
							}
							else
							{
								System.out.println("You failed to solve puzzle! You have been punished!");
								puzzle.setSolved(true);
								puzzle.getPuzzleDamage(player);
							}
							db.getPuzzleList().put(puzzle.getItemID(), puzzle);
							db.getRoomList().put(currentRoom.getRoomID(), currentRoom);
						}
					}while(!puzzle.isSolved() || attempts != 0);
				}
			}
			
			//The console will printout different questions based on if the user is in combat with a monster or not
			/*
			if(player.isInCombat())
			{
				//Change combat to be handled in a method???
				
				monster = monsterMap.get(currentRoom.getRoomNum());
				System.out.println("You are in combat with a " + monster.getName() + ". What would you like to do?");
				System.out.print("> ");
			}
			*/
			/*
			else
			{
				System.out.println("Which direction do you want to go? (N, E, W, S)");
				System.out.print("> ");
			}
			*/
			System.out.println("Which direction do you want to go? (N, E, W, S)");
			System.out.print("> ");
			
			String command = m.input.nextLine(); //The user input
			if(command.contains(" "))
			{
				int spaceIndex = command.indexOf(" ");
				String firstWord = command.substring(0, spaceIndex);
				String secondWord = command.substring(spaceIndex + 1, command.length());
				
				//The Examine commands for Player inventory or Items
				if(firstWord.equalsIgnoreCase("Examine"))
				{
					//Examining the Room's inventory. Can also be done simply by typing "Examine"
					if(secondWord.equalsIgnoreCase("Room"))
					{
						
						player.getCurrentRoom().examineInventory();
						currentRoom.printMonsters();
						/*
						if(player.getCurrentRoom().isHasMonster())
						{
							System.out.println("There's a monster in the room.");
						}
						else
						{
							System.out.println("There are not monsters in the room.");
						}
						*/
					}
					//Examining the Player's inventory
					else if(secondWord.equalsIgnoreCase("Inventory"))
					{
						player.examineInventory();
					}
					//BOOKMARK
					//Examining a Monster, if one is in the Room
					/*
					else if(secondWord.equalsIgnoreCase("Monster"))
					{
						if(currentRoom.isHasMonster())
						{
							monster = monsterMap.get(currentRoom.getRoomNum());
							boolean examiningMonster = true;
							do
							{
								//Where input is asked for Attacking or Ignoring the Monster
								System.out.println("There is a " + monster.getName() + " in the room.");
								System.out.println(monster.getDescription());
								System.out.println(monster.getName() + "'s HP is: " + monster.getHp() + " and its attack is " + monster.getAtkDmg());
								System.out.println("Would you like to 'Attack' or 'Ignore' it?");
								System.out.print("> ");
								String exInput = m.input.nextLine();
								
								//Code that executes when Attack is entered
								if(exm.input.equalsIgnoreCase("Attack"))
								{
									System.out.println("You have engaged combat with the " + monster.getName());
									examiningMonster = false;
									player.setInCombat(true);
									//player.attack(monster);
									//monster.monsterAtk(player);
								}
								//Code that executes when Ignore is entered. The Monster disappears from the HashMap.
								else if(exm.input.equalsIgnoreCase("Ignore"))
								{
									System.out.println("You have ignored the " + monster.getName());
									examiningMonster = false;
									monsterMap.remove(currentRoom.getRoomNum());
									Room updatedRoom = roomMap.get(currentRoom.getRoomNum());
									updatedRoom.setHasMonster(false);
									roomMap.put(currentRoom.getRoomNum(), updatedRoom);
								}
								else
								{
									System.out.println("Invalid m.input. Please type 'Attack' or 'Ignore' to deal with the " + monster.getName() + ".");
								}
							}while(examiningMonster);
						}
						else
						{
							System.out.println("There's no monsters in this room.");
						}
					}
					*/
					//Examining an Item in a Room's inventory
					else if(currentRoom.getInventory().containsKey(secondWord))
					{
						currentRoom.getInventory().get(secondWord).examineItem();
					}
					//Examining an Item in the Player's inventory
					else if(player.getInventory().contains(secondWord))
					{
						int index = player.getInventory().indexOf(secondWord);
						player.getInventory().get(index).examineItem();
					}
					//Failure message to let the user know what they may be typing wrong when using the 'Examine' command
					else
					{
						System.out.println("Failed to examine. Either Item is not in the room, your inventory, or doesn't exist. If you misspelled the item, make sure,\n"
								+ "to spell it exactly as it appears when typed in the 'Examine' description for the room or the 'Examine Inventory description.");
					}
				}
				
				//Picking up an item. Removes an item from the Room's inventory and adds it to the Player's inventory.
				if(firstWord.equalsIgnoreCase("Pickup"))
				{
					if(currentRoom.getInventory().containsKey(secondWord))
					{
						Item item = db.getItemList().get(secondWord);
						item.pickupItem(player, currentRoom);
						//db.itemList.put(item.getItemID(), item);
					}
					else
					{
						System.out.println("Failed to pickup item. Either it is not in the room or was mistyped. If item was misspelled, make sure to spell it exactly\n"
								+ "as it appears when typed in the 'Examine' description for the room or the 'Examine Inventory' description.");
					}
				}
				
				
				//Dropping an item. Removes an item from the Player's inventory and adds it to the Room's inventory.
				if(firstWord.equalsIgnoreCase("Drop"))
				{
					Item itemObject = db.getItemList().get(secondWord);
					//if(player.getInventory().contains(secondWord))
					if(player.getInventory().contains(itemObject))
					{
						Item item = db.getItemList().get(secondWord);
						item.dropItem(player, currentRoom);
						//db.itemList.put(item.getItemID(), item);
					}
					else
					{
						System.out.println("Failed to drop item. Either it is not in your inventory or was mistyped. If item was misspelled, make sure to spell it exactly\n"
								+ "as it appears when typed in the 'Examine' description for the room or the 'Examine Inventory' description.");
					}
				}
				
				if(firstWord.equalsIgnoreCase("Equip"))
				{
					Item itemObject = db.getItemList().get(secondWord);
					//if(player.getInventory().contains(secondWord))
					if(player.getInventory().contains(itemObject))
					{
						Item item = db.getItemList().get(secondWord);
						item.equipItem(player);
					}
					else
					{
						System.out.println("Failed to equip item. Either it is not in your inventory or was mistyped. If item was misspelled, make sure to spell it exactly\n"
								+ "as it appears when typed in the 'Examine' description for the room or the 'Examine Inventory' description.");
					}
				}
				
				if(firstWord.equalsIgnoreCase("Unequip"))
				{
					Item itemObject = db.getItemList().get(secondWord);
					//if(player.getInventory().contains(secondWord))
					if(player.getInventory().contains(itemObject))
					{
						Item item = db.getItemList().get(secondWord);
						item.unequipItem(player);
					}
					else
					{
						System.out.println("Failed to unequip item. Either it is not in your inventory, not already equipped, or was mistyped. If item was misspelled, make\n"
								+ "sure to spell it exactly as it appears when typed in the 'Examine' description for the room or the 'Examine Inventory' description.");
					}
				}
				
				if(firstWord.equalsIgnoreCase("Use"))
				{
					Item itemObject = db.getItemList().get(secondWord);
					//if(player.getInventory().contains(secondWord))
					if(player.getInventory().contains(itemObject))
					{
						Item item = db.getItemList().get(secondWord);
						itemUsed = item.useItem(player);
					}
					else
					{
						System.out.println("Failed to use item. Either it is not in your inventory, cannot be used, or was mistyped. If item was misspelled, make sure\n"
								+ "to spell it exactly as it appears when typed in the 'Examine' description for the room or the 'Examine Inventory' description.");
					}
				}
			}
			
			//Command for examining a Room's inventory of Items
			else if(command.equalsIgnoreCase("Examine"))
			{
				player.getCurrentRoom().examineInventory();
				currentRoom.printMonsters();
			}
			
			//Command for attacking a monster
			/*
			else if (command.equalsIgnoreCase("Attack"))
			{
				if(!player.inCombat)
				{
					System.out.println("Cannot Attack. Not currently in combat.");
				}
				else
				{
					monster = monsterMap.get(currentRoom.getRoomNum());
					player.attack(monster);
					if(monster.getHp() > 0)
					{
						monster.monsterAtk(player);
					}
					if(monster.getHp() <= 0)
					{
						Room updatedRoom = player.getCurrentRoom();
						updatedRoom.setHasMonster(false);
						roomMap.put(updatedRoom.getRoomNum(), updatedRoom);
						monsterMap.put(updatedRoom.getRoomNum(), monster);
					}
				}
			}
			*/
			
			//Command for printing the exits in the current room
			/*
			else if(command.equalsIgnoreCase("Print"))
			{
				printExits(player);
			}
			*/
			
			//Command for looking at the player's hp and attack
			else if (command.equalsIgnoreCase("Stat"))
			{
				//m.printPlayerStats(player);
				player.printPlayerStats();
			}
			
			else if(command.equalsIgnoreCase("Load1") || command.equalsIgnoreCase("Load2") || command.equalsIgnoreCase("Load3"))
			{
				String num = command.substring(command.length() - 1);
				db.readFiles(num);
			}
			
			else if(command.equalsIgnoreCase("Exit"))
			{
				System.out.println("You have exited the game.");
				System.exit(0);
			}
			
			//Navigation commands
			else if((command.equalsIgnoreCase("N") ||command.equalsIgnoreCase("E") || command.equalsIgnoreCase("W") || command.equalsIgnoreCase("S") ||
				command.equalsIgnoreCase("North") || command.equalsIgnoreCase("South") || command.equalsIgnoreCase("East") || command.equalsIgnoreCase("West")))
			{
				//LinkedList<String> exits = currentRoom.getExits();
				//BOOKMARK
				//Get away from HashMap. Use something else.
				HashMap<String, String> exits = currentRoom.getExits();
				String commandLetter = command.substring(0, 1);
				String roomNum = exits.get(commandLetter);
				
				//int index = exits.indexOf(commandLetter);
				
				if(!roomNum.equalsIgnoreCase("0"))
				{
					Room room = db.getRoomList().get(roomNum);
					currentRoom = room;
					roomInt = Integer.parseInt(currentRoom.getRoomID());
					
				}
				else
				{
					System.out.println("You can't go that way. There's a wall.");
				}
				//int index = exits.contains(commandLetter);
				/*
				if(player.inCombat)
				{
					System.out.println("You cannot go to another room. You are currently in combat with a monster.");
				}
				*/
				//else
				//{
					/*
					boolean isWalkable = false; //Enables the game to show the user a message if they can't go a certain direction
					String commandLetter = command.substring(0, 1); //The first letter of the user input used to determine if the word is North, East, South, or West
					LinkedList<String> exitList = currentRoom.getExits(); //The list of exits the current Room has
					for(String exit : exitList)
					{
						String exitLetter = exit.substring(0, 1); //The first letter of the Rooms.txt used to show the direction of a connection: North, East, South, or West
						if(commandLetter.equalsIgnoreCase(exitLetter))
						{
							isWalkable = true;
							
							
							roomInt = Integer.parseInt(exit.substring(exit.length() - 1));
							break;
						}
					}
					if(!isWalkable)
					{
						System.out.println("You can't go that way.");
					}
					*/
				//}
			}
			/*
			prevRoomInt = currentRoom.getRoomNum();
			if(player.isInCombat() && (command.equalsIgnoreCase("Attack") || itemUsed))
			{
				monster = monsterMap.get(currentRoom.getRoomNum());
				System.out.println("Player HP: " + player.getHp() + "               Monster HP: " + monster.getHp());
				itemUsed = false;
			}
			*/
			while(player.getHp() <= 0)
			{
				System.out.println("You choke on your last breath as you see the last of your blood spill from your body. Game over.");
				System.out.println("Would you like to 'Start' a new game, 'Load' a pre-existing save, or 'Exit' the game?");
				command = m.input.nextLine();
				String num = command.substring(command.length() - 1);
				if(command.equalsIgnoreCase("Start"))
				{
					db.readFiles("");
				}
				else if(command.equalsIgnoreCase("Load1") || command.equalsIgnoreCase("Load2") || command.equalsIgnoreCase("Load3"))
				{
					db.readFiles(num);
				}
				else if(command.equalsIgnoreCase("Exit"))
				{
					System.out.println("You have exited the game.");
					System.exit(0);
				}
				else
				{
					System.out.println("Invalid command. Please type 'Start', 'Load', or 'Exit'. If you wish to 'Load', type either 'Load1', 'Load2', or 'Load3' to load their respective saves.");
				}
			}
		//}

	}while(loop);

}
	
	public void printPlayerStats(Player player)
	{
		System.out.println("Player HP : " + player.getHp() + "        Player ATK: " + player.getAtkDmg() + "        Player DEF: " + player.getDef());
		String weapon = "None";
		String armor = "None";
		String wAtk = "0";
		String aDef = "0";
		if(player.getEquippedWeapon() != null)
		{
			weapon = player.getEquippedWeapon().getItemName();
			wAtk = String.valueOf(player.getEquippedWeapon().getUseValue());
		}
		if(player.getEquippedArmor() != null)
		{
			armor = player.getEquippedArmor().getItemName();
			aDef = String.valueOf(player.getEquippedArmor().getUseValue());
		}
		System.out.println("Equipped Weapon: " + weapon + "        Equipped Armor: " + armor);
		System.out.println("Weapon ATK: " + wAtk + "        Armor DEF: " + aDef);
	}
	
}
