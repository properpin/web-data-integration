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
public class DBpediaXMLReader extends XMLMatchableReader<DBpedia, Attribute>  {

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.io.XMLMatchableReader#initialiseDataset(de.uni_mannheim.informatik.wdi.model.DataSet)
	 */
	@Override
	protected void initialiseDataset(DataSet<DBpedia, Attribute> dataset) {
		super.initialiseDataset(dataset);
		
	}
	
	@Override
	public DBpedia createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		DBpedia dbpedia = new DBpedia(id, provenanceInfo);

		// fill the attributes
		dbpedia.setName(getValueFromChildElement(node, "name"));
		dbpedia.setRevenue((long)Double.parseDouble(getValueFromChildElement(node, "revenue")));
		
		try {
			if(getValueFromChildElement(node, "foundingYear") != null) {
				dbpedia.setFoundingYear(Integer.parseInt(getValueFromChildElement(node, "foundingYear")));
			} else {
				dbpedia.setFoundingYear(-99);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dbpedia.setCity(getValueFromChildElement(node, "city"));
		
		try {
			if(getValueFromChildElement(node, "numberOfEmployees") != null) {
				dbpedia.setNumberOfEmployees(Long.parseLong(getValueFromChildElement(node, "numberOfEmployees")));
			} else {
				dbpedia.setNumberOfEmployees(-99);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// load the list of industries
		List<String> industries = getListFromChildElement(node, "industries");
		
		dbpedia.setIndustries(industries);
		
		
		// load the list of ceo names
		List<String> ceoNames = getListFromChildElement(node, "ceoNames");
		dbpedia.setCeoNames(ceoNames);
		// System.out.println(dbpedia.getCeoNames());
		
		return dbpedia;
	}

}