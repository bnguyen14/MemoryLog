package memorylog;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;


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
			return true;
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Unable to read " + f.getAbsolutePath());
			return false;
		}
	}
	
	public boolean save(String path) {
		//TODO
		return false;
	}

	public void run(String path) {
		LocalDate date;
		date = LocalDate.now();
		OurDate today = new OurDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
		ArrayList<DateQuestion> todayQuestions = new ArrayList<DateQuestion>();
		System.out.println("Running " + path);	
		if(load(path)) {
			//TODO find questions to do today	
			for(int i = 0;i<questions.size();i++) {
				if(questions.get(i).getReviewOn().isLesser(today)) {
					todayQuestions.add(new DateQuestion(questions.get(i)));
				}
			}
			for (int i = 0;i<todayQuestions.size();i++) {
				System.out.println(todayQuestions.get(i).toRecord(recordDelimiter));
			}
			
			//TODO ask questions to do today
			
			//TODO as each question is asked, update it in the real list.
			
			//TODO sort real list
			
			//TODO write to disk
		}
	}

	public void add(String path) {
		System.out.println("Adding to " + path);
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
