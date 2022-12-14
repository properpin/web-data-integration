/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ft_db;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Company;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.DBpedia;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Ft;

import org.apache.commons.math3.util.CombinatoricsUtils;

/**
 * {@link Comparator} for {@link Movie}s based on the {@link Movie#getTitle()}
 * value and their {@link LevenshteinSimilarity} value.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class CeoNameComparatorJaroSimilarity implements Comparator<Company, Attribute> {
	
	private static final long serialVersionUID = 1L;
	private JaroSimilarity sim = new JaroSimilarity();
	
	private ComparatorLogger comparisonLog;

	@Override
	public double compare(
			Company record1,
			Company record2,
			Correspondence<Attribute, Matchable> schemaCorrespondences) {
		
		List<Double> combinationsSimilarity = new ArrayList<>();
		double meanSimilarity = 0;
		List<String> s1 = record1.getIndustries();
		List<String> s2 = record2.getIndustries();
		
		
		// for each industry in list s1
		for (int i=0; i < s1.size(); i++) {
			String name1 = s1.get(i); // get industry
			
			
			if (name1 == null) {
				continue;
			} else {
				
				// for each ceo name in list s2
				for (int j=0; j < s2.size(); j++) {
					String name2 = s2.get(j); // get name
					
					if (name2 == null) {
						continue;
					} else {
						double similarity = sim.calculate(name1, name2); // calc similarity
						
						if (Double.isNaN(similarity)) {
							continue;
						} else {
							combinationsSimilarity.add(similarity); // add to sim list
						}
						
						
					}
					
					
				}
				
			}
			
		}
		
		double sumOfSimilarities = 0;
		
		for (int i=0; i < combinationsSimilarity.size(); i++) {
			double sumToAdd= combinationsSimilarity.get(i);
			
			sumOfSimilarities += sumToAdd;
			
		}
		
		meanSimilarity = sumOfSimilarities / 
				combinationsSimilarity.size();

		
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
		
			this.comparisonLog.setRecord1Value("CeoNameListRecord1");
			this.comparisonLog.setRecord2Value("CeoNameListRecord2");
    	
			this.comparisonLog.setSimilarity(Double.toString(meanSimilarity));
		}
		
		return meanSimilarity;
		
	}

	@Override
	public ComparatorLogger getComparisonLog() {
		return this.comparisonLog;
	}

	@Override
	public void setComparisonLog(ComparatorLogger comparatorLog) {
		this.comparisonLog = comparatorLog;
	}

}