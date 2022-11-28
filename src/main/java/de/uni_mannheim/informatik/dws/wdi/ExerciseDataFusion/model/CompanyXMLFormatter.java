package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
		company.appendChild(createTextElementWithProvenance("city",
				record.getCity(),
				record.getMergedAttributeProvenance(Company.CITY), doc));

			if (record.getFoundingYear() != null) {
				company.appendChild(createTextElementWithProvenance("foundingYear",
						Integer.toString(record.getFoundingYear()),
						record.getMergedAttributeProvenance(Company.FOUNDINGYEAR), doc));
			} else {
				company.appendChild(createTextElementWithProvenance("foundingYear", "null", record.getMergedAttributeProvenance(Company.FOUNDINGYEAR), doc));
			}

		

		company.appendChild(createTextElementWithProvenance("country",
				record.getCountry(),
				record.getMergedAttributeProvenance(Company.COUNTRY), doc));
		//company.appendChild(createTextElementWithProvenance("ceoNames",
		//		record.getCeoNames(),
		//		record.getMergedAttributeProvenance(Company.CEONAMES), doc));
		company.appendChild(createTextElementWithProvenance("date", record
			.getDate().toString(), record
				.getMergedAttributeProvenance(Company.DATE), doc));

		return company;
	}

	protected Element createTextElementWithProvenance(String name,
			String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

}