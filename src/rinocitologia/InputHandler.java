package rinocitologia;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
		System.out.println("posizione --------"+patient.getAnamnesiList().size());
		if((anamnesi.getAllergiaGen() != null)&&(anamnesi.getAllergiaGen() == "si"))
			clips.assertString("(famiglia(soggetto genitore) (disturbo allergia) (tipo "+anamnesi.getTipoAllergiaGen()+"))");
		if((anamnesi.getAllergiaFra() != null)&&(anamnesi.getAllergiaFra() == "si"))
			clips.assertString("(famiglia(soggetto fratello) (disturbo allergia) (tipo "+anamnesi.getTipoAllergiaFra()+"))");
		if((anamnesi.getPoliposiGen() != null)&&(anamnesi.getPoliposiGen() == "si"))
			clips.assertString("(famiglia(soggetto genitore) (disturbo poliposi))");
		if((anamnesi.getPoliposiFra() != null)&&(anamnesi.getPoliposiFra() == "si"))
			clips.assertString("(famiglia(soggetto fratello) (disturbo poliposi))");
		if((anamnesi.getAsmaGen() != null)&&(anamnesi.getAsmaGen() == "si"))
			clips.assertString("(famiglia(soggetto genitore) (disturbo asma))");
		if((anamnesi.getAsmaFra() != null)&&(anamnesi.getAsmaFra() == "si"))
			clips.assertString("(famiglia(soggetto fratello) (disturbo asma))");
		int i=0;
		if((anamnesi.getOstruzione()!=null)&&(anamnesi.getOstruzione()!= "nessuna"))
			clips.assertString("(sintomo (nome \"Ostruzione nasale "+anamnesi.getOstruzione()+"\"))");
		if((anamnesi.getRinorrea()!=null)&&(anamnesi.getRinorrea()!= "nessuna")){
			clips.assertString("(sintomo (nome \"Rinorrea "+anamnesi.getRinorrea()+"\"))");
			clips.assertString("(sintomo (nome \"Espansione rinorrea: "+anamnesi.getEspansioneRinorrea()+"\"))");
		}
		if((anamnesi.getPruritoNasale()!= null)&&(anamnesi.getPruritoNasale()== "si"))
			clips.assertString("(sintomo (nome \"Prurito nasale\"))");
		if((anamnesi.getStarnuto()!=null)&&(anamnesi.getStarnuto()!= "nessuna"))
			clips.assertString("(sintomo (nome \"Starnutazione "+anamnesi.getStarnuto()+"\"))");
		if((anamnesi.getOlfatto()!=null)&&(anamnesi.getOlfatto()!= "nessuno"))
			clips.assertString("(sintomo (nome \"Problemi olfattivi dovuti a "+anamnesi.getOlfatto()+"\"))");
		if((anamnesi.getOvattamento()!=null)&&(anamnesi.getOvattamento()!= "nessuno"))
			clips.assertString("(sintomo (nome \"Ovattamento "+anamnesi.getOvattamento()+"\"))");
		if((anamnesi.getIpoacusia()!=null)&&(anamnesi.getIpoacusia()!= "nessuno"))
			clips.assertString("(sintomo (nome \"Ipoacusi "+anamnesi.getIpoacusia()+"\"))");
		if((anamnesi.getAcufeni()!=null)&&(anamnesi.getAcufeni()!= "nessuno"))
			clips.assertString("(sintomo (nome \"Acufeni "+anamnesi.getAcufeni()+"\"))");
		if((anamnesi.getVertigini()!="")&&(anamnesi.getVertigini()!= "nessuna")&&(anamnesi.getVertigini()!=null))
			clips.assertString("(sintomo (nome \"Sindrome vertiginosa "+anamnesi.getVertigini()+"\"))");
		if(anamnesi.getLacrimazione())
			clips.assertString("(sintomo (nome \"Lacrimazione\"))");
		if(anamnesi.getFotofobia())
			clips.assertString("(sintomo (nome \"Fotofobia\"))");
		if(anamnesi.getPrurito())
			clips.assertString("(sintomo (nome \"Prurito occhio\"))");
		if(anamnesi.getBruciore())
			clips.assertString("(sintomo (nome \"Bruciore\"))");

		if((anamnesi.getPirNasale()!=null)&&(anamnesi.getPirNasale()!="normofornata"))
			clips.assertString("(scoperta (parte-anatomica piramide-nasale) (caratteristica \""+anamnesi.getPirNasale()+"\"))");
		if((anamnesi.getValNasale()!=null)&&(anamnesi.getValNasale()!="normofunzionante"))
			clips.assertString("(scoperta (parte-anatomica valvola-nasale) (caratteristica \""+anamnesi.getValNasale()+"\"))");
		if((anamnesi.getSetto()!=null)&&(anamnesi.getSetto()!="in asse"))
			clips.assertString("(scoperta (parte-anatomica setto-nasale) (caratteristica \""+anamnesi.getSetto()+"\"))");
		if((anamnesi.getTurbinati()!=null)&&(anamnesi.getTurbinati()!="normofornata"))
			clips.assertString("(scoperta (parte-anatomica turbinati) (caratteristica \""+anamnesi.getTurbinati()+"\"))");
		if((anamnesi.getPolSx()!=null)&&(anamnesi.getPolSx()!="assente"))
			clips.assertString("(scoperta (parte-anatomica poliposi-sinistra) (caratteristica \""+anamnesi.getPolSx()+"\"))");
		if((anamnesi.getPolDx()!=null)&&(anamnesi.getPolDx()!="assente"))
			clips.assertString("(scoperta (parte-anatomica poliposi-destra) (caratteristica \""+anamnesi.getPolDx()+"\"))");
		if((anamnesi.getEssudato()!=null)&&(anamnesi.getEssudato()!="assente"))
			clips.assertString("(scoperta (parte-anatomica essudato) (caratteristica \""+anamnesi.getEssudato()+"\"))");
		if((anamnesi.getIpertrofia()!="")&&(anamnesi.getIpertrofia()!="normofornata")&&(anamnesi.getIpertrofia()!=null))
			clips.assertString("(scoperta (parte-anatomica ipertrofia) (caratteristica \""+anamnesi.getIpertrofia()+"\"))");
		//Manca la gestione dell'esame rinomanometrico e dell'esame otoscopico
		if(anamnesi.getAllergia()!= "Non presenti."){
			if(anamnesi.getAllergia()!=""){
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
		}else
			clips.assertString("(prick-test(esito negativo))");
	}

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
