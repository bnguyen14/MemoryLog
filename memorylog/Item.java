package memorylog;

import java.util.ArrayList;
public class Item {

	//Quiz that is associated
	private Quiz quiz;

	//The number of days to add to the date.
	private int addThis;

	//The date on which the item needs to be reviewed.
	private OurDate reviewOn;

	//The action that the user should take to push the item further down the list.
	private String title;

	private boolean hasQuiz;

	//If true, the item's action changes per review period(ie. review slides 1st/2nd of 1-10, 11-20).
	private boolean toggleable;

	//Holds the possible split actions that change each review period.
	private ArrayList<String> modifiers;

	//Holds the current item from modifiers that user should next perform (ie. 1st, 2nd, etc.).
	private int modifierIdentifier;

	public Item() {
		this(null, 0, null, null, false, false, null, 1);
	}//End constructor()

	public Item(Quiz quiz, int addThis, OurDate reviewOn, String title, boolean hasQuiz, boolean toggleable, ArrayList<String> modifiers, int modifierIdentifier) {
		this.quiz = quiz;
		this.addThis = addThis;
		this.reviewOn = reviewOn;
		this.title = title;
		this.hasQuiz = hasQuiz;
		this.toggleable = toggleable;
		this.modifiers = modifiers;
		this.modifierIdentifier = modifierIdentifier;
	}//End constructor(int, OurDate, String, boolean, ArrayList<String>)
	
	public Item(Item item) {
		this.quiz = item.quiz;
		this.addThis = item.addThis;
		this.reviewOn = new OurDate();
		this.reviewOn.setDay(item.reviewOn.getDay());
		this.reviewOn.setMonth(item.reviewOn.getMonth());
		this.reviewOn.setYear(item.reviewOn.getYear());
		this.title = item.title;
		this.hasQuiz = item.hasQuiz;
		this.toggleable = item.toggleable;
		this.modifiers = new ArrayList<String>();
		for (int i = 0;i<item.modifiers.size();i++) {
			this.modifiers.add(item.modifiers.get(i).toString());
		}
		this.modifierIdentifier = item.modifierIdentifier;
	}//End constructor(Item)

	//Returns a string representation of the object to be used in viewEntries() in MemoryLog.java.
	public String toString() {
		StringBuilder sb = new StringBuilder();

		//Append a star to show that this entry has a quiz associated with it.
		if (hasQuiz) {
			sb.append("*");
		}
		else {
			sb.append(" ");
		}

		sb.append(reviewOn.getYear() + "-" + String.format("%02d", reviewOn.getMonth()) + "-" + String.format("%02d", reviewOn.getDay()));
		sb.append("(" + String.format("%03d", addThis) + ") ");
		sb.append(title);
		if (toggleable == true) {
			sb.append(" [Option " + modifierIdentifier + " of ");
			for (int i = 0;i<modifiers.size();i++) {
				if (i == modifiers.size() - 1) {
					sb.append(modifiers.get(i) + "]");
				}
				else {
					sb.append(modifiers.get(i) + ", ");
				}
			}
		}
		return sb.toString();
	}//End toString()

	//Stores the information in the object in a record that can be written and read from a file.
	public String toRecord() {
		StringBuilder sb = new StringBuilder();
		sb.append(addThis + "\t");
		sb.append(reviewOn.getYear() + "\t" + reviewOn.getMonth() + "\t" + reviewOn.getDay() + "\t");
		sb.append(title + "\t");
		if(hasQuiz)
			sb.append("1\t");
		else sb.append("0\t");
		if(toggleable)
			sb.append("1\t");
		else sb.append("0\t");
		for (int i = 0;i<modifiers.size();i++) {
			sb.append(modifiers.get(i).toString() + "\t");
		}
		sb.append(".\t" + modifierIdentifier);
		return sb.toString();
	}//End toRecord()
	
	public float questionsPerDay() {
		return (float)quiz.getQuestions().size()/(float)addThis;
	}
	
	public boolean hasQuiz() {
		return hasQuiz;
	}
	
	//Getter for quiz.
	public Quiz getQuiz() {
		return quiz;
	}

	//Getter for addThis.
	public int getAddThis() {
		return addThis;
	}//End getAddThis()

	//Setter for addThis.
	public void setAddThis(int addThis) {
		this.addThis = addThis;
	}//End setAddThis()

	//Getter for reviewOn.
	public OurDate getReviewOn() {
		return reviewOn;
	}//End getReviewOn()

	//Setter for reviewOn.
	public void setReviewOn(OurDate reviewOn) {
		this.reviewOn = reviewOn;
	}//End setReviewOn(OurDate)

	//Getter for title.
	public String getTitle() {
		return title;
	}//End getTitle()

	//Setter for title.
	public void setTitle(String title) {
		this.title = title;
	}//End setTitle(String)

	//Getter for toggleable.
	public boolean getToggleable() {
		return toggleable;
	}//End getToggleable;

	//Setter for toggleable.
	public void setToggleable(boolean toogleable) {
		this.toggleable = toggleable;
	}//End setToggleable(boolean)

	//Getter for modifiers.
	public ArrayList<String> getModifiers() {
		return modifiers;
	}//End getModifiers()

	//Setter for modifiers.
	public void setModifiers(ArrayList<String> modiiers) {
		this.modifiers = modifiers;
	}//End setModifiers(ArrayList<String>);
	
	//Getter for modifierIdentifier
	public int getModifierIdentifier() {
		return modifierIdentifier;
	}//End getModifierIdentifier()
	
	//Setter for modifierIdentifier
	public void setModifierIdentifier(int a) {
		modifierIdentifier = a;
	}//End setModifierIdentifier
}
