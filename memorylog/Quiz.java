package memorylog;
import java.util.ArrayList;

/*
 * Main purpose is to hold a number of Question objects.
 */
public class Quiz {
	
	private String path;
	
	//Holds the title of the quiz object.
	private String title;
	
	//An identifier for what subject this quiz belongs to.
	private int subjectIdentifier;
	
	//An array of questions that are all on the same subject.
	private ArrayList<Question> questions;
	
	public Quiz() {
		this(null,null,0);
	}//End constructor()
	
	public Quiz(Quiz quiz) {
			this.subjectIdentifier = quiz.getSubjectIdentifier();
			this.path = quiz.getPath();
			this.title = quiz.getTitle();
			questions = new ArrayList<Question>();
			for (int i = 0;i<quiz.getQuestions().size();i++) {
				questions.add(new Question());
				questions.get(i).setQuestion(quiz.getQuestions().get(i).getQuestion());
				questions.get(i).setAnswers(new ArrayList<String>());
				for (int j = 0;j<quiz.getQuestions().get(i).getAnswers().size();j++) {
					questions.get(i).getAnswers().add(quiz.getQuestions().get(i).getAnswers().get(j));
				}
			}
	}//End constructor(Quiz)
	
	public Quiz(String title, ArrayList<Question> questions, int subjectIdentifier) {
		this.title = title;
		this.questions = questions;
		this.subjectIdentifier = subjectIdentifier;
	}//End constructor(String, ArrayList<Question>, int)
	
	public void setTitle(String title) {
		this.title = title;
	}//End setTitle(String)
	
	public String getTitle() {
		return title;
	}//End getTitle()
	
	public void setSubjectIdentifier(int subjectIdentifier) {
		this.subjectIdentifier = subjectIdentifier;
	}//End setSubjectIdentifier(int)
	
	public int getSubjectIdentifier() {
		return subjectIdentifier;
	}//End getSubjectIdentifier()
	
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}//End setQuestions(ArrayList<Question>)
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}//End getQuestions()
	
	public String getPath() {
		return path;
	}//End getPath()
	
	public void setPath(String path) {
		this.path = path;
	}//End setPath(String)
	
}//End Quiz
