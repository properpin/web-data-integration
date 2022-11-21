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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * A {@link AbstractRecord} which represents an actor
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class Company extends AbstractRecord<Attribute> implements Serializable {

	/*
	 * example entry <actor> <name>Janet Gaynor</name>
	 * <birthday>1906-01-01</birthday> <birthplace>Pennsylvania</birthplace>
	 * </actor>
	 */

	private static final long serialVersionUID = 1L;
	private String name;
	private LocalDateTime date;
    private List<String> industries;
	private Long revenue;
	private Integer foundingYear;
    private String city;
    private String country;
	private Long numberOfEmployees;
	private List<String> ceoNames;
	private String website;

	public Company(String identifier, String provenance) {
		super(identifier, provenance);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 31 + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	private Map<Attribute, Collection<String>> provenance = new HashMap<>();
	private Collection<String> recordProvenance;

	public void setRecordProvenance(Collection<String> provenance) {
		recordProvenance = provenance;
	}

	public Collection<String> getRecordProvenance() {
		return recordProvenance;
	}

	public void setAttributeProvenance(Attribute attribute,
			Collection<String> provenance) {
		this.provenance.put(attribute, provenance);
	}

	public Collection<String> getAttributeProvenance(String attribute) {
		return provenance.get(attribute);
	}

	public String getMergedAttributeProvenance(Attribute attribute) {
		Collection<String> prov = provenance.get(attribute);

		if (prov != null) {
			return StringUtils.join(prov, "+");
		} else {
			return "";
		}
	}


	public static final Attribute NAME = new Attribute("Name");
	public static final Attribute REVENUE = new Attribute("Revenue");
	public static final Attribute NUMBEROFEMPLOYEES = new Attribute("NumberOfEmployees");
	public static final Attribute INDUSTRIES = new Attribute("Industries");
	public static final Attribute CITY = new Attribute("City");
	public static final Attribute FOUNDINGYEAR = new Attribute("FoundingYear");
	public static final Attribute COUNTRY = new Attribute("Country");
	public static final Attribute CEONAMES = new Attribute("CeoNames");
	public static final Attribute WEBSITE = new Attribute("Website");
	
	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.Record#hasValue(java.lang.Object)
	 */
	
	public boolean hasValue(Attribute attribute) {
		if(attribute==NAME) {
			return getName() != null && !getName().isEmpty();
		}
		else if(attribute==REVENUE) {
			return getRevenue() != null && !Double.isNaN(getRevenue());
		}
		else if(attribute==NUMBEROFEMPLOYEES) {
			return getNumberOfEmployees() != null;
		}
		else if(attribute==INDUSTRIES) {
			return getIndustries() != null && getIndustries().size() > 0;
			}
		else if(attribute==CITY) {
			return getCity() != null && !getCity().isEmpty();
			}
		else if(attribute==FOUNDINGYEAR) {
			return getFoundingYear() != null;
			}
		else if(attribute==COUNTRY) {
			return getCountry() != null && !getCountry().isEmpty();
			}
		else if(attribute==CEONAMES) {
			return getCeoNames() != null && getCeoNames().size() > 0;
			}
		else if(attribute==WEBSITE) {
			return getWebsite() != null && !getWebsite().isEmpty();
			}
		else {
			return false;
		}
			
	}
	
	@Override
	public String toString() {
		return String.format("[Actor: %s]", getName());
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(Long numberOfEmployees) {
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}