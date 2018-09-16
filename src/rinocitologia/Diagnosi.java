package rinocitologia;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Diagnosi {
    private String nome;
    private String time;
    private int mese;
    private ArrayList<String> informazioni = new ArrayList<String>();

    public Diagnosi(String nome, String info) {
        nome = nome.substring(1);
        nome = nome.substring(0, nome.length()-1);
        this.setNome(nome);
        info = info.substring(2);
        info = info.substring(0, info.length()-2);
        String str[] = info.split("\" \"");
        informazioni.addAll(Arrays.asList(str));

        System.out.println("NOME - "+this.nome);
        System.out.println("INFO "+this.informazioni);
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        LocalDate localDate = dateobj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.setMese(localDate.getMonthValue());
        this.time = df.format(dateobj);

    }

    public void setMese(int mese){ this.mese = mese; }

    public String getTime() {
        return time;
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
