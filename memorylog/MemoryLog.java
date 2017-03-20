package memorylog;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;

class MemoryLog {

	Scanner scan;
	ArrayList<Item> entries;
	File itemList;
	TestManager testManager;

	//Used to determine what day is today. Used with viewTodaysEntries().
	LocalDate date;

	public MemoryLog() throws java.io.FileNotFoundException {
		scan = new Scanner(System.in);
		entries = new ArrayList<Item>();
		itemList = new File("memorylog/auto_memory_log.txt");
		date = LocalDate.now();
		testManager = new TestManager();
		testManager.loadQuizzes();
		loadEntries();
	}

	//Takes the information from a file and populates the ArrayList with Item objects from it.
	public void loadEntries() {
		Scanner s = null;
		boolean noExceptionThrown = true;

		//Remove all current entries.
		while (entries.size() != 0) {
			entries.remove(0);
		}

		//Open the input stream.
		try {
			s = new Scanner(itemList);
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("Could not load " + itemList.getAbsolutePath());
			noExceptionThrown = false;
		}

		if (noExceptionThrown) {

			//Create temporary holders for all of the values in each line of the file.
			Quiz tempQuiz = null;
			int tempAddThis = 0;
			int tempYear = 0;
			int tempMonth = 0;
			int tempDay = 0;
			String tempTitle = null;
			boolean tempHasQuiz = false;
			boolean tempToggleable = false;
			ArrayList<String> tempModifiers = new ArrayList<String>();
			int tempModifierIdentifier = 0;
			String record = null;

			//While there is still information in the file.
			while (s.hasNextLine()) {
				record = s.nextLine();
				
				//Allow full-line comments
				while (record.charAt(0) == '#') {
					record = s.nextLine();
				}
				
				StringBuilder sb = new StringBuilder();
				int offset = 0;

				//Get the addThis value.
				for(;record.charAt(offset) != '\t';offset++) {
					sb.append(record.charAt(offset));
				}
				tempAddThis = Integer.parseInt(sb.toString());
				offset++;
				sb = new StringBuilder();

				//Get the year from the file.
				for (;record.charAt(offset) != '\t';offset++) {
					sb.append(record.charAt(offset));
				}
				tempYear = Integer.parseInt(sb.toString());
				offset++;
				sb = new StringBuilder();

				//Get the month from the file.
				for (;record.charAt(offset) != '\t';offset++) {
					sb.append(record.charAt(offset));
				}
				tempMonth = Integer.parseInt(sb.toString());
				offset++;
				sb = new StringBuilder();

				//Get the day from the file.
				for (;record.charAt(offset) != '\t';offset++) {
					sb.append(record.charAt(offset));
				}
				tempDay = Integer.parseInt(sb.toString());
				offset++;
				sb = new StringBuilder();

				//Get the title from the file.
				for (;record.charAt(offset) != '\t';offset++) {
					sb.append(record.charAt(offset));
				}
				tempTitle = sb.toString();
				offset++;
				sb = new StringBuilder();

				//Get the hasQuiz from file
				for (;record.charAt(offset) != '\t';offset++) {
					sb.append(record.charAt(offset));
				}
				if (sb.toString().equals("1")) {
					tempHasQuiz = true;
				}
				else {
					tempHasQuiz = false;
				}
				offset++;
				sb = new StringBuilder();

				//Get the toggleable from file
				for (;record.charAt(offset) != '\t';offset++) {
					sb.append(record.charAt(offset));
				}
				if (sb.toString().equals("1")) {
					tempToggleable = true;
				}
				else {
					tempToggleable = false;
				}
				offset++;
				sb = new StringBuilder();

				//Get the modifiers from the file.
				for (;record.charAt(offset) != '.';offset++) {
					for (;record.charAt(offset) != '\t';offset++) {
						sb.append(record.charAt(offset));
					}
					tempModifiers.add(sb.toString());
					sb = new StringBuilder();
				}
				offset+=2;
				sb = new StringBuilder();

				//Get the modifierIdentifier from the file
				for (;offset < record.length();offset++) {
					sb.append(record.charAt(offset));
				}
				tempModifierIdentifier = Integer.parseInt(sb.toString());

				//Assign the matching quiz reference in memory to tempQuiz. 
				if(tempHasQuiz) {
					boolean found = false;
					for(int i = 0;i<testManager.quizzes.size();i++) {
						if(testManager.quizzes.get(i).getTitle().equals(tempTitle)) {
							tempQuiz = testManager.quizzes.get(i);
							found = true;
						}
					}
					if(found == false)
						System.out.println("Unable to find matching quiz for " + tempTitle);
				}	

				entries.add(new Item(tempQuiz, tempAddThis, new OurDate(tempDay, tempMonth, tempYear), tempTitle, tempHasQuiz, tempToggleable, tempModifiers, tempModifierIdentifier ));
				sb = null;
				tempModifiers = new ArrayList<String>();
			}
		}
	}

