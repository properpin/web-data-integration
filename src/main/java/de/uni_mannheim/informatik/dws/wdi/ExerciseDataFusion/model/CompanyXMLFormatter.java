package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Movie}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class CompanyXMLFormatter extends XMLFormatter<Company> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("companies");
	}

	@Override
	public Element createElementFromRecord(Company record, Document doc) {
		Element company = doc.createElement("company");

		company.appendChild(createTextElement("id", record.getIdentifier(), doc));

		company.appendChild(createTextElementWithProvenance("name",
				record.getName(),
				record.getMergedAttributeProvenance(Company.NAME), doc));
		company.appendChild(createTextElementWithProvenance("revenue",
				Long.toString(record.getRevenue()),
				record.getMergedAttributeProvenance(Company.REVENUE), doc));
		
		company.appendChild(createTextElementWithProvenance("numberOfEmployees",
				Long.toString(record.getNumberOfEmployees()),
				record.getMergedAttributeProvenance(Company.NUMBEROFEMPLOYEES), doc));
		
		//if (record.getNumberOfEmployees() != null) {
		//company.appendChild(createTextElementWithProvenance("numberOfEmployees",
			//	Long.toString(record.getNumberOfEmployees()),
				//record.getMergedAttributeProvenance(Company.NUMBEROFEMPLOYEES), doc));
		//} else {
			//company.appendChild(createTextElementWithProvenance("numberOfEmployees", "null", record.getMergedAttributeProvenance(Company.NUMBEROFEMPLOYEES), doc));
		//}
		
		//industries
		company.appendChild(createIndustriesElement(record, doc));
		
		company.appendChild(createTextElementWithProvenance("city",
				record.getCity(),
				record.getMergedAttributeProvenance(Company.CITY), doc));
		
		//company.appendChild(createTextElementWithProvenance("foundingYear",
			//	Integer.toString(record.getFoundingYear()),
				//record.getMergedAttributeProvenance(Company.FOUNDINGYEAR), doc));

			if (record.getFoundingYear() != null) {
				company.appendChild(createTextElementWithProvenance("foundingYear",
						Integer.toString(record.getFoundingYear()),
						record.getMergedAttributeProvenance(Company.FOUNDINGYEAR), doc));
			} else {
				company.appendChild(createTextElementWithProvenance("foundingYear", "", record.getMergedAttributeProvenance(Company.FOUNDINGYEAR), doc));
			}
		
		company.appendChild(createTextElementWithProvenance("country",
				record.getCountry(),
				record.getMergedAttributeProvenance(Company.COUNTRY), doc));
		
		company.appendChild(createCEONamesElement(record, doc));
		
		company.appendChild(createTextElementWithProvenance("website",
				record.getWebsite(),
				record.getMergedAttributeProvenance(Company.WEBSITE), doc));
		
	

		return company;
	}


	protected Element createTextElementWithProvenance(String name,
			String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}
	

	protected Element createIndustriesElement(Company record, Document doc) {
		Element industriesRoot = doc.createElement("industries");
		
		industriesRoot.setAttribute("provenance",
				record.getMergedAttributeProvenance(Company.INDUSTRIES));

		for (String a : record.getIndustries()) {
			if (!a.isEmpty()) {
				industriesRoot.appendChild(createTextElement("industry", a, doc));
			}
			
		}

		return industriesRoot;
	}
	
	protected Element createCEONamesElement(Company record, Document doc) {
		Element ceosRoot = doc.createElement("ceoNames");
		
		ceosRoot.setAttribute("provenance",
				record.getMergedAttributeProvenance(Company.CEONAMES));

		for (String a : record.getCeoNames()) {
			if (!a.isEmpty()) {
				ceosRoot.appendChild(createTextElement("ceoName", a, doc));
			}
			
		}

		return ceosRoot;
	}
}