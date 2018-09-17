package utility;

import java.util.ArrayList;

import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.FactAddressValue;
import net.sf.clipsrules.jni.MultifieldValue;
import rinocitologia.Diagnosi;

public class ClipsManager {
	private Environment clips;
	private ArrayList<Diagnosi> diagnosis = new ArrayList<Diagnosi>();
	
	public ClipsManager(Environment clips){
		this.clips = clips;
	}

	/**
	 * Returns a list of possible diagnosis from cell count.
	 * @return diagnosis ArrayList containing name of possible diagnosis.
	 */
	public ArrayList<Diagnosi> getDiagnosis(){
		MultifieldValue mv = (MultifieldValue) clips.eval("(find-all-facts((?f diagnosi)) TRUE)");
		for(int i=0;i<mv.size();i++){
		    FactAddressValue fv = (FactAddressValue) mv.get(i);
		    try {
				diagnosis.add(new Diagnosi(fv.getFactSlot("nome").toString(), fv.getFactSlot("informazioni").toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return diagnosis;
	}
}
