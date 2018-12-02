package memorylog;

import java.io.File;
import java.io.FileWriter;
import java.io.*;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class QuizTester {
  private ArrayList<String> questions;
  private ArrayList<String> answers;
  private ArrayList<String> rlt;
  private static int number;
  private static int only;
  private static String results;
  private static List <Integer> list; // sau khi tron cau hoi
  String recordDelimiter = "\t";

    public static int getOnly() {
        return only;
    }

    public static void setOnly(int only) {
        QuizTester.only = only;
    }
  
  
    public ArrayList<String> getRlt() {
        return rlt;
    }

    public void setRlt(ArrayList<String> rlt) {
        this.rlt = rlt;
    }
  
  

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public static int getNumber() {
        return number;
    }

    public static void setNumber(int number) {
        QuizTester.number = number;
    }

    public static List<Integer> getList() {
        return list;
    }

    public static void setList(List<Integer> list) {
        QuizTester.list = list;
    }

    public String getRecordDelimiter() {
        return recordDelimiter;
    }

    public void setRecordDelimiter(String recordDelimiter) {
        this.recordDelimiter = recordDelimiter;
    }

    public QuizTester() {
        this.number = 0;
    }

   
  // loading all questions and answers
  public void load() {
    only = 8;
    String path = "savedQuiz.txt";
    questions = new ArrayList <String>();
    answers = new ArrayList <String>();
    ArrayList<String> q = new ArrayList <String>();
    ArrayList<String> a = new ArrayList <String>();
    File f = new File(path);
    Scanner s = null;
    try {
      s = new Scanner(f);
      while(s.hasNextLine()) {
        String[] fields = s.nextLine().split("\t");
        q.add(fields[4]);
        a.add(fields[5]);
    }
      Integer [] lists = new Integer[q.size()];
      for(int i = 0; i<q.size();i++){
          lists[i] = i;
      }
      list = Arrays.asList(lists);
      Collections.shuffle(list);
      
      for(int i = 0; i<q.size();i++){
          questions.add(q.get(list.get(i)));
          answers.add(a.get(list.get(i)));
          System.out.print(answers.get(i));
    }
      s.close();
    } catch (java.io.FileNotFoundException e) {
      System.out.println("Unable to read " + f.getAbsolutePath());
    }
  }
  // Comparing user's answers with the right answers
  public void compare(String input, String answer){
    if (input.equalsIgnoreCase(answer)){
        results = ": Your Answer: " + input + " - Right ";
        try {
            saveRlt();
        } catch (IOException ex) {
            Logger.getLogger(QuizTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    else{
        results = ": Your Answer: " + input + " - Wrong - Answer: " + answer;
        try {
            saveRlt();
        } catch (IOException ex) {
            Logger.getLogger(QuizTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
  }
  //
  // save results
  public void saveRlt () throws IOException
  {

      File inputFile = new File("result.txt");
      //Open file to write
      FileWriter infile = new FileWriter(inputFile,true);
      PrintWriter writer = new PrintWriter(infile);
    
    //write to file
      writer.println(results);
    //Close File
    writer.close();
  }
   public void deleteRlt () throws IOException
  {

      File inputFile = new File("result.txt");
      inputFile.delete();
  }
  // save questions and answer after shuffling
  public void saveR (int num) throws IOException 
  {
      String rlt = "";
      File inputFile = new File("questionAns.txt");
      //Open file to write
      FileWriter infile = new FileWriter(inputFile);
      PrintWriter writer = new PrintWriter(infile);
    
    //write to file
      for (int i = num; i < questions.size(); i++){
          rlt = questions.get(i) + "," + answers.get(i);
          writer.println(rlt);
      }
    //Close File
    writer.close();
  }
  //
  public void loadR() {
    
    String path = "questionAns.txt";
    questions = new ArrayList <String>();
    answers = new ArrayList <String>();
    //results = new ArrayList<String>();
    ArrayList<String> q1 = new ArrayList <String>();
    ArrayList<String> a1 = new ArrayList <String>();
    File f = new File(path);
    Scanner s = null;
    try {
      s = new Scanner(f);
      while(s.hasNext()) {
        String[] fields = s.nextLine().split(",");
        q1.add(fields[0]);
        a1.add(fields[1]);
    }
      for(int i = 0; i<q1.size();i++){
          questions.add(q1.get(i));
          answers.add(a1.get(i));
          
        
    }
      s.close();
    } catch (java.io.FileNotFoundException e) {
      System.out.println("Unable to read " + f.getAbsolutePath());
    }
  }
  //
  public void check (String path) throws FileNotFoundException
  {
      number = 0;
    //String path = "questionAns.txt";
      rlt = new ArrayList<String>();
    File f = new File(path);
    Scanner s = null;

    s = new Scanner(f);
    while(s.hasNextLine()) {
        number = number + 1;
    String[] fields = s.nextLine().split(",");
    if (fields.length==0){
       
    }
    else {
      
        rlt.add(fields[0]);
    }
   }
     
       s.close();
  }
  //
  public static void main (String args[]){
    QuizTester st = new QuizTester ();
    st.load();
  }

    void delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
  