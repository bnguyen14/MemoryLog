package memorylog;

import java.util.ArrayList;
public class DateQuestion extends Question {
	private int addThis;
	private OurDate reviewOn;

	public DateQuestion() {
		super();
		addThis=0;
		reviewOn = null;
	}
	
	public DateQuestion(DateQuestion dateQuestion) {
		super(dateQuestion.answers, dateQuestion.question);
		this.addThis = dateQuestion.addThis;
		this.reviewOn = new OurDate(dateQuestion.reviewOn);
	}

	public DateQuestion(String record, String recordDelimiter) {
		answers = new ArrayList<String>();
		String[] fields = record.split("\t");
		addThis = Integer.parseInt(fields[0]);
		reviewOn = new OurDate(Integer.parseInt(fields[3]), Integer.parseInt(fields[2]), Integer.parseInt(fields[1]));
		question = fields[4];
		for(int i = 5; i<fields.length;i++) {
			answers.add(fields[i]);
		}
	}
	
	public OurDate getReviewOn() {
		return this.reviewOn;
	}
	
	public void increasePeriod(OurDate today) {
		if(addThis%2 == 0) {
			addThis = (int)(addThis + addThis*0.5);
		} else {
			addThis = 1+(int)(addThis + addThis*0.5);
		}

		reviewOn = new OurDate(today);

		for(int i = 0;i<addThis;i++) {
			reviewOn.addOne();
		}
	}

	public void decreasePeriod(OurDate today) {
		reviewOn = new OurDate(today);
		addThis = 1;
		reviewOn.addOne();
	}
	
	//Returns a string that is written to a file to be read later.
	public String toRecord(String recordDelimiter) {
		StringBuilder sb = new StringBuilder();
		//Write addThis
		sb.append(addThis + recordDelimiter);
	
		//Write date
		sb.append(reviewOn.getYear() + recordDelimiter);
		sb.append(reviewOn.getMonth() + recordDelimiter);
		sb.append(reviewOn.getDay() + recordDelimiter);

		//Write the question.
		sb.append(question + recordDelimiter);

		//Write each answer that is in the answers ArrayList.
		for (int i = 0;i<answers.size();i++) {
			sb.append(answers.get(i) + recordDelimiter);
		}
		
		return sb.toString();
	}

}


	/*//Holds a list of several possible answers to a question.
	private ArrayList<String> answers;
	
	//Holds a question.
	private String question;
	
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
}*/
