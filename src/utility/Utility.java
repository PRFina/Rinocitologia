package utility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import rinocitologia.Anamnesi;
import rinocitologia.Cell;
import rinocitologia.Patient;


/**
 * Provides some utilities such as transformation of data stored in Patient in XML file, JSON and PDF.
 * <br>
 * Refer to https://stackoverflow.com/questions/5971964/file-separator-or-file-pathseparator for File.separator.
 */
public class Utility {
	
	private Patient dict;
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private String PATH = String.format("%s", System.getProperty("user.home"));
	
	/**
	 * Instantiates dict by a parameter and creates inputs and reports folders.
	 * @param dict
	 */
	public Utility(Patient dict) {
		this.dict = dict;
		createDirs();
				
	}


	/**
	 * Utility to write Pdf and Json reports.
	 */
	public void writeReports() {
		writeJson();
		try {
			writePdfReport();
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates inputs and reports folders.
	 * If name is missing, default name is Pinco Pallino.
	 */
	public void createDirs() {
		if(dict.getFirstName()==null && dict.getSurname()==null) {
			dict.setFirstName("Pinco");
			dict.setSurname("Pallino");
		}
		
		//String usrPATH = PATH + File.separator + "data" + File.separator + dict.getFirstName() + "_" + dict.getSurname();
		String usrPATH = dict.getPath();
		String inputsPATH =  usrPATH + File.separator + "inputs";
		final File folderReports = new File(usrPATH , "reports");
		if(!folderReports.exists() && !folderReports.mkdir()) {
			   //failed to create the folder, probably exit
			   throw new RuntimeException("Failed to create save directory.");
		}
		
		final File folderInputs = new File(usrPATH , "inputs");
		if(!folderInputs.exists() && !folderInputs.mkdir()) {
			   //failed to create the folder, probably exit
			   throw new RuntimeException("Failed to create save directory.");
		}


		//START CREATIONS OF INPUTS FOLDERS
		final File folderNeutrofili = new File(inputsPATH , "neutrofili");
		if(!folderNeutrofili.exists() && !folderNeutrofili.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		} else {
			//System.out.print("\nWriting neutrofili\n" + folderNeutrofili.getAbsolutePath());
			System.out.print("\nWriting neutrofili\n" + folderNeutrofili.getAbsolutePath());
		}
		final File folderEosinofili = new File(inputsPATH , "eosinofili");
		if(!folderEosinofili.exists() && !folderEosinofili.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}else {
			//System.out.print("\nWriting eosinofili\n" + folderEosinofili.getAbsolutePath());
		}
		final File folderMastcellule = new File(inputsPATH , "mastcellule");
		if(!folderMastcellule.exists() && !folderMastcellule.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}else {
			//System.out.print("\nWriting mastcellule\n" + folderMastcellule.getAbsolutePath());
		}
		final File folderMetaplastiche = new File(inputsPATH , "epiteliali");
		if(!folderMetaplastiche.exists() && !folderMetaplastiche.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}else {
			//System.out.print("\nWriting epiteliali\n" + folderMetaplastiche.getAbsolutePath());
		}
		final File folderLinfociti = new File(inputsPATH , "linfociti");
		if(!folderLinfociti.exists() && !folderLinfociti.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}else {
			//System.out.print("\nWriting linfociti\n" + folderLinfociti.getAbsolutePath());
		}
		/*
		final File folderCiliate = new File(inputsPATH , "ciliate");
		if(!folderCiliate.exists() && !folderCiliate.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}else {
			System.out.print("\nWriting ciliate\n" + folderCiliate.getAbsolutePath());
		}
		*/
		final File folderMucipare = new File(inputsPATH , "mucipare");
		if(!folderMucipare.exists() && !folderMucipare.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}else {
			//System.out.print("\nWriting mucipare\n" + folderMucipare.getAbsolutePath());
		}
		final File folderOthers = new File(inputsPATH , "others");
		if(!folderOthers.exists() && !folderOthers.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}else {
			//System.out.print("\nWriting others\n" + folderOthers.getAbsolutePath());
		}

		final File folderCellule = new File(inputsPATH , "cellule");
		if(!folderCellule.exists() && !folderCellule.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}else {
			//System.out.print("\nWriting mucipare\n" + folderMucipare.getAbsolutePath());
		}
		final File folderBiofilmsi = new File(inputsPATH , "biofilmsi");
		if(!folderBiofilmsi.exists() && !folderBiofilmsi.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}else {
			//System.out.print("\nWriting mucipare\n" + folderMucipare.getAbsolutePath());
		}
		final File folderBiofilmno = new File(inputsPATH , "biofilmno");
		if(!folderBiofilmno.exists() && !folderBiofilmno.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}else {
			//System.out.print("\nWriting mucipare\n" + folderMucipare.getAbsolutePath());
		}
	}
	
	
	/**
	 * Writes informations contained in Patient in a JSON File (report.json) located in patient.getPath()/reports.
	 * If the directory does not exist, creates it.
	 */
	public void writeJson() {
		String json = gson.toJson(dict);	
		String directoryPath = dict.getPath() + File.separator + "reports";
		String fullPath = directoryPath + File.separator + "report.json";
		 try {
			   FileWriter writer = new FileWriter(fullPath);
			   writer.write(json);
			   writer.close();
			  
		 	  } catch (IOException e) {
			   e.printStackTrace();
			  }
		 System.out.println("Writing JSON into file:\n" + fullPath + "\n----------------------------");
			  
	}

	/**
	 * Static method to read JSON file (report.json) by CF.
	 * @param cf
	 * @return
	 */
	public static Patient readJson(String cf){
		String PATH = System.getProperty("user.home") + File.separator + "data" + File.separator + cf + File.separator + "reports" + File.separator + "report.json";
		Patient patient = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try {

			System.out.println("Reading JSON from a file");
			System.out.println(PATH);
			System.out.println("----------------------------");

			BufferedReader br = new BufferedReader(
					new FileReader(PATH));

			//convert the json string back to object
			patient = gson.fromJson(br, Patient.class);


		} catch (IOException e) {
			e.printStackTrace();
		}

		return patient;
	}


	/**
	 * Static method to read JSON and populate Patient object.
	 * JSON red from path.
	 * @param path
	 * @return
	 */
	public static Patient readJson(File path){
		Patient patient = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try {

			System.out.println("Reading JSON from a file");
			System.out.println(path);
			System.out.println("----------------------------");

			BufferedReader br = new BufferedReader(
					new FileReader(path));

			//convert the json string back to object
			patient = gson.fromJson(br, Patient.class);


		} catch (IOException e) {
			e.printStackTrace();
		}

		return patient;
	}




	/**
	 * Writes last session in user.home/data/lastsession.json
	 *
	 */
	public void writeLastSession(){
		String json = gson.toJson(dict);
		String fullPath = dict.getPathData() + File.separator + "lastsession.json";
		try {
			FileWriter writer = new FileWriter(fullPath);
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Writing JSON into file:\n" + fullPath + "\n----------------------------");

	}
	
	/**
	 * Utility to write pdf report in patient.getPath/reports/report.pdf.
	 * Writes a report in Pdf format.
	 * Refer to http://www.baeldung.com/java-pdf-creation
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public void writePdfReport() throws FileNotFoundException, DocumentException {

		String path = dict.getPath() + File.separator + "reports" + File.separator + "report.pdf";
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(path));
		 
		document.open();
		Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk("Report clinico per il paziente: " + dict.getSurname() + " " + dict.getFirstName() + ".\n", fontHeader);
		
		
		Font fontInformativa = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
		Font fontParagraph =  FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK);

		Paragraph info;
		if(dict.getCf() != null) {
			info = new Paragraph("Nome: " + dict.getFirstName() + "\nCognome: " + dict.getSurname() + "\nNato il: " + dict.getCf().getDay() + "/" + dict.getCf().getMonth() + "/" + dict.getCf().getYear(), fontInformativa);
		} else {
			info = new Paragraph("Nome: " + dict.getFirstName() + "\nCognome: " + dict.getSurname() + "\n", fontInformativa);
		}

		Chunk chunkAnamnesiHeader = new Chunk("Anamnesi", fontParagraph);
		Paragraph anamnesi = null;
		if (dict.getLastAnamnesi() != null){
			anamnesi = new Paragraph(dict.getLastAnamnesi().toString(), fontInformativa);
		}

		Chunk chunkDiagnosiHeader = new Chunk("Diagnosi",fontParagraph);
		Paragraph diagnosi = null;
		if (dict.getDiagnosiUfficiale() != null && dict.getTerapia() != null){
			diagnosi = new Paragraph("Diagnosi: " + dict.getDiagnosiUfficiale() + "\nTerapia: " + dict.getTerapia(), fontInformativa);
		}

		Paragraph informativa = new Paragraph("Questo è un report generato automaticamente dal sistema di supporto medico per la Rinocitologia Nasale. Per maggiori informazioni rivolgersi a un dottore specializzato in Rinocitologia.", fontInformativa);
		informativa.setIndentationLeft(20);
		PdfPTable table = new PdfPTable(3); //3 is the number of columns for the table: Name, Cell Count and Grade
		table.setSpacingBefore(12);   
		table.setSpacingAfter(12);

		addTableHeader(table);
		for (Map.Entry<String, Cell> entry : dict.getDictionary().entrySet()) {
			addRows(table, entry.getKey(), Integer.toString(entry.getValue().getcellCount()), Integer.toString(entry.getValue().getgrade()));
		}
		 
	
		document.add(chunk);
		document.add( Chunk.NEWLINE );
		document.add(info);
		
		document.add(table);
		document.add( Chunk.NEWLINE );
		document.add(chunkAnamnesiHeader);
		document.add( Chunk.NEWLINE );
		if(anamnesi!=null) {
			document.add(anamnesi);
		}
		document.add( Chunk.NEXTPAGE );
		document.add(chunkDiagnosiHeader);
		document.add( Chunk.NEWLINE );

		if(diagnosi!=null) {
			document.add(diagnosi);
		}

		document.add( Chunk.NEWLINE );
		document.add(informativa);

		document.close();
		
		System.out.println("Writing PDF into file:\n" + path + "\n----------------------------");
	}


	/**
	 * Utility to write pdf report in patient.getPath/reports/report<Anamnesi date>.pdf
	 * @param anamnesi
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public void writePdfReport(Anamnesi anamnesi) throws FileNotFoundException, DocumentException {

		String path = dict.getPath() + File.separator + "reports" + File.separator + "report_anamnesi" + anamnesi.getTime().split(" ")[0].replace("/", "_") + ".pdf";
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(path));

		document.open();
		Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK);
		Font fontParagraph =  FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK);

		Chunk chunk = new Chunk("Report clinico per il paziente: " + dict.getSurname() + " " + dict.getFirstName() + ".\n", fontHeader);


		Font fontInformativa = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
		Paragraph info;
		if(dict.getCf() != null) {
			info = new Paragraph("Nome: " + dict.getFirstName() + "\nCognome: " + dict.getSurname() + "\nNato il: " + dict.getCf().getDay() + "/" + dict.getCf().getMonth() + "/" + dict.getCf().getYear(), fontInformativa);
		} else {
			info = new Paragraph("Nome: " + dict.getFirstName() + "\nCognome: " + dict.getSurname() + "\n", fontInformativa);
		}
		Paragraph informativa = new Paragraph("Questo è un report generato automaticamente dal sistema di supporto medico per la Rinocitologia Nasale. Per maggiori informazioni rivolgersi a un dottore specializzato in Rinocitologia.", fontInformativa);
		informativa.setIndentationLeft(20);
		PdfPTable table = new PdfPTable(3); //3 is the number of columns for the table: Name, Cell Count and Grade
		table.setSpacingBefore(12);
		table.setSpacingAfter(12);

		addTableHeader(table);
		for (Map.Entry<String, Cell> entry : dict.getDictionary().entrySet()) {
			addRows(table, entry.getKey(), Integer.toString(entry.getValue().getcellCount()), Integer.toString(entry.getValue().getgrade()));
		}

		Chunk chunkAnamnesiHeader = new Chunk("Anamnesi", fontParagraph);

		Paragraph anamnesiParagraph = new Paragraph(anamnesi.toString(), fontInformativa);


		document.add(chunk);
		document.add( Chunk.NEWLINE );
		document.add(info);

		document.add(table);
		document.add( Chunk.NEWLINE );
		document.add(chunkAnamnesiHeader);
		document.add(Chunk.NEWLINE);
		document.add(anamnesiParagraph);
		document.add( Chunk.NEWLINE );

		document.add(informativa);


		document.close();

		System.out.println("Writing PDF into file:\n" + path + "\n----------------------------");
	}


	private void addTableHeader(PdfPTable table) {
	    Stream.of("Nome", "Conta cellule", "Grado")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle));
	        table.addCell(header);
	    });
	}
	
	private void addRows(PdfPTable table, String name, String count, String grade) {
	    table.addCell(name);
	    table.addCell(count);
	    table.addCell(grade);
		
	}



	//************
	//*DEPRECATED*
	//************
	/**
	 * DEPRECATED???
	 *
	 * Reads informations contained in a JSON format file (retrieved by patient.getPath()) and populates an instance of Patient.
	 * @return dictionary instance of the class Patient populated with the informations obtained from JSON file.
	 */
	public static Patient readJson(Patient dict) {
		String directoryPath = dict.getPath() + File.separator +  "reports";
		String fullPath = directoryPath + File.separator + "report.json";
		Patient patient = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try {

			System.out.println("Reading JSON from a file");
			System.out.println(fullPath);
			System.out.println("----------------------------");

			BufferedReader br = new BufferedReader(
					new FileReader(fullPath));

			//convert the json string back to object
			patient = gson.fromJson(br, Patient.class);


		} catch (IOException e) {
			e.printStackTrace();
		}

		return patient;
	}


	/**
	 * DEPRECATED
	 * Reads informations contained in a JSON format file (retrieved by firstName and surname) and populates an instance of Patient.
	 * @param firstName String that contains user's first name.
	 * @param surname String that contains user's surname.
	 * @return dictionary instance of the class Patient populated with the informations obtained from JSON file.
	 */
	public Patient readJson(String firstName, String surname) {
		String directoryPath = dict.getPath() + File.separator + firstName + "_" + surname + File.separator + "reports";
		String fullPath = directoryPath + File.separator + firstName + "_" + surname + ".json";
		Patient patient = null;

		try {

			System.out.println("Reading JSON from a file");
			System.out.println(PATH);
			System.out.println(fullPath);
			System.out.println("----------------------------");

			BufferedReader br = new BufferedReader(
					new FileReader(fullPath));

			//convert the json string back to object
			patient = gson.fromJson(br, Patient.class);


		} catch (IOException e) {
			e.printStackTrace();
		}

		return patient;
	}
}
