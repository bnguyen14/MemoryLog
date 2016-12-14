package quizletcopy;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.lang.StringBuilder;
import java.util.Random;
import java.io.PrintWriter;

public class TestManager {
	
	String cancelQuizString = "!exit";

	//Quizzes from file system are loaded into this.
	ArrayList<Quiz> quizzes = null;
	
	Quiz savedQuiz = null;

	//Handles the user's input.
	Scanner input = null;

	public String configFilePath;

	public static String recordDelimiter = "\t";
	
	int currentQuiz = 0;

	int configFileLength = 0;
	int configurationSeparatorLine = 0;

	private String savedQuizPath;
	boolean quizInProgress;
	boolean takingSavedQuiz;
	boolean finishedTakingSavedQuiz = true;

	public TestManager() {
		quizzes = new ArrayList<Quiz>();
		input = new Scanner(System.in);
		configFilePath = new String("configuration.txt");
		savedQuizPath = new String("savedQuiz.txt");
	}//End constructor()

	//Main part of program, shows the menu, is the interface through which actions are performed.
	public void runTestManager() {

		//Variables related to the menu.
		int menuChoice;
		int lowestMenuChoice = 0;
		int highestMenuChoice = 2;

		//Display the menu and receive user input.\
		do {
			System.out.print("1. Take Quiz\n2. Load Quizes\n0. Exit\nChoice: ");
			menuChoice = getUserInput(lowestMenuChoice,highestMenuChoice,"Choice: ");
			System.out.println();
			switch (menuChoice) {
			case 1:
				if (quizzes.size() > 0) {
					takeQuiz();
				}
				else {
					System.out.println("No quizzes loaded.");
					pressEnter();
				}
				break;
			case 2:
				try {
					loadQuizzes();
				}
				catch (java.io.FileNotFoundException e) {
					System.out.println("Could not find configuration file.");
				}
				break;
			}
		} while (menuChoice != 0);
	}

	//Moves the user through a tree to allow them to select the right quiz.
	public void selectQuiz() throws java.io.FileNotFoundException {

		//Create resources in order to read from the configuration file.
		File f = new File(configFilePath);
		FileReader r = new FileReader(f);
		Scanner s = new Scanner(r);

		/* Loop through different quiz subjects specified in the configuration file and load them
		 * into an ArrayList.
		 */
		ArrayList<String> subjects = new ArrayList<String>();
		for (int i = 0;i<configurationSeparatorLine;i++) {
			subjects.add(i + ": " + s.nextLine());
		}
		s.close();

		//Receive user input with getUserInput() specifying what subject to display quizes for.
		for (int i = 0;i<subjects.size();i++) {
			System.out.println(subjects.get(i));
		}
		System.out.print("Select subject: ");
		int subjectChoice = getUserInput(0, subjects.size()-1, "Select subject: ");
		System.out.println();

		/*
		 * Loop through the quizzes ArrayList and display all quizzes whose subject identifier 
		 * matches that of the choice selected above. Display index which the user can use to
		 * select what quiz to take.	
		 */
		ArrayList<Quiz> singleSubjectQuizzes = new ArrayList<Quiz>();
		for (int i = 0;i<quizzes.size();i++) {
			if (quizzes.get(i).getSubjectIdentifier() == subjectChoice) {
				singleSubjectQuizzes.add(new Quiz(quizzes.get(i)));
			}
		}
		for (int i = 0;i<singleSubjectQuizzes.size();i++) {
			System.out.printf("%03d" + ": " + singleSubjectQuizzes.get(i).getTitle() + "\n", i);
		}

		//Receive user input on what quiz they want to take from the single subject.
		System.out.print("Select quiz: ");
		int selectedQuiz = getUserInput(0, singleSubjectQuizzes.size()-1, "Select quiz: ");
		
		for (int i = 0;i<quizzes.size();i++) {
			if (quizzes.get(i).getPath().equals(singleSubjectQuizzes.get(selectedQuiz).getPath())) {
				currentQuiz = i;
			}
		}
		System.out.println();

		//Return the Quiz object that the user selected

	}//End selectQuiz()

