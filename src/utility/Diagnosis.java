package utility;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.itextpdf.text.DocumentException;

import net.sf.clipsrules.jni.*;
import rinocitologia.Patient;
import utility.ClipsManager;
import utility.InputHandler;
import utility.Utility;
/**
 * Launch CLIPS (Expert System) and get preliminary informations such as templates for facts and rules (sources located in src/clips).
 * Through the object handler (InputHandler) assert facts from the informations retrieved by neural network.
 */
public class Diagnosis {

	private Environment clips = null;
	private InputHandler handler;

	private ArrayList<ArrayList<String>> fattiAsseriti = new ArrayList<>();

	private Patient dict;
	private ClipsManager cm;

	public ClipsManager getClipsManager() { return cm; }

	public Diagnosis(Patient dict) throws FileNotFoundException, DocumentException {
		clips = new Environment(); 
		clips.clear();
		clips.loadFromResource("/clips/fatti.clp");
		clips.loadFromResource("/clips/functions.clp");
		clips.loadFromResource("/clips/diagnosi.clp");

		clips.reset();

		//DIAGNOSI NARES
		//dict = new Patient("Gian", "Sekko");
		/*
		dict.addElement("Eosinofili", 6);
		dict.addElement("Mastociti", 0);
		dict.addElement("Neutrofili", 6);
		handler = new InputHandler(clips, dict);
		handler.assertFacts();
		Utility util = new Utility(dict);
		util.writeReports();

*/
		dict.addAllElements();

		/*
		//EXAMPLE OF ADDING ELEMENTS MANUALLY TO DICTIONARY NO NAME
		dict = new Patient();
		dict.addElement("Neutrofili", 6);
		handler = new InputHandler(clips, dict);
		handler.assertFacts();
		Utility util = new Utility(dict);
		util.writeJson();
		util.writePdfReport();
		*/
		
		/*
		//EXAMPLE OF ADDING ELEMENTS MANUALLY TO DICTIONARY NAME
		dict = new Patient("gian", "sekko");
		dict.addElement("Neutrofili", 6);
		handler = new InputHandler(clips, dict);
		handler.assertFacts();
		Utility util = new Utility(dict);
		util.writeJson();
		util.writePdfReport();
		 */
		
		
		/*
		//EXAMPLE OF LOAD INFORMATIONS FROM JSON NO NAME
		Utility util = new Utility(new Patient());
		dict = util.readJson();
		System.out.println(dict);
		handler = new InputHandler(clips, dict);
		handler.assertFacts();
		*/
		
		/*
		//EXAMPLE OF LOAD INFORMATIONS FROM JSON NAME
		Utility util = new Utility(new Patient());
		dict = util.readJson("Lac", "Gua");
		System.out.println(dict);
		handler = new InputHandler(clips, dict);
		handler.assertFacts();
		*/
		
		
		/*
		//PROVA LETTURA DA FILE SERGIO
		dict = new Patient("Lac", "Gua");
		handler = new InputHandler(clips, dict);
		handler.readFile();
		System.out.println(dict);
		*/

		handler = new InputHandler(clips, dict);
		handler.assertFacts();
		this.setFattiAsseriti(handler.getFattiAsseriti());
		Utility util = new Utility(dict);
		util.writeReports();

		clips.run();
		System.out.println("Caricato run");

		clips.eval("(facts)");

		clips.eval("(build \"(defrule lol2 (scoperta(parte-anatomica ?f)) => (printout t \\\"PROVA BUILD - \\\" ?f crlf))\")");


		cm = new ClipsManager(clips);
		dict.setDiagnosi(cm.getDiagnosis());
		System.out.println("Pinco");
	}



	public Patient getDict() {
		return dict;
	}

	public ArrayList<ArrayList<String>> getFattiAsseriti() {
		return fattiAsseriti;
	}

	public void setFattiAsseriti(ArrayList<ArrayList<String>> fattiAsseriti) {
		this.fattiAsseriti = fattiAsseriti;
	}

	public void writeFileDiagnosi(String parteSinistra, String informazioni) {
		handler.writeFileDiagnosi(parteSinistra, informazioni);
	}

/*
	public static void main(String args[]) throws FileNotFoundException, DocumentException {
	// void repl() {
		//Diagnosis elimina = new Diagnosis();
		boolean endInteraction = false;
		Scanner in = new Scanner(System.in);
		while (!endInteraction) {
			System.out.print(" CLIPS >");
			String userInput = in.nextLine(); //Read
			try {
				String response = elimina.clips.eval(userInput).toString(); //Eval
				System.out.println(response); //print
				if (response.equals("(exit)")) { //loop
					endInteraction = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	*/
}