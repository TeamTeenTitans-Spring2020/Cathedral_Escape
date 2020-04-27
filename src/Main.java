import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
		Monster monster = db.getMonsterList().get("Human Skeleton");
		Room currentRoom = player.getCurrentRoom();
		
		do
		{
			if(restarted)
			{
				player = db.getPlayer();
				roomInt = Integer.parseInt(player.getCurrentRoom().getRoomID());
				restarted = false;
				currentRoom = player.getCurrentRoom();
				monster = player.enterRoom(currentRoom, db, m.input);
			}
			player.setCurrentRoom(db.getRoomList().get(String.valueOf(roomInt)));
			currentRoom = player.getCurrentRoom();
			monster = db.getMonsterList().get("Human Skeleton");
			boolean itemUsed = false;
			if(roomInt != prevRoomInt)
			{
				if(!restarted)
				{
					monster = player.enterRoom(currentRoom, db, m.input);
				}
				restarted = false;
				if(currentRoom.isWasVisitedPreviously())
				{
					System.out.println("This room looks familiar.");
				}
				prevRoomInt = Integer.parseInt(currentRoom.getRoomID());
			}
			
			//The input for Solving Puzzles
			//Puzzles will automatically ask for the user to solve them once they enter a Room with a puzzle that has more than 0 attempts.
			if(!(currentRoom.getPuzzle() == null))
			{
				if(!currentRoom.getPuzzle().isSolved())
				{
					Puzzle puzzle = currentRoom.getPuzzle();
					System.out.println("    " + puzzle.getPuzzlePrompt());
					int attempts = 3;
					ArrayList<String> solution = puzzle.getPuzzleSolution();
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
							System.out.println("    You are right! You have been rewarded.");
							attempts = 0;
							if(puzzle.getPuzzleReward() != 0)
							{
								player.setHp(player.getHp() + puzzle.getPuzzleReward());
								System.out.println("    You have been healed for " + puzzle.getPuzzleReward() + " HP. You now have " + player.getHp());
							}
							System.out.println("    A " + puzzle.getItem().getItemName() + " has been added to your inventory!");
							player.addItem(puzzle.getItem());
							
							puzzle.setSolved(true);
							db.getPuzzleList().put(puzzle.getItemID(), puzzle);
							db.getRoomList().put(currentRoom.getRoomID(), currentRoom);
						}
						else if(puzzleCommand.equalsIgnoreCase("Skip"))
						{
							System.out.println("    You skipped the puzzle");
							puzzle.setSolved(true);
							attempts = 0;
							db.getPuzzleList().put(puzzle.getItemID(), puzzle);
							db.getRoomList().put(currentRoom.getRoomID(), currentRoom);
						}
						else if(puzzleCommand.equalsIgnoreCase("Help") || puzzleCommand.equalsIgnoreCase("Commands") || puzzleCommand.equalsIgnoreCase("Command"))
						{
							try
							{
								String file = "COMMANDS";
								FileReader fr = new FileReader(file);
								Scanner scan = new Scanner(fr);
								while(scan.hasNextLine())
								{
									System.out.println(scan.nextLine());
								}
								scan.close();
							}
							catch(FileNotFoundException fr)
							{
								System.out.println("COMMANDS file not found");
							}
						}
						else
						{
							
							attempts -= 1;
							if(attempts > 0)
							{
								System.out.println("    The answer you provided is wrong. Try again or 'Skip'. You still have " + attempts + " attempts left.");
								if(attempts == 1)
								{
									System.out.println("    " + puzzle.getPuzzleHint());
								}
							}
							else
							{
								System.out.println("    You failed to solve puzzle! You have been punished!");
								puzzle.setSolved(true);
								puzzle.getPuzzleDamage(player);
							}
							db.getPuzzleList().put(puzzle.getItemID(), puzzle);
							db.getRoomList().put(currentRoom.getRoomID(), currentRoom);
						}
					}while(!puzzle.isSolved() || attempts != 0);
				}
			}
			String command = "";
			if(!monster.getMonsterName().equalsIgnoreCase("Vampire Queen") && player.getHp() > 0)
			{
				System.out.println("Which direction do you want to go? (N, E, W, S)");
				System.out.print("> ");
				
				command = m.input.nextLine(); //The user input
			}
			if(command.contains(" "))
			{
				int spaceIndex = command.indexOf(" ");
				String firstWord = command.substring(0, spaceIndex);
				String secondWord = command.substring(spaceIndex + 1, command.length());
				
				//The Examine commands for Player inventory or Items
				if(firstWord.equalsIgnoreCase("Explore"))
				{
					//Exploring the Room's inventory. Can also be done simply by typing "Explore"
					if(secondWord.equalsIgnoreCase("Room"))
					{
						player.getCurrentRoom().examineInventory();
						currentRoom.printMonsters();
					}
					
					//Failure message to let the user know what they may be typing wrong when using the 'Explore' command
					else
					{
						System.out.println("Failed to explore. Either Item is not in the room, your inventory, or doesn't exist. If you misspelled the item, make sure,\n"
								+ "to spell it exactly as it appears when typed in the 'Explore' description for the room or the 'Explore Inventory description.");
					}
				}
				
				if(firstWord.equalsIgnoreCase("Inspect"))
				{
					System.out.println("First : " + firstWord);
					System.out.println("Second: " + secondWord);
					//int indexP = player.getInventory().indexOf(secondWord);
					int indexP = player.getInventory().indexOf(db.getItemList().get(secondWord));
					
					//Inspecting the Player's inventory
					if(secondWord.equalsIgnoreCase("Inventory"))
					{
						System.out.println("    Inspecting Inventory");
						player.examineInventory();
					}
					//Inspecting an Item in the Player's inventory
					else if(indexP > -1)
					{
						System.out.println("    Inspecting " + secondWord);
						player.examineItem(indexP);
					}
					//Inspecting an Item in a Room's inventory
					else if(currentRoom.getInventory().containsKey(secondWord))
					{
						System.out.println("    Inspecting " + secondWord);
						currentRoom.getInventory().get(secondWord).examineItem();
					}
					else
					{
						System.out.println("Failed to Inspect item.");
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
								+ "as it appears when typed in the 'Explore' description for the room or the 'Explore Inventory' description.");
					}
				}
				
				
				//Dropping an item. Removes an item from the Player's inventory and adds it to the Room's inventory.
				if(firstWord.equalsIgnoreCase("Drop"))
				{
					Item itemObject = db.getItemList().get(secondWord);
					if(player.getInventory().contains(itemObject))
					{
						Item item = db.getItemList().get(secondWord);
						item.dropItem(player, currentRoom);
						//db.itemList.put(item.getItemID(), item);
					}
					else
					{
						System.out.println("Failed to drop item. Either it is not in your inventory or was mistyped. If item was misspelled, make sure to spell it exactly\n"
								+ "as it appears when typed in the 'Explore' description for the room or the 'Explore Inventory' description.");
					}
				}
				
				if(firstWord.equalsIgnoreCase("Equip"))
				{
					Item itemObject = db.getItemList().get(secondWord);
					if(player.getInventory().contains(itemObject))
					{
						Item item = db.getItemList().get(secondWord);
						item.equipItem(player);
					}
					else
					{
						System.out.println("Failed to equip item. Either it is not in your inventory or was mistyped. If item was misspelled, make sure to spell it exactly\n"
								+ "as it appears when typed in the 'Explore' description for the room or the 'Explore Inventory' description.");
					}
				}
				
				if(firstWord.equalsIgnoreCase("Unequip"))
				{
					Item itemObject = db.getItemList().get(secondWord);
					if(player.getInventory().contains(itemObject))
					{
						Item item = db.getItemList().get(secondWord);
						item.unequipItem(player);
					}
					else
					{
						System.out.println("Failed to unequip item. Either it is not in your inventory, not already equipped, or was mistyped. If item was misspelled, make\n"
								+ "sure to spell it exactly as it appears when typed in the 'Explore' description for the room or the 'Explore Inventory' description.");
					}
				}
				
				if(firstWord.equalsIgnoreCase("Use"))
				{
					Item itemObject = db.getItemList().get(secondWord);
					if(player.getInventory().contains(itemObject))
					{
						Item item = db.getItemList().get(secondWord);
						itemUsed = item.useItem(player);
					}
					else
					{
						System.out.println("Failed to use item. Either it is not in your inventory, cannot be used, or was mistyped. If item was misspelled, make sure\n"
								+ "to spell it exactly as it appears when typed in the 'Explore' description for the room or the 'Explore Inventory' description.");
					}
				}
				
				if(firstWord.equalsIgnoreCase("New"))
				{
					if(secondWord.equalsIgnoreCase("Game"))
					{
						System.out.println("    You are starting a new game");
						db.readFiles("");
						monster = db.getMonsterList().get(monster.getMonsterName());
						player = db.getPlayer();
						currentRoom = player.getCurrentRoom();
						roomInt = Integer.parseInt(currentRoom.getRoomID());
						prevRoomInt = 0;
					}
				}
			}
			
			//Command for examining a Room's inventory of Items
			else if(command.equalsIgnoreCase("Explore"))
			{
				player.getCurrentRoom().examineInventory();
				currentRoom.printMonsters();
			}
			
			else if(command.equalsIgnoreCase("Inspect"))
			{
				player.examineInventory();
			}
			
			//Command for looking at the player's hp and attack
			else if (command.equalsIgnoreCase("Stat"))
			{
				player.printPlayerStats();
			}
			
			else if(command.equalsIgnoreCase(""))
			{
				
			}
			
			else if(command.equalsIgnoreCase("Help") || command.equalsIgnoreCase("Commands") || command.equalsIgnoreCase("Command"))
			{
				try
				{
					String file = "COMMANDS";
					FileReader fr = new FileReader(file);
					Scanner scan = new Scanner(fr);
					while(scan.hasNextLine())
					{
						System.out.println(scan.nextLine());
					}
					scan.close();
				}
				catch(FileNotFoundException fr)
				{
					System.out.println("COMMANDS file not found");
				}
			}
			
			else if(command.equalsIgnoreCase("Exit"))
			{
				System.out.println("You have exited the game.");
				System.exit(0);
			}

			//Loading a save
			else if(command.equalsIgnoreCase("Load1") || command.equalsIgnoreCase("Load2") || command.equalsIgnoreCase("Load3"))
			{
				String num = command.substring(command.length() - 1);
				db = new Database();
				//db = db.readFiles(num);
				System.out.println("    You are loading save" + num);
				db.readFiles(num);
				monster = db.getMonsterList().get(monster.getMonsterName());
				player = db.getPlayer();
				currentRoom = player.getCurrentRoom();
				roomInt = Integer.parseInt(currentRoom.getRoomID());
				prevRoomInt = 0;
			}
			
			//Saving a file
			else if(command.equalsIgnoreCase("Save1") || command.equalsIgnoreCase("Save2") || command.equalsIgnoreCase("Save3"))
			{
				String num = command.substring(command.length() - 1);
				//db = new Database();
				//db = db.readFiles(num);
				System.out.println("    You are saveing to save" + num);
				db.saveFiles(num);
			}
			
			//Navigation commands
			else if((command.equalsIgnoreCase("N") ||command.equalsIgnoreCase("E") || command.equalsIgnoreCase("W") || command.equalsIgnoreCase("S") ||
				command.equalsIgnoreCase("North") || command.equalsIgnoreCase("South") || command.equalsIgnoreCase("East") || command.equalsIgnoreCase("West")))
			{
				HashMap<String, String> exits = currentRoom.getExits();
				String commandLetter = command.substring(0, 1);
				if(commandLetter.equalsIgnoreCase("N"))
				{
					String roomNum = currentRoom.getroomToTheNorth();
					if(!(roomNum.equals("0")))
					{
						Room room = db.getRoomList().get(currentRoom.getroomToTheNorth());
						prevRoomInt = Integer.parseInt(currentRoom.getRoomID());
						currentRoom = room;
						roomInt = Integer.parseInt(currentRoom.getRoomID());
					}
					else
					{
						System.out.println("You can't go that way. There's a wall.");
					}
				}
				else if(commandLetter.equalsIgnoreCase("S"))
				{
					String roomNum = currentRoom.getroomToTheSouth();
					if(!(roomNum.equals("0")))
					{
						Room room = db.getRoomList().get(currentRoom.getroomToTheSouth());
						prevRoomInt = Integer.parseInt(currentRoom.getRoomID());
						currentRoom = room;
						roomInt = Integer.parseInt(currentRoom.getRoomID());
					}
					else
					{
						System.out.println("You can't go that way. There's a wall.");
					}
				}
				else if(commandLetter.equalsIgnoreCase("E"))
				{
					String roomNum = currentRoom.getroomToTheEast();
					if(!(roomNum.equals("0")))
					{
						Room room = db.getRoomList().get(currentRoom.getroomToTheEast());
						prevRoomInt = Integer.parseInt(currentRoom.getRoomID());
						currentRoom = room;
						roomInt = Integer.parseInt(currentRoom.getRoomID());
					}
					else
					{
						System.out.println("You can't go that way. There's a wall.");
					}
				}
				else if(commandLetter.equalsIgnoreCase("W"))
				{
					String roomNum = currentRoom.getroomToTheWest();
					if(!(roomNum.equals("0")))
					{
						Room room = db.getRoomList().get(currentRoom.getroomToTheWest());
						prevRoomInt = Integer.parseInt(currentRoom.getRoomID());
						currentRoom = room;
						roomInt = Integer.parseInt(currentRoom.getRoomID());
					}
					else
					{
						System.out.println("    You can't go that way. There's a wall.");
					}
				}
			}
			else
			{
				System.out.println("    Invalid input. This is not a proper command.");
			}
			while(player.getHp() <= 0)
			{
				System.out.println("    You choke on your last breath as you see the last of your blood spill from your body. Game over.");
				System.out.println("Would you like to 'Start' a new game, 'Load' a pre-existing save, or 'Exit' the game?");
				command = m.input.nextLine();
				String num = command.substring(command.length() - 1);
				if(command.equalsIgnoreCase("Start"))
				{
					//db = new Database();
					//db = db.readFiles("");
					System.out.println("    You are starting a new game");
					db.readFiles("");
					monster = db.getMonsterList().get(monster.getMonsterName());
					player = db.getPlayer();
					currentRoom = player.getCurrentRoom();
					roomInt = Integer.parseInt(currentRoom.getRoomID());
					prevRoomInt = 0;
				}
				else if(command.equalsIgnoreCase("Load1") || command.equalsIgnoreCase("Load2") || command.equalsIgnoreCase("Load3"))
				{
					//db = new Database();
					//db = db.readFiles(num);
					System.out.println("    You are loading save file" + num);
					db.readFiles(num);
					monster = db.getMonsterList().get(monster.getMonsterName());
					player = db.getPlayer();
					currentRoom = player.getCurrentRoom();
					prevRoomInt = 0;
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
			//monster.setHp(0);
			//if(monster.getMonsterName().equalsIgnoreCase("Human Skeleton"))
			if(monster.getMonsterName().equalsIgnoreCase("Vampire Queen") && monster.getHp() <= 0)
			{
				System.out.println("   The Vampire Queen crumples to her knees. Despite being undead, her breathing is ragged to the point of exhaustion. As she backs up into the"
						+ "\nfirey light of the torch, her features become more defined. You now realize her vicious red eyes were actually red with tears, and that her"
						+ "\npale, ghastly face was because of malnutrition from being stuck here. Just like you. She asks you one last question, 'Why?' She falls to the"
						+ "\nground, lifeless. You look back at the cutsey keychain you obtained from her and see a picture of a family. She appears happy in the photo.");
				System.out.println("    With a heavy heart, you march back to the first floor's large corridor and unlock a door leading out of the building. You escaped,"
						+ "\nbut can you live with killing an innocent girl that was just defending herself?");
				
				while(monster.getHp() <= 0)
				{
					System.out.println("    Would you like to 'Start' a new game, 'Load' a pre-existing save, or 'Exit' the game?");
					command = m.input.nextLine();
					String num = command.substring(command.length() - 1);
					if(command.equalsIgnoreCase("Start"))
					{
						//db = new Database();
						//db = db.readFiles("");
						System.out.println("    You are starting a new game");
						db.readFiles("");
						monster = db.getMonsterList().get(monster.getMonsterName());
						player = db.getPlayer();
						currentRoom = player.getCurrentRoom();
						prevRoomInt = 0;
					}
					else if(command.equalsIgnoreCase("Load1") || command.equalsIgnoreCase("Load2") || command.equalsIgnoreCase("Load3"))
					{
						//db = new Database();
						//db = db.readFiles(num);
						System.out.println("    You are loading save file" + num);
						db.readFiles(num);
						monster = db.getMonsterList().get(monster.getMonsterName());
						player = db.getPlayer();
						currentRoom = player.getCurrentRoom();
						prevRoomInt = 0;
					}
					else if(command.equalsIgnoreCase("Exit"))
					{
						System.out.println("    You have exited the game.");
						System.exit(0);
					}
					else
					{
						System.out.println("Invalid command. Please type 'Start', 'Load', or 'Exit'. If you wish to 'Load', type either 'Load1', 'Load2', or 'Load3' to load their respective saves.");
					}
				}
				restarted = true;
			}
			db.getMonsterList().get(monster.getMonsterName());
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
