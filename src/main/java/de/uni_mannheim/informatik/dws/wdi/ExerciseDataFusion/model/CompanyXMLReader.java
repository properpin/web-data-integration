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
package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

/**
 * A {@link XMLMatchableReader} for {@link Movie}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class CompanyXMLReader extends XMLMatchableReader<Company, Attribute> implements
FusibleFactory<Company, Attribute> {

/* (non-Javadoc)
* @see de.uni_mannheim.informatik.wdi.model.io.XMLMatchableReader#initialiseDataset(de.uni_mannheim.informatik.wdi.model.DataSet)
*/
@Override
protected void initialiseDataset(DataSet<Company, Attribute> dataset) {
super.initialiseDataset(dataset);

// the schema is defined in the Movie class and not interpreted from the file, so we have to set the attributes manually
dataset.addAttribute(Company.NAME);
dataset.addAttribute(Company.REVENUE);
dataset.addAttribute(Company.NUMBEROFEMPLOYEES);
dataset.addAttribute(Company.INDUSTRIES);
dataset.addAttribute(Company.CITY);
dataset.addAttribute(Company.FOUNDINGYEAR);
dataset.addAttribute(Company.COUNTRY);
dataset.addAttribute(Company.CEONAMES);
dataset.addAttribute(Company.WEBSITE);
//dataset.addAttribute(Company.DATE);

}

@Override
public Company createModelFromElement(Node node, String provenanceInfo) {
	String id = getValueFromChildElement(node, "id").toLowerCase();

	// create the object with id and provenance information
	Company company = new Company(id, provenanceInfo);
	
	// fill the attributes
	company.setName(getValueFromChildElement(node, "name"));
	
	try {
		if(getValueFromChildElement(node, "revenue") != null && !getValueFromChildElement(node, "revenue").isEmpty()) {
			company.setRevenue((long)Double.parseDouble(getValueFromChildElement(node, "revenue")));
		} else {
			company.setNumberOfEmployees(null);
		}
	} catch (Exception e) {
		e.printStackTrace();	
	}
	
	
	try {
		if(getValueFromChildElement(node, "numberOfEmployees") != null) {
			company.setNumberOfEmployees(Long.parseLong(getValueFromChildElement(node, "numberOfEmployees")));
		} else {
			company.setNumberOfEmployees(null);
		}
	} catch (Exception e) {
		e.printStackTrace();	
	}
	
	company.setCity(getValueFromChildElement(node, "city"));
	company.setWebsite(getValueFromChildElement(node, "website"));
	
	try {
		if(getValueFromChildElement(node, "foundingYear") != null) {
			company.setFoundingYear(Integer.parseInt(getValueFromChildElement(node, "foundingYear")));
		} else {
			company.setFoundingYear(null);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	company.setCountry(getValueFromChildElement(node, "country"));
	
	// load the list of actors
	List<String> industries = getListFromChildElement(node, "industries");
	
	company.setIndustries(industries);
	
	// load list of ceo names
	List<String> ceoNames = getListFromChildElement(node, "ceoNames");
	company.setCeoNames(ceoNames);
	
	// convert the date string into a DateTime object
	//try {
	//	String date = getValueFromChildElement(node, "date");
	//	if (date != null && !date.isEmpty()) {
	/*
	 * DateTimeFormatter formatter = new DateTimeFormatterBuilder()
	 * .appendPattern("yyyy-MM-dd['T'HH:mm:ss.SSS]")
	 * .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
	 * .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
	 * .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
	 * .optionalStart().appendOffset("+HH:MM", "+00:00").optionalEnd()
	 * .toFormatter(Locale.ENGLISH); LocalDateTime dt = LocalDateTime.parse(date,
	 * formatter); company.setDate(dt); } } catch (Exception e) {
	 * e.printStackTrace(); }
	 */
	
	return company;
	}

	
	@Override
	public Company createInstanceForFusion(RecordGroup<Company, Attribute> cluster) {
	
	List<String> ids = new LinkedList<>();
	
	for (Company c : cluster.getRecords()) {
		ids.add(c.getIdentifier());
	}
	
	Collections.sort(ids);
	
	String mergedId = StringUtils.join(ids, '+');
	
	return new Company(mergedId, "fused");
	}

}