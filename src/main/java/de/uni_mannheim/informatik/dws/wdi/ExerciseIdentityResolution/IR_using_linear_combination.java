package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import org.apache.xpath.functions.FuncString;
import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.CompanyBlockingKeyByNameGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.CompanyBlockingKeyByNumberOfEmployeesGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.CompanyBlockingKeyByRevenueGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.CompanyBlockingKeyByYearGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.MovieBlockingKeyByTitleGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.MovieBlockingKeyByYearGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieDateComparator2Years;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ft_db.CeoNameComparatorJaroSimilarity;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieTitleComparatorJaccard
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ft_db.CompanyNameComparatorLevenshteinSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ft_db.CompanyNameComparatorTokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ft_db.IndustryComparatorLevenshteinSimilarity;
// import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ft_db.CompanyNameComparatorLevenshteinSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ft_db.NumberOfEmployeesComparatorAbsoluteDifferenceSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ft_db.NumberOfEmployeesComparatorPercentageSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ft_db.RevenueComparatorPercentageSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Company;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.CompanyXMLReader;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.DBpedia;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.DBpediaXMLReader;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Ft;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.FtXMLReader;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Movie;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.MovieXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.MaximumBipartiteMatchingAlgorithm;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.Blocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class IR_using_linear_combination 
{
	/*
	 * Logging Options:
	 * 		default: 	level INFO	- console
	 * 		trace:		level TRACE     - console
	 * 		infoFile:	level INFO	- console/file
	 * 		traceFile:	level TRACE	- console/file
	 *  
	 * To set the log level to trace and write the log to winter.log and console, 
	 * activate the "traceFile" logger as follows:
	 *     private static final Logger logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	// default log without each record
	// private static final Logger logger = WinterLogManager.activateLogger("default");
	// show log on console
	private static final Logger logger = WinterLogManager.activateLogger("traceFile");
    
	public static void main( String[] args ) throws Exception
    {
		// loading data
		logger.info("*\tLoading datasets\t*");
		//HashedDataSet<Movie, Attribute> dataAcademyAwards = new HashedDataSet<>();
		//new MovieXMLReader().loadFromXML(new File("data/input/academy_awards.xml"), "/movies/movie", dataAcademyAwards);
		//HashedDataSet<Movie, Attribute> dataActors = new HashedDataSet<>();
		//new MovieXMLReader().loadFromXML(new File("data/input/actors.xml"), "/movies/movie", dataActors);
		
		HashedDataSet<Company, Attribute> dataFt = new HashedDataSet<>();
		new CompanyXMLReader().loadFromXML(new File("mapping/ft/mapforce/FT_ASS_02.xml"), "/companies/company", dataFt);
		
		HashedDataSet<Company, Attribute> dataDBpedia = new HashedDataSet<>();
		new CompanyXMLReader().loadFromXML(new File("mapping/dbpedia/mapforce/dbpedia_OUTPUT_26102022_V2.xml"), "/companies/company", dataDBpedia);

		// load the gold standard (test set)
		logger.info("*\tLoading gold standard\t*");
		// MatchingGoldStandard gsTest = new MatchingGoldStandard();
		// gsTest.loadFromCSVFile(new File(
		//		"data/goldstandard/gs_academy_awards_2_actors_test.csv"));
		
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File(
				"data/goldstandard/gs_ft_db_lower.csv"));

		// create a matching rule
		//LinearCombinationMatchingRule<Movie, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
		//		0.7);
		LinearCombinationMatchingRule<Company, Attribute> matchingRule = new LinearCombinationMatchingRule<>(0.7);
		
		
		// create debug report in csv
		// matchingRule.activateDebugReport("data/output/debugResultsMatchingRule_test.csv", 1000, gsTest);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRule_ft_db_test.csv", 1000, gsTest);
		
		// add comparators
		// matchingRule.addComparator(new MovieDateComparator2Years(), 0.3);
		//matchingRule.addComparator(new MovieTitleComparatorJaccard(), 0.7);
		

		matchingRule.addComparator(new NumberOfEmployeesComparatorPercentageSimilarity(), 0.2);
		//matchingRule.addComparator(new CompanyNameComparatorTokenizingJaccardSimilarity(), 0.3);
		matchingRule.addComparator(new CompanyNameComparatorLevenshteinSimilarity(), 0.8);
		// matchingRule.addComparator(new RevenueComparatorPercentageSimilarity(), 0.2);
		
		//matchingRule.addComparator(new CeoNameComparatorJaroSimilarity(), 0.2);
		// matchingRule.addComparator(new IndustryComparatorLevenshteinSimilarity(), 0.2);
//		matchingRule.addComparator(new NumberOfEmployeesComparatorAbsoluteDifferenceSimilarity(), 0.2);
		// matchingRule.addComparator(new CompanyNameComparatorLevenshteinSimilarity(), 0.8);
		

		// create a blocker (blocking strategy)
		//StandardRecordBlocker<Company, Attribute> blocker = new StandardRecordBlocker<Company, Attribute>(new CompanyBlockingKeyByNameGenerator());
		//StandardRecordBlocker<Company, Attribute> blocker = new StandardRecordBlocker<Company, Attribute>(new CompanyBlockingKeyByYearGenerator());
		//StandardRecordBlocker<Company, Attribute> blocker = new StandardRecordBlocker<Company, Attribute>(new CompanyBlockingKeyByRevenueGenerator());
		//SortedNeighbourhoodBlocker<Company,Company, Attribute> blocker = new SortedNeighbourhoodBlocker<Company, Company, Attribute>(new CompanyBlockingKeyByNumberOfEmployeesGenerator(), 5);
		NoBlocker<Company, Attribute> blocker = new NoBlocker<>();

		// blocker.setMeasureBlockSizes(true);
		
		// Blocker<Movie, Attribute> blocker2 = new StandardBlocker<DBpedia, Attribute>((m) -> Integer.toString(m.getDate().getYear() / 10));
		
		//Write debug results to file:
		blocker.collectBlockSizeData("data/output/debugResultsBlocking_ft_db.csv", 100);
		
		
		// Initialize Matching Engine
		MatchingEngine<Company, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		logger.info("*\tRunning identity resolution\t*");
		
		//Processable<Correspondence<Movie, Attribute>> correspondences = engine.runIdentityResolution(
		//		dataAcademyAwards, dataActors, null, matchingRule,
		//		blocker);
		
		Processable<Correspondence<Company, Attribute>> correspondences = engine.runIdentityResolution(
				dataFt, dataDBpedia, null, matchingRule,
				blocker);
		
		
		// Create a top-1 global matching - ONLY IF SURE THAT THERE ARE NO DUPLICATES
		 // correspondences = engine.getTopKInstanceCorrespondences(correspondences, 1, 0.9);

//		 Alternative: Create a maximum-weight, bipartite matching
		//  MaximumBipartiteMatchingAlgorithm<DBpedia,Attribute> maxWeight = new MaximumBipartiteMatchingAlgorithm<>(correspondences);
		//  maxWeight.run();
		//  correspondences = maxWeight.getResult();

		// write the correspondences to the output file
		// new CSVCorrespondenceFormatter().writeCSV(new File("data/output/academy_awards_2_actors_correspondences.csv"), correspondences);		
		 new CSVCorrespondenceFormatter().writeCSV(new File("data/output/ft_2_dbpedia_correspondences.csv"), correspondences);		
		logger.info("*\tEvaluating result\t*");
		// evaluate your result
		MatchingEvaluator<Company, Attribute> evaluator = new MatchingEvaluator<Company, Attribute>();

		Performance perfTest = evaluator.evaluateMatching(correspondences.get(),
				gsTest);

		// print the evaluation result
		logger.info("DBpedia <-> FT");
		logger.info(String.format(
				"Precision: %.10f",perfTest.getPrecision()));
		logger.info(String.format(
				"Recall: %.10f",	perfTest.getRecall()));
		logger.info(String.format(
				"F1: %.10f",perfTest.getF1()));
    }
}