	//some way to stop a quiz in progress.
	//"Executes" a selected quiz, processing questions and grading the user.
	public void takeQuiz() {
		
		//Select the quiz that is to be taken and store it's information in a temporary object.
		Quiz tempQuiz = null;
		
		//continue saved quiz depending on user input.
		boolean notSkippingSelection = true;
		if (quizInProgress) {
			System.out.print("You have an uncompleted quiz saved.\n0. Continue quiz\n1. Skip for now\n2. Delete quiz\n> ");
			int choice = getUserInput(0,2,"> ");
			switch (choice) {
				case 0:
					//manually assign tempQuiz from below.
					tempQuiz = new Quiz(savedQuiz);
					notSkippingSelection = false;
					System.out.println("Continuing quiz: " + tempQuiz.getTitle());
					takingSavedQuiz = true;
					break;
				case 1:
					System.out.println("\nSaved quiz will be overwritten if you exit this quiz.");
					//just let program continue.
					break;
				case 2:
					//delete content of the saved quiz file, change quizInProgress to false.
					deleteSavedQuiz();
					break;
			}
			System.out.println();
		}

		boolean noExceptionThrown = true;
		
		if (notSkippingSelection) {
			try {
				selectQuiz();
				tempQuiz = new Quiz(quizzes.get(currentQuiz));
			}
			catch (java.io.FileNotFoundException e) {
				System.out.println("Could not find file.");
				pressEnter();
				noExceptionThrown = false;
			}
		}

		if (noExceptionThrown) {

			//Create an array that holds incorrectly answered questions and unanswered questions.
			ArrayList<Question> questionsToAnswer = new ArrayList<Question>();

			/*Load all Question objects from the quiz into the incorrect/unanswered questions 
			 * array in random fashion.
			 */
			Random rand = new Random();
			ArrayList<Question> questionsToAdd = new ArrayList<Question>();
			for (int i = 0;i<tempQuiz.getQuestions().size();i++) {
				questionsToAdd.add(tempQuiz.getQuestions().get(i));
			}
			while (questionsToAdd.size() > 0) {
				int randomSelection = rand.nextInt(questionsToAdd.size());
				questionsToAnswer.add(questionsToAdd.get(randomSelection));
				questionsToAdd.remove(randomSelection);				
			}

			System.out.println("To pause quiz, enter \"" + cancelQuizString + "\"" );
			//While there is still a question in the incorrect/unanswered question array.
			while (questionsToAnswer.size() > 0) {
				
				//Display the question.
				System.out.println(questionsToAnswer.size() + " left. \n" + questionsToAnswer.get(0).getQuestion());

				//Receive the user's answer.
				System.out.print("> ");
				String userAnswer = input.nextLine();

				//Loop through the question's answers to see if it matches any of them.
				boolean userAnsweredRight = false;
				boolean userWantsToExit = false;
				if (userAnswer.equals(cancelQuizString)) {
					userWantsToExit = true;
				}
				for (int i = 0;i<questionsToAnswer.get(0).getAnswers().size();i++) {
					if (userAnswer.equals(questionsToAnswer.get(0).getAnswers().get(i))) {
						userAnsweredRight = true;
					}
				}

				/* If the answer is found within the answers, remove that question from the
				 * questions to answer.
				 */
				if (userAnsweredRight && userWantsToExit == false) {
					questionsToAnswer.remove(0);
				}
				//If the answer is not found.
				else if (userAnsweredRight == false && userWantsToExit == false) {
					System.out.printf("\nIncorrect: %s\n%-11s",userAnswer,"Correct: ");
					for (int i = 0;i<questionsToAnswer.get(0).getAnswers().size();i++) {
						if (i > 0) {
							System.out.print("           ");
						}
						System.out.printf("%s\n",questionsToAnswer.get(0).getAnswers().get(i));
					}
					//Ask the user if they want to add this answer to the answers array.
					System.out.print("Add answer? (0-Yes/1-No/2-I was right): ");
					int addAnswer = getUserInput(0,2,"> ");
					if (addAnswer == 0 && takingSavedQuiz == false) {//If they want to add it, then add it into answers and save to file.
						for (int i = 0;i<quizzes.get(currentQuiz).getQuestions().size();i++) {
							if (quizzes.get(currentQuiz).getQuestions().get(i).getQuestion().equals(questionsToAnswer.get(0).getQuestion())) {
								quizzes.get(currentQuiz).getQuestions().get(i).getAnswers().add(userAnswer);
								questionsToAnswer.get(0).getAnswers().add(userAnswer);
								saveQuizzes();
								System.out.println("Modification saved.");
								questionsToAnswer.remove(0);
								i = quizzes.get(currentQuiz).getQuestions().size();//Kill the loop
							}
						}
					}
					//write new answer to actual quiz, not the saved quiz if we're finishing one.
					else if (addAnswer == 0 && takingSavedQuiz == true) {
						//find quiz that has same title as saved quiz
						for (int i = 0;i<quizzes.size()-1;i++) {
							//find question in quiz that has same title as current savedQuiz question.
							if (savedQuiz.getTitle().equals(quizzes.get(i).getTitle())) {
								for (int j = 0;j<quizzes.get(i).getQuestions().size();j++) {
									if (questionsToAnswer.get(0).getQuestion().equals(quizzes.get(i).getQuestions().get(j).getQuestion())){
										quizzes.get(i).getQuestions().get(j).getAnswers().add(userAnswer);
										questionsToAnswer.get(0).getAnswers().add(userAnswer);
										saveQuizzes();
										System.out.println("Modification saved.");
										questionsToAnswer.remove(0);
										
										//Kill two loops.
										j = quizzes.get(i).getQuestions().size();
										i = quizzes.size()-1;
									}
								}
							}
						}
					}
					/* If not, create temporary Question object, delete from incorrectly 
					 * answered questions, and then re-add it to the incorrectly answered 
					 * questions so that it's located at the end.
					 */
					else if (addAnswer == 1) {
						Question tempQuestion = new Question(questionsToAnswer.get(0));
						questionsToAnswer.remove(0);
						questionsToAnswer.add(tempQuestion);
					}
					else if (addAnswer == 2) {
						questionsToAnswer.remove(0);
					}
				}
				//User wants to pause quiz
				else {
					//save questions to answer to file, exit somehow.
					File f = new File(savedQuizPath);
					PrintWriter p = null;
					boolean savedQuizFileWriteable = true;
					try {
						p = new PrintWriter(f);
					}
					catch (java.io.FileNotFoundException e) {
						System.out.println("Could not create/write to " + savedQuizPath + "\nContinuing...");
						savedQuizFileWriteable = false;
					}
					
					if (savedQuizFileWriteable) {
						finishedTakingSavedQuiz = false;
						//continue as planned.
						//write the subjectIdentifier at the very top.
						p.println(tempQuiz.getSubjectIdentifier());
						//write title of quiz at top to be read later.
						p.println(tempQuiz.getTitle());
						while (questionsToAnswer.size() > 0) {
							p.println(questionsToAnswer.get(0).toRecord(recordDelimiter));
							questionsToAnswer.remove(0);//remove them so that the quiz is terminated.
						}
						p.close();
					}
				}
				System.out.println();//Space inbetween questions.
			}
			if (takingSavedQuiz && finishedTakingSavedQuiz) {
				deleteSavedQuiz();
			}
		}
		takingSavedQuiz = false;
	}//End takeQuiz()
	
