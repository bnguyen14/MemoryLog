package memorylog;

//OurDate is a date entity.
public class OurDate {
	
	//Data fields that hold the day, month, and year of the date.
	private int day;
	private int month;
	private int year;
	
	//months serves as a reference to see how many days exist in each month.
	private int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	//Creates a default OurDate object.
	public OurDate() {
		day = 1;
		month = 1;
		year = 2000;
	}//End constructor()

	//Creates an OurDate object using inputted information.
	public OurDate(int dayHolder, int monthHolder, int yearHolder) {
		day = dayHolder;
		month = monthHolder;
		year = yearHolder;
	}//End constructor(int,int,int)

	//Used to return the OurDate object to original values.
	public void resetObject() {
		day = 1;
		month = 1;
		year = 2000;
		months[1] = 28;
	}//End resetObject()
	
	//returns the object's day
	public int getDay() {
		return day;
	}//End getDay()

	//returns the object's month
	public int getMonth() {
		return month;
	}//End getMonth()

	//returns the object's year.
	public int getYear() {
		return year;
	}//End getYear()

	//receives an integer and sets it as the object's day.
	public void setDay(int dayHolder) {
		if (dayHolder < 1 || dayHolder > months[month-1]) {
			dayHolder = 1;
		}
		day = dayHolder;
	}//End setDay(int)

	//receives an integer and sets it as the object's month.
	public void setMonth (int monthHolder) {
		if (monthHolder < 1 || monthHolder > 12) {
			monthHolder = 1;
		}
		month = monthHolder;
	}//End setMonth(int)

	//receives an integer and sets it as the object's year.
	public void setYear (int yearHolder) {
		if (yearHolder < 2000) {
			yearHolder = 2000;
		}
		year = yearHolder;
	}//End setYear(int)

	//Determines if the given OurDate object is equal to another.
	public boolean isEqual (OurDate dateHolder) {
		if (dateHolder.calcDays() == this.calcDays()) {
			return true;
		}
		else {
			return false;
		}
	}//End isEqual(OurDate)

	//Adds one 'day' to the date.
	public void addOne() {
		int changedSomething = 0;
		if (day == months[month-1]) {
			changedSomething = 1;
			if (month < 12) {
				day = 1;
				month++;
			}
			if (month == 12) {
				day = 1;
				month = 1;
				year++;
			}
		}
		if (changedSomething == 0 && day < months[month-1]) {
			day++;
		}
	}//End addOne()

	//returns a (DD/MM/YYYY) representation of the date.
	public String toString() {
		String displayDate = new String();
		displayDate = month + "/" + day + "/" + year;
		return displayDate;
	}//End toString()

	//calculates the number of days that exist beginning from 0 of the current object.
	/*
	 * This method could be fully functional if it was made so that it would count 
	 * how many leap years there were in between the object's value and 1,1,2000
	 * because keep in mind that some years have 366 days and not 365.
	 */
	public int calcDays() {
		int numDay = 0;
		if (month > 1) {
			for (int i = 0;i<month-1;i++) {
				numDay+=months[i];
			}
			numDay+= day + (year*365);
		}
		else {
			numDay = day + (year*365);
		}
		return numDay;
	}//End calcDays()

	//determines if the given date is a leap year.
	public boolean isLeapYear(int year) {
		if((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			return true;
		}
		else {
			return false;
		}
	}//End isLeapYear(int)
	
	//augments the months array to leap year settings.
	public void setLeapYear() {
		months[1] = 29;
	}//End setLeapYear()
	
} //End OurDate