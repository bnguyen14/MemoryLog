package memorylog;

import java.util.Comparator;

public class DateQuestionComparator implements Comparator<DateQuestion> {
	public int compare(DateQuestion d1, DateQuestion d2){
		return Integer.compare(d1.getReviewOn().calcDays(), d2.getReviewOn().calcDays());
	}
}
