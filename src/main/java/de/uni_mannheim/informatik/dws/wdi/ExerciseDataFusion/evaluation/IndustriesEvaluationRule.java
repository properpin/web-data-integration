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
package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Actor;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Company;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Movie;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link EvaluationRule} for the actors of {@link Movie}s. The rule simply
 * compares the full set of actors of two {@link Movie}s and returns true, in
 * case they are identical.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class IndustriesEvaluationRule extends EvaluationRule<Company, Attribute> {

	@Override
	public boolean isEqual(Company record1, Company record2, Attribute schemaElement) {
		Set<String> Industry1 = new HashSet<>();
		List<String> s1=  record1.getIndustries();

		for (int i = 0; i< s1.size();i++) {
			// note: evaluating using the actor's name only suffices for simple
			// lists
			// in your project, you should have actor ids which you use here
			// (and in the identity resolution)
			Industry1.add(s1.get(i));
		}

		Set<String> Industry2 = new HashSet<>();
		List<String> s2=  record1.getIndustries();
		for (int j = 0; j< s2.size();j++) {
			Industry2.add(s2.get(j));
		}

		return Industry1.containsAll(Industry2) && Industry2.containsAll(Industry1);
	}

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.datafusion.EvaluationRule#isEqual(java.lang.Object, java.lang.Object, de.uni_mannheim.informatik.wdi.model.Correspondence)
	 */
	@Override
	public boolean isEqual(Company record1, Company record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