	/*
	 * Takes the information stored in the entries ArrayList and writes it to a file to be read by
	 * loadEntries or by a user on the computer.
	 */
	public void saveEntries() {
		System.out.print("Confirm save: 0-Yes/1-No: ");
		int choice = 1;
		boolean noExceptionThrown = true;
		if (scan.hasNextInt()) {
			choice = scan.nextInt();
			scan.nextLine();
		}
		else {
			System.out.println("Invalid input, cancelling save.");
			scan.nextLine();
			noExceptionThrown = false;
		}
		
		//Only if the user says to continue.
		if (choice == 0 && noExceptionThrown) {
			if (entries.size() > 0) {
				//Arrange the entries in the proper order before saving.
				Collections.sort(entries, new DateComparator());
				PrintWriter p = null;
				try {
					p = new PrintWriter(itemList);
				}
				catch (java.io.FileNotFoundException e) {
					System.out.println("There was a problem when writing to the file.");
				}
	
				for (int i = 0;i<entries.size();i++) {
					if (i == entries.size()-1) {
						p.printf("%s", entries.get(i).toRecord());	
					}
					else {
						p.printf("%s\n", entries.get(i).toRecord());
					}
				}
				p.close();
			}
			else {
				System.out.println("No entries to save.");
				pressEnter();
			}
		}
		else {
			if (noExceptionThrown == true) {
				System.out.println("Cancelling save.");
			}
			pressEnter();
		}
	}

	//Changes an entry, adding days to the reviewOn and incrementing the modifier identifier.
	public void processIndex(Item passedItem, int addThis) {
		passedItem.setAddThis(addThis);
		
		//Set date to current date, start to add days.
		OurDate today = new OurDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
		passedItem.setReviewOn(today);
		for (int i = 0;i<passedItem.getAddThis();i++) {
			passedItem.getReviewOn().addOne();
		}
		
		//Change modifierIdentifier if entry has modifiers.
		if (passedItem.getToggleable()) {
			int holder = passedItem.getModifierIdentifier();
			if (holder < passedItem.getModifiers().size()) {
				passedItem.setModifierIdentifier(holder+1);
			}
			else {
				passedItem.setModifierIdentifier(1);
			}
		}
		
	}

	//Prints out entries based on the numbers within a passed array.
	public void viewEntries(int[] indexes) {
		//Prints out all of the entries that the user has.

		if (entries.size() > 0) {
			for (int i = 0;i<indexes.length;i++) {
				System.out.println( String.format("%03d",i) + ": " + entries.get(indexes[i]).toString());
			}
		}
		else {
			System.out.println("No entries.");
		}
	}

