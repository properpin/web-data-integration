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
package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers;


import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Company;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.Voting;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

import java.time.LocalDateTime;


public class NameFuserVoting extends AttributeValueFuser<String, Company, Attribute> {

	public NameFuserVoting() {
		super(new Voting<String, Company, Attribute>());
	}
	
	@Override
	public boolean hasValue(Company record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Company.NAME);
	}
	
	@Override
	public String getValue(Company record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getName();
	}

	@Override
	public void fuse(RecordGroup<Company, Attribute> group, Company fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<String, Company, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setName(fused.getValue());
		fusedRecord.setAttributeProvenance(Company.NAME, fused.getOriginalIds());
	}

}
