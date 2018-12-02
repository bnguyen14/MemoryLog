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
 String path = "savedQuiz.txt";
 public SubjectTester() {
  questions = new ArrayList<DateQuestion>();
 }

 public boolean load() {
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

 public boolean save() {
  Collections.sort(questions, new DateQuestionComparator());
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

 public int ask(DateQuestion question, String answer){
  String userConfirm = "default";
  //final int DEFAULT =-1;
  final int FAILURE = 0;
  final int SUCCESS = 1;
  //final int DELETE  = 2;
  for (int i = 0;i<question.getAnswers().size();i++) {
    if(answer.equals(question.getAnswers().get(i)))
      return SUCCESS;
  }
  return FAILURE;
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

 public ArrayList<DateQuestion> run() {
  Scanner input = new Scanner(System.in);
  LocalDate date;
  date = LocalDate.now();
  OurDate today = new OurDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
  ArrayList<DateQuestion> todayQuestions = new ArrayList<DateQuestion>();
  ArrayList<DateQuestion> missedQuestions = new ArrayList<DateQuestion>();
  boolean previouslyWrong = false;
  boolean questionsToAnswer = false;

  if(load()) {

   //find questions to do today 
   for(int i = 0;i<questions.size();i++) {
    if(questions.get(i).getReviewOn().isLesser(today)) {
     todayQuestions.add(new DateQuestion(questions.get(i)));
     questionsToAnswer = true;
    }
   }

   if(questionsToAnswer) {

    Collections.shuffle(todayQuestions);
    //ask questions to do today
    return todayQuestions;
   }
  }
  return null;
 }

 public void add(String a, String q) {
  Scanner input = new Scanner(System.in);
  boolean moreQuestions = true;
  boolean moreAnswers = true;
  String question = q;
  String answer = a;
  ArrayList<String> answers = new ArrayList<String>();

  if(load()) {
    answers.add(answer);
    questions.add(new DateQuestion(new Question(answers, question)));

   //when finised, sort list.
   Collections.sort(questions, new DateQuestionComparator());

   //write to disk
   save();
  }
 }
}
