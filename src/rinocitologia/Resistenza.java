package rinocitologia;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextField;

public class Resistenza {
    private final SimpleStringProperty titolo;
    private TextField base;
    private TextField decong;
    private final SimpleStringProperty valori;


    public Resistenza(String fName, String lName, String email, String value) {
        this.titolo = new SimpleStringProperty(fName);
        this.base = new TextField(lName);
        this.decong = new TextField(email);
        this.valori = new SimpleStringProperty(value);

    }

    public String getTitolo() {
        return titolo.get();
    }
    public void setTitolo(String fName) {
        titolo.set(fName);
    }

    public TextField getBase() {
        return base;
    }
    public void setBase(TextField fName) { base = fName; }

    public TextField getDecong() {
        return decong;
    }
    public void setDecong(TextField fName) { decong = fName; }

    public String getValori() { return valori.get(); }
    public void setValori(String remar) {
        valori.set(remar);
    }

}

