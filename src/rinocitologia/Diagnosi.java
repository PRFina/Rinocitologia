package rinocitologia;

import java.util.ArrayList;
import java.util.Arrays;

public class Diagnosi {
    private String nome;
    private ArrayList<String> informazioni = new ArrayList<String>();

    public Diagnosi(String nome, String info) {
        System.out.println("COSA STO PASSANDO - NOME "+ nome);
        System.out.println("COSA STO PASSANDO - INFO "+ info);
        nome = nome.substring(1);
        nome = nome.substring(0, nome.length()-1);
        this.setNome(nome);
        if(info.length() > 2){
            info = info.substring(2);
            info = info.substring(0, info.length()-2);
            String str[] = info.split("\" \"");
            informazioni.addAll(Arrays.asList(str));
        }
        System.out.println("COSA OTTENGO - NOME "+this.nome);
        System.out.println("COSA OTTENGO - INFO "+this.informazioni);
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
