import java.util.Comparator;


class ScoreComparator implements Comparator<Score>{
	public int compare(Score first, Score second){
		float firstValue = first.rate;
		float secondValue = second.rate;
		
		//Order by descending
		if(firstValue>secondValue)
			return -1;
		else if(firstValue<secondValue)
			return 1;
		else 
			return 0;
		
	}

}
