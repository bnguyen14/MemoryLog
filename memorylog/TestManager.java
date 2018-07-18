package memorylog;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.lang.StringBuilder;
import java.util.Random;
import java.io.PrintWriter;
import java.util.Collections;

public class TestManager {

	ArrayList<Question> questions;
	
	public TestManager() {
		questions = new ArrayList<Question>();
	}

	public void runTest(String path) {
		Scanner input = new Scanner(System.in);
		if(loadQuiz(path)) {
			System.out.println("Taking quiz " + path);
			Collections.shuffle(questions);
			while(questions.size() > 0) {
				System.out.println(questions.size() + " left.");
				if(ask(questions.get(0), input) == true)
					questions.remove(0);
				else {
					questions.add(questions.get(0));
					questions.remove(0);
				}
			}
		}
	}

	public boolean ask(Question question, Scanner input) {
		String userConfirm;

		System.out.println(question.getQuestion());
		System.out.print("> ");
		String answer = input.nextLine();
		boolean foundAnswer = false;
		for (int i = 0;i<question.getAnswers().size();i++) {
			if(answer.equals(question.getAnswers().get(i))) 
				foundAnswer = true;
		}
		if(foundAnswer) 
			return true;
		else {
			for(int i = 0;i<question.getAnswers().size();i++) {
				System.out.println(question.getAnswers().get(i));
			}
			System.out.print("Confirm (1-Wrong,2-Actually Correct): ");
			userConfirm = input.nextLine();
			if(userConfirm.equals("2")) {
				return true;
			} else return false;
		}
	}

	public boolean loadQuiz(String path) {
		File f = new File(path);
		Scanner s = null;
		try {
			s = new Scanner(f);
			while(s.hasNextLine()) {
				questions.add(new Question(s.nextLine(), "\t"));
			}
			return true;
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("Unable to read " + f.getAbsolutePath());
			return false;
		}
	}
	
	
	public static void main(String[] args) {
		TestManager testManager = new TestManager();
		if(args.length != 1) {
			System.out.println("Need to supply the path of the quiz to take.");
		} else {
			testManager.runTest(args[0]);
		}
	}
}
