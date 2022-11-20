package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ft_db;


// implement only if there is still time left
public class JaroSimilarity {

	
	int m;
	int t;

	
	public double calculate(String first, String second) {
		if (first == null || second == null) {
			return 0.0;
		} else {
			

			double score = Math.abs(l.score(first, second));
			score = score / Math.max(first.length(), second.length());

			return 1 - score;
		}
	}

}