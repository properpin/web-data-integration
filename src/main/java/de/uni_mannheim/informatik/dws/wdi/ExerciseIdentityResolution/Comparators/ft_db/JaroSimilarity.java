package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ft_db;


// implement only if there is still time left
public class JaroSimilarity {



	
	public double calculate(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return 0.0;
		} else {
			if (s1==s2)
				return 1.0;

			int len1 = s1.length();
			int len2 = s2.length();

			// search range for matching characters

			int max_dist = (int)(Math.floor(Math.max(len1,len2)/2)-1);

			int match = 0;

			int hash_s1[]= new int[s1.length()];
			int hash_s2[] = new int[s2.length()];

			for (int i=0; i< len1; i++){
				int start= Integer.max(0,i-max_dist),
						end = Integer.min(len2,i+max_dist+1);
				//if there is a match
				for(int j = start; j< end; j++)
					if (s1.charAt(i) == s2.charAt(j) && hash_s2[j]==0){
						hash_s1[i]=1;
						hash_s2[j]=1;
						match++;
						break;
					}



			}
			// no match
			if(match == 0)
				return 0;

			// tranpositions t

			double t = 0;
			int k=0;
			for (int i=0; i<len1; i++){
				if (hash_s1[i]==1){
					while (hash_s2[k]==0)
						k++;
					if (s1.charAt(i)!=s2.charAt(k))
						t++;
				}
			}
			t/=2;

			// jaro similarity
			return (
					((double)match) / ((double)len1)
							+ ((double)match) / ((double)len2)
							+ ((double)match-t) / ((double)match)
			)/3.0;






//			double score = Math.abs(l.score(first, second));
//			score = score / Math.max(first.length(), second.length());
//
//			return 1 - score;
		}
	}

}