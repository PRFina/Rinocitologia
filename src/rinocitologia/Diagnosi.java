package rinocitologia;

import java.util.ArrayList;
import java.util.Arrays;

public class Diagnosi {
    private String nome;
    private ArrayList<String> informazioni = new ArrayList<String>();

    public Diagnosi(String nome, String info) {
        nome = nome.substring(1);
        nome = nome.substring(0, nome.length()-1);
        this.setNome(nome);
        info = info.substring(2);
        info = info.substring(0, info.length()-2);
        String str[] = info.split("\" \"");
        informazioni.addAll(Arrays.asList(str));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<String> getInformazioni() {
        return informazioni;
    }

    public void setInformazioni(ArrayList<String> informazioni) {
        this.informazioni = informazioni;
    }
}
