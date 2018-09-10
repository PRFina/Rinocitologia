package rinocitologia;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.FactAddressValue;
import net.sf.clipsrules.jni.MultifieldValue;

/**
 * Handles the inputs from neural network.
 * If some informations are missing, automatically fills them with default values.
 * Assert facts for the Expert System from the informations previously retrieved.
 */
public class InputHandler {
	private Environment clips;
	private Patient patient;
	private String PATH = System.getProperty("user.home");
	private ArrayList<ArrayList<String>> fattiAsseriti = new ArrayList<>();
	
	/**
	 * @param clips An instance of the clips engine.
	 * @param patient An instance of the object containing all the information useful to fill generate facts.
	 */
	public InputHandler(Environment clips, Patient patient) {
		this.clips = clips;
		this.patient = patient;
	}
	
	/**
	 * Assert facts from informations retrieved by neural network.
	 * Those informations are stored in a class which contains an HashMap having the following structure:
	 * <br>
	 * <String, Cellule> => <Cell Name, Cell Informations>
	 * <br>
	 * Cell Informations contains the count of the cells (done by neural network) and the grade transposition.
	 * For more informations, please refer to "Atlante di Citologia Nasale" by Dr. Gelardi
	 */
	public void assertFacts() {
		String assertion;
		patient.completeDictionary();

		for (Map.Entry<String, Cell> entry : patient.getDictionary().entrySet()) {
			assertion = "(cellula (nome " + entry.getKey() + ") (grado " + entry.getValue().getgrade() + "))";
			//System.out.println(assertion);
			clips.assertString(assertion);
		}
		Anamnesi anamnesi = patient.getLastAnamnesi();
		System.out.println("INPUTHANDLER ------ "+ anamnesi.toString());

		int i = 0;
		if ((anamnesi.getAllergiaGen() != null) && (anamnesi.getAllergiaGen() == "si")) {
			clips.assertString("(famiglia(soggetto genitore) (disturbo allergia) (tipo " + anamnesi.getTipoAllergiaGen() + "))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Genitore allergico");
			fattiAsseriti.get(i).add("(famiglia(soggetto genitore) (disturbo allergia))");
			i++;
		}
		if ((anamnesi.getAllergiaFra() != null) && (anamnesi.getAllergiaFra() == "si")) {
			clips.assertString("(famiglia(soggetto fratello) (disturbo allergia) (tipo " + anamnesi.getTipoAllergiaFra() + "))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Fratello allergico");
			fattiAsseriti.get(i).add("(famiglia(soggetto fratello) (disturbo allergia))");
			i++;
		}
		if ((anamnesi.getPoliposiGen() != null) && (anamnesi.getPoliposiGen() == "si")) {
			clips.assertString("(famiglia(soggetto genitore) (disturbo poliposi))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Poliposi presente nel genitore");
			fattiAsseriti.get(i).add("(famiglia(soggetto genitore) (disturbo poliposi))");
			i++;
		}
		if ((anamnesi.getPoliposiFra() != null) && (anamnesi.getPoliposiFra() == "si")){
			clips.assertString("(famiglia(soggetto fratello) (disturbo poliposi))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Poliposi presente nel fratello");
			fattiAsseriti.get(i).add("(famiglia(soggetto fratello) (disturbo poliposi))");
			i++;
		}
		if((anamnesi.getAsmaGen() != null)&&(anamnesi.getAsmaGen() == "si")) {
			clips.assertString("(famiglia(soggetto genitore) (disturbo asma))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Genitore asmatico");
			fattiAsseriti.get(i).add("(famiglia(soggetto genitore) (disturbo asma))");
			i++;
		}
		if((anamnesi.getAsmaFra() != null)&&(anamnesi.getAsmaFra() == "si")) {
			clips.assertString("(famiglia(soggetto fratello) (disturbo asma))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Fratello asmatico");
			fattiAsseriti.get(i).add("(famiglia(soggetto fratello) (disturbo asma))");
			i++;
		}
		if((anamnesi.getOstruzione()!=null)&&(anamnesi.getOstruzione()!= "nessuna")) {
			clips.assertString("(sintomo (nome \"Ostruzione nasale " + anamnesi.getOstruzione() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Ostruzione nasale "+ anamnesi.getOstruzione());
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Ostruzione nasale " + anamnesi.getOstruzione() + "\\\"))");
			i++;
		}
		if((anamnesi.getRinorrea()!=null)&&(anamnesi.getRinorrea()!= "nessuna")){
			clips.assertString("(sintomo (nome \"Rinorrea "+anamnesi.getRinorrea()+"\"))");
			clips.assertString("(sintomo (nome \"Espansione rinorrea: "+anamnesi.getEspansioneRinorrea()+"\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Rinorrea "+anamnesi.getRinorrea());
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Rinorrea "+anamnesi.getRinorrea()+"\\\"))");
			i++;
		}
		if((anamnesi.getPruritoNasale()!= null)&&(anamnesi.getPruritoNasale()== "si")) {
			clips.assertString("(sintomo (nome \"Prurito nasale\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Prurito nasale");
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Prurito nasale\\\"))");
			i++;
		}
		if((anamnesi.getStarnuto()!=null)&&(anamnesi.getStarnuto()!= "nessuna")) {
			clips.assertString("(sintomo (nome \"Starnutazione " + anamnesi.getStarnuto() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Starnutazione " + anamnesi.getStarnuto());
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Starnutazione " + anamnesi.getStarnuto() + "\\\"))");
			i++;
		}
		if((anamnesi.getOlfatto()!=null)&&(anamnesi.getOlfatto()!= "nessuno")) {
			clips.assertString("(sintomo (nome \"Problemi olfattivi dovuti a " + anamnesi.getOlfatto() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Problemi olfattivi dovuti a " + anamnesi.getOlfatto());
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Problemi olfattivi dovuti a " + anamnesi.getOlfatto() + "\\\"))");
			i++;
		}
		if((anamnesi.getOvattamento()!=null)&&(anamnesi.getOvattamento()!= "nessuno")) {
			clips.assertString("(sintomo (nome \"Ovattamento " + anamnesi.getOvattamento() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Ovattamento " + anamnesi.getOvattamento());
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Ovattamento " + anamnesi.getOvattamento() + "\\\"))");
			i++;
		}
		if((anamnesi.getIpoacusia()!=null)&&(anamnesi.getIpoacusia()!= "nessuno")) {
			clips.assertString("(sintomo (nome \"Ipoacusi " + anamnesi.getIpoacusia() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Ipoacusi " + anamnesi.getIpoacusia());
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Ipoacusi " + anamnesi.getIpoacusia() + "\\\"))");
			i++;
		}
		if((anamnesi.getAcufeni()!=null)&&(anamnesi.getAcufeni()!= "nessuno")) {
			clips.assertString("(sintomo (nome \"Acufeni " + anamnesi.getAcufeni() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Acufeni " + anamnesi.getAcufeni());
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Acufeni " + anamnesi.getAcufeni() + "\\\"))");
			i++;
		}
		if((anamnesi.getVertigini()!="")&&(anamnesi.getVertigini()!= "nessuna")&&(anamnesi.getVertigini()!=null)) {
			clips.assertString("(sintomo (nome \"Sindrome vertiginosa " + anamnesi.getVertigini() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Sindrome vertiginosa " + anamnesi.getVertigini());
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Sindrome vertiginosa " + anamnesi.getVertigini() + "\\\"))");
			i++;
		}
		if(anamnesi.getLacrimazione()) {
			clips.assertString("(sintomo (nome \"Lacrimazione\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Lacrimazione");
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Lacrimazione\\\"))");
			i++;
		}
		if(anamnesi.getFotofobia()) {
			clips.assertString("(sintomo (nome \"Fotofobia\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Fotofobia");
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Fotofobia\\\"))");
			i++;
		}
		if(anamnesi.getPrurito()) {
			clips.assertString("(sintomo (nome \"Prurito occhio\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Prurito occhio");
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Prurito occhio\\\"))");
			i++;
		}
		if(anamnesi.getBruciore()) {
			clips.assertString("(sintomo (nome \"Bruciore\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Bruciore");
			fattiAsseriti.get(i).add("(sintomo (nome \\\"Bruciore\\\"))");
			i++;
		}

		if((anamnesi.getPirNasale()!=null)&&(anamnesi.getPirNasale()!="normofornata")) {
			clips.assertString("(scoperta (parte-anatomica piramide-nasale) (caratteristica \"" + anamnesi.getPirNasale() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Piramide nasale nasale "+ anamnesi.getPirNasale());
			fattiAsseriti.get(i).add("(scoperta (parte-anatomica piramide-nasale) (caratteristica \\\"" + anamnesi.getPirNasale() + "\\\"))");
			i++;
		}
		if((anamnesi.getValNasale()!=null)&&(anamnesi.getValNasale()!="normofunzionante")) {
			clips.assertString("(scoperta (parte-anatomica valvola-nasale) (caratteristica \"" + anamnesi.getValNasale() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("valvola nasale nasale "+ anamnesi.getValNasale());
			fattiAsseriti.get(i).add("(scoperta (parte-anatomica valvola-nasale) (caratteristica \\\"" + anamnesi.getValNasale() + "\\\"))");
			i++;
		}
		if((anamnesi.getSetto()!=null)&&(anamnesi.getSetto()!="in asse")) {
			clips.assertString("(scoperta (parte-anatomica setto-nasale) (caratteristica \"" + anamnesi.getSetto() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("setto-nasale "+ anamnesi.getSetto());
			fattiAsseriti.get(i).add("(scoperta (parte-anatomica setto-nasale) (caratteristica \\\"" + anamnesi.getSetto() + "\\\"))");
			i++;
		}
		if((anamnesi.getTurbinati()!=null)&&(anamnesi.getTurbinati()!="normofornata")) {
			clips.assertString("(scoperta (parte-anatomica turbinati) (caratteristica \"" + anamnesi.getTurbinati() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Turbinati "+ anamnesi.getTurbinati());
			fattiAsseriti.get(i).add("(scoperta (parte-anatomica turbinati) (caratteristica \\\"" + anamnesi.getTurbinati() + "\\\"))");
			i++;
		}
		if((anamnesi.getPolSx()!=null)&&(anamnesi.getPolSx()!="assente")) {
			clips.assertString("(scoperta (parte-anatomica poliposi-sinistra) (caratteristica \"" + anamnesi.getPolSx() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Poliposi sinistra "+ anamnesi.getPolSx());
			fattiAsseriti.get(i).add("(scoperta (parte-anatomica poliposi-sinistra) (caratteristica \\\"" + anamnesi.getPolSx() + "\\\"))");
			i++;
		}
		if((anamnesi.getPolDx()!=null)&&(anamnesi.getPolDx()!="assente")) {
			clips.assertString("(scoperta (parte-anatomica poliposi-destra) (caratteristica \"" + anamnesi.getPolDx() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Poliposi destra "+ anamnesi.getPolDx());
			fattiAsseriti.get(i).add("(scoperta (parte-anatomica poliposi-destra) (caratteristica \\\"" + anamnesi.getPolDx() + "\\\"))");
			i++;
		}
		if((anamnesi.getEssudato()!=null)&&(anamnesi.getEssudato()!="assente")) {
			clips.assertString("(scoperta (parte-anatomica essudato) (caratteristica \"" + anamnesi.getEssudato() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Essudato "+ anamnesi.getEssudato());
			fattiAsseriti.get(i).add("(scoperta (parte-anatomica essudato) (caratteristica \\\"" + anamnesi.getEssudato() + "\\\"))");
			i++;
		}
		if((anamnesi.getIpertrofia()!="")&&(anamnesi.getIpertrofia()!="normofornata")&&(anamnesi.getIpertrofia()!=null)) {
			clips.assertString("(scoperta (parte-anatomica ipertrofia) (caratteristica \"" + anamnesi.getIpertrofia() + "\"))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Ipertrofia "+ anamnesi.getIpertrofia());
			fattiAsseriti.get(i).add("(scoperta (parte-anatomica ipertrofia) (caratteristica \\\"" + anamnesi.getIpertrofia() + "\\\"))");
			i++;
		}
		//Manca la gestione dell'esame rinomanometrico e dell'esame otoscopico
		if(anamnesi.getAllergia()!= "Non presenti."){
			if(anamnesi.getAllergia()!=""){
				fattiAsseriti.add(new ArrayList<String>());
				fattiAsseriti.get(i).add("Prick-test positivo");
				fattiAsseriti.get(i).add("(prick-test(esito positivo))");
				i++;
				AllergieManager allergieManager = new AllergieManager();
				for(String allergia : anamnesi.getAllergie()){
					if(allergia.equals("allergene perenne"))
						clips.assertString("(prick-test(esito positivo) (allergene perenne))");
					else{
						System.out.println("ALLERGENE: "+allergia+" PERIODO: "+allergieManager.evaluateMonthString(allergia, anamnesi.getMese()));
						clips.assertString("(prick-test(esito positivo) (periodo "+allergieManager.evaluateMonthString(allergia, anamnesi.getMese())+") (allergene "+allergia+"))");
					}
				}
			}
		}else {
			clips.assertString("(prick-test(esito negativo))");
			fattiAsseriti.add(new ArrayList<String>());
			fattiAsseriti.get(i).add("Prick-test negativo");
			fattiAsseriti.get(i).add("(prick-test(esito negativo))");
			i++;
		}
		System.out.println("INPUT HANDLER 2 ---------- "+fattiAsseriti.toString());
		this.readFileDiagnosi();
	}

	/**
	 * Read and parse a file (locating it by patient's name) and populates an instance of patient.
	 * @return patient An instance of Patient containing informations retrieved from file and subsequently filled with default values for missing entries.
	 */
	public void readFileDiagnosi() {
		String directoryPath = PATH + File.separator + "data";
		String fullPath = directoryPath + File.separator + "DiagnosiAggiuntive.txt";
		try {
			if(new File(fullPath).exists()){
				File file = new File(fullPath);
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				StringBuffer stringBuffer = new StringBuffer();
				String line;

				while ((line = bufferedReader.readLine()) != null) {
					System.out.println("LINE ------ "+line);
					clips.eval(line);
				}
				fileReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeFileDiagnosi(String parteSinistra, String informazioni) {
		String directoryPath = PATH + File.separator + "data";
		String fullPath = directoryPath + File.separator + "DiagnosiAggiuntive.txt";
		try {
			FileWriter fStream = new FileWriter(fullPath, true);
			String nameRule = patient.getDiagnosiUfficiale();
			//Il nome della regola sarebbe "dataUltimaAnamnesi orarioUltimaAnamnesi", ma il nome di una regola
			//non può contenere spazi. Di conseguenza replaceAll eliminerà i whitespace e i caratteri non visibili
			nameRule = nameRule.replaceAll("\\p{Z}", "-");
			fStream.append("(build \"(defrule "+nameRule+parteSinistra+" => (assert(diagnosi(nome \\\""+patient.getDiagnosiUfficiale()+"\\\") (informazioni"+informazioni+"))))\")");
			fStream.append("\n");
			fStream.flush();
			fStream.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public ArrayList<ArrayList<String>> getFattiAsseriti() { return fattiAsseriti; }


	/**
	 * Read and parse a file (locating it by patient's name) and populates an instance of patient.
	 * @return patient An instance of Patient containing informations retrieved from file and subsequently filled with default values for missing entries.
	 */
	public Patient readFile() {
		
		if(patient.getFirstName()==null && patient.getSurname()==null) {
			patient.setFirstName("Pinco");
			patient.setSurname("Pallino");
		}
		
		String directoryPath = PATH + File.separator + "data" + File.separator + patient.getFirstName() + "_" + patient.getSurname() + File.separator + "inputs";
		String fullPath = directoryPath + File.separator + patient.getFirstName() + "_" + patient.getSurname() + ".txt";
		
		try {
			File file = new File(fullPath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
				String[] array = parseInput(line);
				patient.addElement(array[0], Integer.parseInt(array[1]));
			}
			fileReader.close();
			System.out.println("Contents of file:");
			System.out.println(stringBuffer.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		patient.completeDictionary();
		
		return patient;
	}


	
	public String[] parseInput(String line) {
		String[] array = line.trim().split("\\s*-\\s*");		
		return array;
	}
}
