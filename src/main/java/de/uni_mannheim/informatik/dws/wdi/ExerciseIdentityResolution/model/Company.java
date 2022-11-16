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
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

/**
 * A {@link AbstractRecord} representing a movie.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class Company implements Matchable {

	/*
	 example entry 
     <company>
		<id>dbpedia_5902</id>
		<name>Hewlett Packard Enterprise</name>
		<industries>
			<industry>
				<name>Artificial intelligence</name>
				<name>Cloud computing</name>
				<name>Computer hardware</name>
				<name>Computer network</name>
				<name>Financial technology</name>
				<name>Internet of things</name>
				<name>Software</name>
			</industry>
		</industries>
		<revenue>27000000000</revenue>
		<foundingYear>2015</foundingYear>
		<city>Spring (Teksas)</city>
		<numberOfEmployees>59400</numberOfEmployees>
		<ceoNames>
			<ceoName>
				<name>Antonio Neri (Manager)</name>
				<name>Antonio Neri (businessman)</name>
				<name>Patricia Russo</name>
			</ceoName>
		</ceoNames>
	</company>
	 */

	protected String id;
	protected String provenance;
    private String name;
    private List<String> industries;
	private Long revenue;
	private Integer foundingYear;
    private String city;
    private String country;
	private Long numberOfEmployees;
	private List<String> ceoNames;
	private String website;


	public Company(String identifier, String provenance) {
		id = identifier;
		this.provenance = provenance;
	}
	

	@Override
	public String getIdentifier() {
		return id;
	}

	@Override
	public String getProvenance() {
		return provenance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getIndustries() {
		return industries;
	}

	public void setIndustries(List<String> industries) {
		this.industries = industries;
	}
	
	public Long getRevenue() {
		return revenue;
	}

	public void setRevenue(Long revenue) {
		this.revenue = revenue;
	}

	public Integer getFoundingYear() {
		return foundingYear;
	}

	public void setFoundingYear(Integer foundingYear) {
		this.foundingYear = foundingYear;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(long numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

    public List<String> getCeoNames() {
		return ceoNames;
	}

	public void setCeoNames(List<String> ceoNames) {
		this.ceoNames = ceoNames;
	}

    public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public String toString() {
		return String.format("[Company %s: %s / %s / %s]", getIdentifier(), getName(),
				getCity(), getFoundingYear().toString());
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DBpedia){
			return this.getIdentifier().equals(((DBpedia) obj).getIdentifier());
		}else
			return false;
	}
	
	
	
}