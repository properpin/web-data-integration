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
public class Ft implements Matchable {

	/*
	 example entry 
     <company>
		<id>FT_ID_0</id>
		<name>Berkshire Hathaway</name>
		<industries>
			<industry>
				<name>Diversified Financials</name>
			</industry>
		</industries>
		<revenue>276094000000</revenue>
		<foundingYear>1939</foundingYear>
		<country>United States</country>
		<city>Omaha</city>
		<numberOfEmployees>372000</numberOfEmployees>
		<ceoNames>
			<ceoName>
				<name>Warren Edward Buffett</name>
			</ceoName>
		</ceoNames>
		<website>http://www.berkshirehathaway.com</website>
	</company>
	 */

	protected String id;
	protected String provenance;
    private String name;
    private List<String> industries;
	private Integer revenue;
	private Integer foundingYear;
    private String country;
    private String city;
    private Long numberOfEmployees;
	private List<String> ceoNames;
	private String website;


	public Ft(String identifier, String provenance) {
		id = identifier;
		this.provenance = provenance;
		industries = new LinkedList<>();
		ceoNames = new LinkedList<>();
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
	
	public Integer getRevenue() {
		return revenue;
	}

	public void setRevenue(Integer revenue) {
		this.revenue = revenue;
	}

	public Integer getFoundingYear() {
		return foundingYear;
	}

	public void setFoundingYear(Integer foundingYear) {
		this.foundingYear = foundingYear;
	}

    public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

    public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public String toString() {
		return String.format("[Company %s: %s / %s / %s]", getIdentifier(), getName(),
				getCountry(), getFoundingYear().toString());
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Ft){
			return this.getIdentifier().equals(((Ft) obj).getIdentifier());
		}else
			return false;
	}
	
	
	
}
