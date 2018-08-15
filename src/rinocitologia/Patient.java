package rinocitologia;
import utility.CodiceFiscale;

import java.io.File;
import java.util.*;

/**
 * Contains informations meaningful for the diagnosis such as an HashMap in the format:
 * <br>
 * <String, Cellule> => <Cell Name, Cell Informations>
 * <br>
 * Cell Informations contains the count of the cells (done by neural network) and the grade transposition.
 * <br>
 * Contains methods useful to manipulate those informations.
 */
public class Patient {
	private String firstName; //????????
	private String surname; //??????
	private Map<String, Cell> dictionary;
	private String path;
	private String pathData = System.getProperty("user.home") + File.separator + "data";
	private CodiceFiscale cf;

	//private Anamnesi anamnesi;
	private ArrayList<Anamnesi> anamnesiList = new ArrayList<>();

	/*
	//In ogni Controller in anamnesiCaller devi
	if(patient.getAnamnesi == null){
		Anamnesi anam = new Anamnesi()
		patient.setAnamnesi(anam);
	}
	controller.setPatient(patient);
	*/

	public Patient() {
		dictionary = new HashMap<String, Cell>();
		final File folderData = new File(pathData);
		if(!folderData.exists() && !folderData.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}


		final File folder = new File(pathData , "Pinco_Pallino");
		if(!folder.exists() && !folder.mkdir()) {
			   //failed to create the folder, probably exit
			   throw new RuntimeException("Failed to create save directory.");
		}
		this.path = folder.getAbsolutePath();
		System.out.print(path);

	}


	public Patient(String name, String surname) {
		dictionary = new HashMap<String, Cell>();
		this.firstName = name;
		this.surname = surname;
		final File folderData = new File(pathData);
		if(!folderData.exists() && !folderData.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}
		final File folder = new File(pathData , name + "_" + surname);
		if(!folder.exists() && !folder.mkdir()) {
			   //failed to create the folder, probably exit
			   throw new RuntimeException("Failed to create save directory.");
		}
		this.path = folder.getAbsolutePath();

	}

	//public void setAnamnesi(Anamnesi anamnesi) { this.anamnesi = anamnesi; }

	//public Anamnesi getAnamnesi() {return anamnesi;}

	public void addAnamnesi(Anamnesi anamnesi) { this.anamnesiList.add(anamnesi); }

	public ArrayList<Anamnesi> getAnamnesiList() {return anamnesiList;}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Map<String, Cell> getDictionary() {
		return dictionary;
	}

	public void setDictionary(Map<String, Cell> dictionary) {
		this.dictionary = dictionary;
	}

	public String getPath() { return path;	}

	public void setPath(String path) { this.path = path; }

	public void setNewPath(){this.path = pathData + File.separator + cf.getCF();}

	public String getPathData() { return pathData; }

	public void setPathData(String folderPath) { this.pathData = folderPath; }

	public void setCf(CodiceFiscale cf) {this.cf = cf;}

	public CodiceFiscale getCf() {return cf;}


	/**
	 * Rename patient folder and saves path to path variable
	 */
	public void rename(){
		final File folder = new File(path);
		if(!folder.exists() && !folder.mkdir()) {
			//failed to create the folder, probably exit
			throw new RuntimeException("Failed to create save directory.");
		}
		File newDir = new File(pathData, cf.getCF());
		folder.renameTo(newDir);
		this.path = newDir.getAbsolutePath();
		System.out.print(path);
	}

	/**
	 * Put an element in the HashMap (dictionary).
	 * Since there could be some missunderstanding in the format of the name, it will be lower cased and then capitalized just the first letter.
	 * @param nome String that contains name of the cell.
	 * @param numeroCellule Integer that contains the number of the cells (for each type) retrieved by neural network.
	 */
	public void addElement(String nome, int numeroCellule) {
		String actualName = nome.toLowerCase().substring(0,1).toUpperCase() + nome.toLowerCase().substring(1);
		dictionary.put(actualName, new Cell(nome, numeroCellule));
		
	}
	
	/**
	 * Autofills the dictionary with the missing informations from neural network.
	 * It is filled with missing cells with a count of 0.
	 */
	public void completeDictionary() {
		List<String> cellsList = Arrays.asList("Ciliate", "Mucipare", "Metaplastiche", "Neutrofili", "Eosinofili", "Mastociti", "Linfociti", "Batteri", "Spore", "Macchia", "Epiteliali");
		String cellName;
		Iterator<String> i = cellsList.iterator();
		while(i.hasNext()) {
			cellName = i.next();
			Cell cell = dictionary.get(cellName);
			if(cell == null) {
				dictionary.put(cellName, new Cell(0,0));
			}
		}
	}

	public Anamnesi getAnamnesi(String time){
		Anamnesi anam = new Anamnesi();
		for(Anamnesi anamnesi: anamnesiList){
			if (anamnesi.getTime() == time)
				anam = anamnesi;
		}
		return anam;
	}
	
	@Override
	public String toString() {
		String name = "First Name: " + firstName + "\nSurname: " + surname + "\n\n";
		StringBuilder cells = new StringBuilder("Cells List:\n");
		for (Map.Entry<String, Cell> entry : dictionary.entrySet()) {
			cells.append("Name: " + entry.getKey() + " - Count: " + entry.getValue().getcellCount() + " - Grade: " + entry.getValue().getgrade() + ";\n");
		}
		if(cf != null)
			return name + cells.toString() + cf.toString();
		else
			return name + cells.toString();
	}


}


