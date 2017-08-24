package memorylog;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class SubjectTester {

	public SubjectTester() {
		
	}

	public void run(String path) {
		System.out.println("Running " + path);	
	}

	public void add(String path) {
		System.out.println("Adding to " + path);
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