	//Saves the information found in the quizzes ArrayList into the specific quizzes found in the configuration file.
	public void saveQuizzes() {
		
		//For each quiz that there is in the quizzes ArrayList.
		for (int i = 0;i<quizzes.size();i++) {
			
			//Create resources
			File f = new File (quizzes.get(i).getPath());
			PrintWriter p = null;
			try {
				p = new PrintWriter(f);
			}
			catch (java.io.FileNotFoundException e) {
				System.out.println("Could not find: " + quizzes.get(i).getPath());
			}
			
			//Write subjectIdentifier at the top.
			p.println(quizzes.get(i).getSubjectIdentifier());
			
			//For each Question object that exists.
			for (int j = 0;j<quizzes.get(i).getQuestions().size();j++) {
				
				//Write the record to the file.
				p.println(quizzes.get(i).getQuestions().get(j).toRecord(recordDelimiter));
			}
			p.close();
		}
	}//End saveQuizes()

	//Enters information from the file system into the quizzes ArrayList.
	public void loadQuizzes() throws java.io.FileNotFoundException {

		//Remove all previous data from the quizzes ArrayList.
		for (int i = 0;i<quizzes.size();i++) {
			quizzes.remove(i);
		}

		//Declare some resources
		File f = new File(configFilePath);
		FileReader r = new FileReader(f);
		Scanner s = new Scanner(r);
		ArrayList<String> configFileLines = new ArrayList<String>();
		ArrayList<String> filePaths = new ArrayList<String>();
		ArrayList<Question> savedQuizQuestions;
		String savedQuizTitle = null;
		int savedQuizSubjectIdentifier = 0;
		configurationSeparatorLine = 0;
		
		//Load all lines from the configuration file.
		while (s.hasNextLine()) {
			configFileLines.add(s.nextLine());
		}
		s.close();
		
		//read from saved quiz file and determine if there is anything in there. Fill in savedQuiz if so.
		File quizFile = new File(savedQuizPath);
		Scanner quizFileScanner = null;
		boolean noExceptionThrown = true;
		try {
			quizFileScanner = new Scanner(quizFile);
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("Could not read from " + savedQuizPath + "\nWill not be able to continue previous quiz.");
			noExceptionThrown = false;
		}
		if (noExceptionThrown) {
			if (quizFileScanner.hasNextLine()) {
				quizInProgress = true;
				savedQuizQuestions = new ArrayList<Question>();
				savedQuizSubjectIdentifier = quizFileScanner.nextInt();
				quizFileScanner.nextLine();
				savedQuizTitle = quizFileScanner.nextLine();
				while (quizFileScanner.hasNextLine()) {
					savedQuizQuestions.add(new Question(quizFileScanner.nextLine(), recordDelimiter));
				}
				savedQuiz = new Quiz(savedQuizTitle, savedQuizQuestions, savedQuizSubjectIdentifier);
			}
			else {
				quizInProgress = false;
			}
			quizFileScanner.close();
		}
		
		//Find separation point between the quiz subjects and the files.
		configFileLength = configFileLines.size();
		for (int i = 0;i<configFileLines.size();i++) {
			if (configFileLines.get(i).isEmpty()) {
				configurationSeparatorLine = i;
				i = configFileLines.size();//End the loop.
			}
		}

		//Add all file paths into another ArrayList.
		for (int i = configurationSeparatorLine+1;i<configFileLines.size();i++) {
			filePaths.add(configFileLines.get(i));
		}

		//Loop through all paths specified after the separation line.
		for (int i = 0;i<filePaths.size();i++) {

			//Create File pointing to file system path.
			File filePath = new File(filePaths.get(i));

			//Instantiate a FileReader and Scanner to read from the file.
			FileReader filePathReader = new FileReader(filePath);
			Scanner filePathScanner = new Scanner(filePathReader);

			int linesInFile = -1;//-1 because we don't want to count the subjectIdentifier line.

			while (filePathScanner.hasNextLine()) {
				filePathScanner.nextLine();
				linesInFile++;
			}
			filePathScanner.close();
			filePathReader = new FileReader(filePath);
			filePathScanner = new Scanner(filePathReader);

			//Add the record's information into the quizzes ArrayList.
			quizzes.add(new Quiz());
			quizzes.get(i).setPath(filePaths.get(i));
			quizzes.get(i).setQuestions(new ArrayList<Question>());
			int subjectIdentifier = filePathScanner.nextInt();
			filePathScanner.nextLine();
			quizzes.get(i).setSubjectIdentifier(subjectIdentifier);
			for (int j = 0;j<linesInFile;j++) {
				String record = filePathScanner.nextLine();
				quizzes.get(i).getQuestions().add(new Question(record, recordDelimiter));
			}
			filePathScanner.close();

			//Read the title from the file.
			StringBuilder tempTitle = new StringBuilder();
			for (int j = filePaths.get(i).length()-1; filePaths.get(i).charAt(j) != '/';j--) {
				tempTitle.append(filePaths.get(i).charAt(j));
			}
			tempTitle = tempTitle.reverse();
			int extensionIndex = tempTitle.toString().indexOf(".txt");
			while (tempTitle.toString().length() > extensionIndex) {
				tempTitle.deleteCharAt(extensionIndex);
			}
			quizzes.get(i).setTitle(tempTitle.toString());
		}

	}//End loadQuizes()

	//Used throughout the program to receive user input.
	public int getUserInput(int lowestNumber, int highestNumber, String prompt) {
		int data = -1;
		while (data == -1) {
			try {
				data = input.nextInt();
				input.nextLine();
				if (data < lowestNumber || data > highestNumber) {
					data = -1;
					System.out.print(prompt);
				}
			}
			catch (java.util.InputMismatchException e) {
				System.out.print(prompt);
				input.nextLine();
				data = -1;
			}
		}
		return data;
	}//End getUserInput(int, int, String)

	//Waits for the user to press the enter key before continuing.
	public void pressEnter() {
		System.out.println("Press enter to continue.");
		input.nextLine();
	}//End pressEnter()
	
	public void deleteSavedQuiz() {
		quizInProgress = false;
		File f = new File(savedQuizPath);
		PrintWriter p = null;
		boolean noExceptionThrown = true;
		try {
			p = new PrintWriter(f);
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("Could not open " + savedQuizPath + "\nDelete manually...");
			noExceptionThrown = false;
		}
		if (noExceptionThrown) {
			p.print("");
			p.close();	
		}		
	}//End deleteSavedQuiz()

}//End TestManager