	/*
	 * Finds out what indexes of the entries ArrayList happen on the current date or previously and
	 * then passes a normal array with those indexes to the viewEntries() method. Asks the user
	 * whether or not they want to move an entry forward depending on a new menu choice.
	 */
	public void viewTodaysEntries() {
		//Prints out the entries that are on the current day.

		boolean hasTodayEntry = false;
		//Create ArrayList with all entries that have the date of today.
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		OurDate today = new OurDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
		for (int i = 0;i<entries.size();i++) {
			if (entries.get(i).getReviewOn().calcDays() <= today.calcDays()) {
				indexes.add(i);
				hasTodayEntry = true;
			}
		}

		int[] passedIndexes = new int[indexes.size()];
		if (hasTodayEntry) {

			//Convert the ArrayList to a normal array to be passed into viewEntries().
			for (int i = 0;i<indexes.size();i++) {
				passedIndexes[i] = indexes.get(i);
			}

			//Ask the user if they'll be moving entries around.
			int choice = -1;
			System.out.print("Will you be processing entries?\n0. Yes\n1. No\n");
			try {
				while (choice != 0 && choice != 1) {
					System.out.print("Choice: ");
					choice = scan.nextInt();
					scan.nextLine();
				}
				System.out.println();
				if (choice == 0) {
					viewEntries(passedIndexes);
					System.out.print("Index to process: ");
					int index = scan.nextInt();
					while(index < 0 || index > entries.size()-1) {
						System.out.print("Invalid input, reenter: ");
						index = scan.nextInt();
					}
					scan.nextLine();

					//Show current record information
					System.out.println(String.format("\nCurrent: ") + entries.get(index).toString());
					System.out.print("Set new addThis: ");
					int addThis = scan.nextInt();
					scan.nextLine();
					
					//Create an item to display what the entry will be changed to before it happens.
					Item tempItem = new Item(entries.get(index));
					processIndex(tempItem, addThis);

					//Show new record information
					System.out.println(String.format("\n%10s", "Previous: ") + entries.get(index).toString());
					System.out.println(String.format("%10s", "New: ") + tempItem.toString());
					System.out.print("Confirm to continue: 0-Yes/1-No: ");
					int checker = scan.nextInt();
					scan.nextLine();
					System.out.println();
					if (checker == 0) {
						processIndex(entries.get(index), addThis);
						Collections.sort(entries, new DateComparator());
					}
					else {
						System.out.println("Cancelled.");
						pressEnter();
					}
				}

				//choice is 1
				else {
					viewEntries(passedIndexes);
					pressEnter();
				}
			}
			catch (java.util.InputMismatchException e) {
				System.out.println("Invalid input, cancelling action.");
				scan.nextLine();
				pressEnter();
			}
		}

		//If there are no entries with the a reviewOn up until today.
		else {
			System.out.println("No entries today.");
			pressEnter();
		}

		//Recover the memory.
		today = null;
		indexes = null;
		passedIndexes = null;
	}

	public void printMenu() {
		System.out.print("1. View entries for today\n"
				+ "2. View all entries\n"
				+ "3. Take quiz\n"
				+ "4. Add entry\n"
				+ "5. Remove entry\n"
				+ "6. Reload entries\n"
				+ "7. Save entries\n"
				+ "0. Exit\nChoice: ");
	}

