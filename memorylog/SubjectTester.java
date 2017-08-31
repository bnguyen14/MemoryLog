package memorylog;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Collections;

public class SubjectTester {

	ArrayList<DateQuestion> questions;
	String recordDelimiter = "\t";

	public SubjectTester() {
		questions = new ArrayList<DateQuestion>();
	}

	public boolean load(String path) {
		File f = new File(path);
		Scanner s = null;
		try {
			s = new Scanner(f);
			while(s.hasNextLine()) {
				questions.add(new DateQuestion(s.nextLine(), recordDelimiter));
			}
			s.close();
			return true;
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Unable to read " + f.getAbsolutePath());
			return false;
		}
	}
	
	public boolean save(String path) {
		File f = new File(path);
		PrintWriter p = null;
		try {
			p = new PrintWriter(f);
			for(int i = 0;i<questions.size();i++) {
				p.println(questions.get(i).toRecord("\t"));
			}
			p.close();
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Unable to write to " + f.getAbsolutePath());
		}
		return false;
	}
	
	public boolean ask(DateQuestion question, Scanner input) {
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
	
	//return question that matches the parameter.
	public DateQuestion match(DateQuestion question) {
		for(int i = 0;i<questions.size();i++) {
			if(questions.get(i).getQuestion().equals(question.getQuestion())) {
				return questions.get(i);
			}
		}
		return null;
	}
	
	public void run(String path) {
		LocalDate date;
		date = LocalDate.now();
		OurDate today = new OurDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
		ArrayList<DateQuestion> todayQuestions = new ArrayList<DateQuestion>();
		ArrayList<DateQuestion> missedQuestions = new ArrayList<DateQuestion>();
		Scanner input = new Scanner(System.in);
		boolean previouslyWrong = false;;

		if(load(path)) {

			//find questions to do today	
			for(int i = 0;i<questions.size();i++) {
				if(questions.get(i).getReviewOn().isLesser(today)) {
					todayQuestions.add(new DateQuestion(questions.get(i)));
				}
			}

			Collections.shuffle(todayQuestions);

			//TODO ask questions to do today
			while(todayQuestions.size() > 0) {
				previouslyWrong = false;
				System.out.printf("%d left.\n", todayQuestions.size());

				//as each question is asked, update it in the real list.
				for(int i = 0;i<missedQuestions.size();i++) {
					if(todayQuestions.get(0).getQuestion().equals(missedQuestions.get(i).getQuestion())) {
						previouslyWrong = true;
					}
				}

				if(ask(todayQuestions.get(0), input) == true) {
					if(previouslyWrong == false) 
						match(todayQuestions.get(0)).increasePeriod(today);	
					todayQuestions.remove(0);
				} else {
					match(todayQuestions.get(0)).decreasePeriod(today);
					todayQuestions.get(0).decreasePeriod(today);

					if(previouslyWrong == false)
						missedQuestions.add(todayQuestions.get(0));

					todayQuestions.add(new DateQuestion(todayQuestions.get(0)));
					todayQuestions.remove(0);
				}
			}
			
			//sort real list
			Collections.sort(questions, new DateQuestionComparator());
			
			//write to disk
			save(path);
		}
	}

	public void add(String path) {
		if(load(path)) {
			//TODO enter questions into list
			
			//TODO when finised, sort list.
			
			//TODO write to disk
		}
	}


	public static void main(String[] args) {
		
		String usage = "java memorylog.SubjectTester <command> <path/to/subject_file>\n\n" +
			"Commands: \n" +
			"run\n" +
			"\tRun the given subject file.\n" +
			"add\n" +
			"\tAdd questions to the given subject file.\n";

		if(args.length != 2) {
			System.out.println("Wrong number of arguments.");
			System.out.println(usage);
		} else if (!args[0].equals("add") && !args[0].equals("run")) {
			System.out.println("Unrecognized command.");
			System.out.println(usage);
		} else {
			SubjectTester subjectTester = new SubjectTester();
			String command = args[0];
			switch (command) {
				case "run":
					subjectTester.run(args[1]);
					break;
				case "add":
					subjectTester.add(args[1]);
					break;
			}
		}	
	}
}
