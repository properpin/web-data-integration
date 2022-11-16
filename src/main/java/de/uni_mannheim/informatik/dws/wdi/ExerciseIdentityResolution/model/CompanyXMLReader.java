package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.util.List;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

/**
 * A {@link XMLMatchableReader} for {@link Movie}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class CompanyXMLReader extends XMLMatchableReader<Company, Attribute>  {

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.io.XMLMatchableReader#initialiseDataset(de.uni_mannheim.informatik.wdi.model.DataSet)
	 */
	@Override
	protected void initialiseDataset(DataSet<Company, Attribute> dataset) {
		super.initialiseDataset(dataset);
		
	}
	
	@Override
	public Company createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id").toLowerCase();

		// create the object with id and provenance information
		Company company = new Company(id, provenanceInfo);

		// fill the attributes
		company.setName(getValueFromChildElement(node, "name"));
		
		try {
			if(getValueFromChildElement(node, "revenue").isEmpty()) {
				company.setRevenue((long) -99.00);
			} else {
				company.setRevenue((long)Double.parseDouble(getValueFromChildElement(node, "revenue")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(getValueFromChildElement(node, "foundingYear") != null) {
				company.setFoundingYear(Integer.parseInt(getValueFromChildElement(node, "foundingYear")));
			} else {
				company.setFoundingYear(-99);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(getValueFromChildElement(node, "city") != null) {
				company.setCity(getValueFromChildElement(node, "city"));
			} else {
				company.setFoundingYear(-99);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(getValueFromChildElement(node, "country") != null) {
				company.setCountry(getValueFromChildElement(node, "country"));
			} else {
				company.setFoundingYear(-99);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(getValueFromChildElement(node, "numberOfEmployees") != null) {
				company.setNumberOfEmployees(Long.parseLong(getValueFromChildElement(node, "numberOfEmployees")));
			} else {
				company.setNumberOfEmployees(-99);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(getValueFromChildElement(node, "website") != null) {
				company.setWebsite(getValueFromChildElement(node, "website"));
			} else {
				company.setWebsite("-99");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// load the list of industries
		List<String> industries = getListFromChildElement(node, "industries");
		
		company.setIndustries(industries);
		
		
		// load the list of ceo names
		List<String> ceoNames = getListFromChildElement(node, "ceoNames");
		company.setCeoNames(ceoNames);
		// System.out.println(dbpedia.getCeoNames());
		
		return company;
	}
	}
