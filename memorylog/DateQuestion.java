package memorylog;

import java.util.ArrayList;
import java.time.LocalDate;

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
	
	public DateQuestion(Question q) {
		LocalDate today;
		today = LocalDate.now();

		reviewOn = new OurDate(today.getDayOfMonth(), today.getMonthValue(), today.getYear());
		question = q.question;
		answers = q.answers;
		addThis = 0;
	}
	
	public OurDate getReviewOn() {
		return this.reviewOn;
	}
	
	public void increasePeriod(OurDate today) {
		if(addThis==0) {
			addThis=1;
		} else if (addThis%2 == 0) {
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
