package memorylog;

import java.util.ArrayList;
public class Question {
	
	//Holds a list of several possible answers to a question.
	protected ArrayList<String> answers;
	
	//Holds a question.
	protected String question;
	
	public Question() {
		this.question = null;
		this.answers = null;
	}//End constructor()
	
	public Question(ArrayList<String> answers, String question) {
		this.answers = answers;
		this.question = question;
	}//End constructor(String, String)
	
	public Question(Question question) {
		this(question.getAnswers(), question.getQuestion());
	}//End constructor(Question)
	
	public Question(String record, String recordDelimiter) {
		//Read the answers from the quiz record.
		answers = new ArrayList<String>();
		StringBuilder dynamicRecord = new StringBuilder(record);
		StringBuilder nextAnswer = new StringBuilder();
		while (dynamicRecord.toString().contains(recordDelimiter)) {
			int nextDelimiterIndex = dynamicRecord.toString().indexOf(recordDelimiter);
			for (int i = 0;i<nextDelimiterIndex;i++) {
				nextAnswer.append(dynamicRecord.toString().charAt(i));
			}
			answers.add(nextAnswer.toString());
			nextAnswer = new StringBuilder();
			for (int i = 0;i<nextDelimiterIndex+recordDelimiter.length();i++) {
				dynamicRecord.deleteCharAt(0);
			}
		}
		//Read the question from the quiz record.
		StringBuilder chopOffAnswers = new StringBuilder(record);
		while (chopOffAnswers.toString().contains(recordDelimiter)) {
			int nextDelimiterIndex = chopOffAnswers.indexOf(recordDelimiter);
			for (int i = 0;i<nextDelimiterIndex+recordDelimiter.length();i++) {
				chopOffAnswers.deleteCharAt(0);
			}
		}
		question = chopOffAnswers.toString();
	}
	
	//Returns a string that is written to a file to be read later.
	public String toRecord(String recordDelimiter) {
		StringBuilder sb = new StringBuilder();
		//Write each answer that is in the answers ArrayList.
		for (int i = 0;i<answers.size();i++) {
			sb.append(answers.get(i) + recordDelimiter);
		}
			
		//Write the question.
		sb.append(question);
		return sb.toString();
	}//End toRecord()
	
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}//End setAnswers(Arraylist<String>)
	
	public ArrayList<String> getAnswers() {
		return answers;
	}//End getAnswers()
	
	public void setQuestion(String question) {
		this.question = question;
	}//End setQuestion(String)
	
	public String getQuestion() {
		return question;
	}//End getQuestion()
}