	//Used to add a new entry into the ArrayList.
	public void addEntry() {
/*
		//Create temporary variables that the user will enter information into.
		int tempAddThis = 0;
		int tempYear = 0;
		int tempMonth = 0;
		int tempDay = 0;
		String tempTitle = null;
		boolean tempToggleable = false;
		ArrayList<String> tempModifiers = new ArrayList<String>();
		int tempModifierIdentifier = 0;
		boolean noExceptionThrown = true;

		//Enter values for temporary variables.
		try {
			System.out.print("Add this: ");
			tempAddThis = scan.nextInt();
			scan.nextLine();
			System.out.print("Year: ");
			tempYear = scan.nextInt();
			scan.nextLine();
			System.out.print("Month: ");
			tempMonth = scan.nextInt();
			scan.nextLine();
			System.out.print("Day: ");
			tempDay = scan.nextInt();
			scan.nextLine();
			System.out.print("Title: ");
			tempTitle = scan.nextLine();
			System.out.print("Toggleable? 0-Yes/1-No: ");
			int holder = scan.nextInt();
			scan.nextLine();
			if (holder == 0) {
				tempToggleable = true;
			}
			else {
				tempToggleable = false;
			}
		}
		catch (java.util.InputMismatchException e) {
			System.out.println("Invalid input, cancelling addition.");
			scan.nextLine();
			pressEnter();
			noExceptionThrown = false;
		}

		//Only if an exception wasn't thrown.
		if (noExceptionThrown) {

			//If the user has a modifiable entry
			if (tempToggleable == true) {
				System.out.print("Enter modifier: ");
				tempModifiers.add(scan.nextLine());
				System.out.print("Continue? 0-Yes/1-No: ");
				try {
					int holder2 = scan.nextInt();
					scan.nextLine();
					while (holder2 != 1) {
						System.out.print("Enter modifier: ");
						tempModifiers.add(scan.nextLine());
						System.out.print("Continue? 0-Yes/1-No: ");
						holder2 = scan.nextInt();
						scan.nextLine();
					}
					System.out.print("Enter starting identifier: ");
					tempModifierIdentifier = scan.nextInt();
					scan.nextLine();
				}
				catch (java.util.InputMismatchException e) {
					System.out.println("Invalid input, cancelling addition.");
					scan.nextLine();
					pressEnter();
					noExceptionThrown = false;
				}
			}

			//Add new entry into the ArrayList based on the entered values.
			if (noExceptionThrown) {
				entries.add(new Item(tempAddThis, new OurDate(tempDay, tempMonth, tempYear), tempTitle, tempToggleable, tempModifiers, tempModifierIdentifier));
				System.out.println();
			}
		}*/
	}

	//Used to remove an entry from the ArrayList.
	public void removeEntry() {
		int choice = 0;
		if (entries.size() != 0) {
			System.out.print("Delete this index: ");
			try {
				choice = scan.nextInt();
				scan.nextLine();
				entries.remove(choice);
			}
			catch (java.util.InputMismatchException e) {
				System.out.println("Invalid input, cancelling deletion.");
				scan.nextLine();
				pressEnter();
			}
		}
		else {
			System.out.println("No entries to delete.");
			pressEnter();
		}
	}

	//User must press enter to continue
	public void pressEnter() {
		System.out.println("Press enter to continue...");
		scan.nextLine();
	}

	/*
	 * Main part of the program, offers a menu that the user can choose options from. Allows
	 * the user to view their entries, move them, and exit.
	 */
	public void runMenu() {

		int choice = -1;

		//While user does not say to quit the program.
		while (choice != 0) {

			/*
			 * Gets information from the user about what they want to do.
			 * 'choice != -2' is used with the try catch to make sure that the
			 * menu doesn't display if they type a non-numeric value.
			 */
			if (choice != -2) {
				printMenu();
			}
			else {
				System.out.print("Choice: ");
			}

			try {
				choice = scan.nextInt();
				System.out.println();
				scan.nextLine();
				while (choice < 0 || choice > 7) {
					System.out.print("Choice: ");
					choice = scan.nextInt();
					scan.nextLine();
				}
			}
			catch (java.util.InputMismatchException e) {
				choice = -2;
				scan.nextLine();
			}

			if (choice > 0 && choice < 8) {			

				//User has made a choice, time to do what they said.
				switch (choice) {
				case 1:
					viewTodaysEntries();
					break;
				case 2:

					//Create an array with all indexes to pass into the viewEntries method.
					int[] allIndexes = new int[entries.size()];
					for (int i = 0; i < entries.size(); i++) {
						allIndexes[i] = i;
					}
					viewEntries(allIndexes);
					pressEnter();
					break;
				case 3:
					testManager.takeQuiz();
					break;
				case 4:
					addEntry();
					break;
				case 5:
					removeEntry();
					System.out.println();
					break;
				case 6:
					loadEntries();
					break;
				case 7:
					saveEntries();
					break;
				}
			}
		}
	}

	//Main method just runs the runMenu method in MemoryLog class.
	public static void main(String[] args) {
		try {
			MemoryLog log = new MemoryLog();
			log.runMenu();
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Failed to load quizzes: could not find config file/perhaps a quiz is missing?");
		}
	}
}
