package rinocitologia;
import com.itextpdf.text.DocumentException;
import utility.Utility;

import java.io.File;
import java.io.FileNotFoundException;

public class Prove {
	private InputHandler handler;
	private Patient dict;
	private Utility util;
	private Diagnosis diagnosis;

	public Prove(Patient dict){
		this.dict = dict;
		dict.addElement("Eosinofili", 6);
		dict.addElement("Mastociti", 6);
		dict.addElement("Neutrofili", 6);
		util = new Utility(dict);
		util.writeReports();
		try {
			diagnosis = new Diagnosis(dict);
		} catch (FileNotFoundException | DocumentException e ){
			e.printStackTrace();
		}
	}

	public Patient getPatient(){
		return dict;
	}

	public Utility getUtil(){
		return util;
	}
}
