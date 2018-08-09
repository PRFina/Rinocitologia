package rinocitologia;

public class Anamnesi {
    //Variabili per tenere traccia delle informazioni delle varie anamnesi
    private String tipoParto="";
    private String alcool="";
    private String alimentazione="";
    private String attivita="";
    private String caffeina="";
    private String esecuzioneParto="";
    private String fumatore="";
    private String riposo="";
    private String scuola="";
    private String sviluppo="";
    private String droga="";
    private String stress="";
    private String eta="";
    private String allergia="";
    private String anamFamiliare="";
    private String anamProssima="";
    private String anamRemota="";

    public void setInfo(String tipoParto, String alcool, String alimentazione, String attivita, String caffeina, String esecuzioneParto, String fumatore, String riposo, String scuola, String sviluppo, String droga, String stress, String eta, String allergia, String anamFamiliare, String anamProssima, String anamRemota){
        this.setTipoParto(tipoParto);
        this.setAlcool(alcool);
        this.setAlimentazione(alimentazione);
        this.setAttivita(attivita);
        this.setCaffeina(caffeina);
        this.setEsecuzioneParto(esecuzioneParto);
        this.setFumatore(fumatore);
        this.setRiposo(riposo);
        this.setScuola(scuola);
        this.setSviluppo(sviluppo);
        this.setDroga(droga);
        this.setStress(stress);
        this.setEta(eta);
        this.setAllergia(allergia);
        this.setAnamFamiliare(anamFamiliare);
        this.setAnamProssima(anamProssima);
        this.setAnamRemota(anamRemota);
        System.out.println(this.tipoParto);
        System.out.println(this.anamFamiliare);
    }


    public String getTipoParto() {
        return tipoParto;
    }

    public void setTipoParto(String tipoParto) {
        this.tipoParto = tipoParto;
    }

    public String getAlcool() {
        return alcool;
    }

    public void setAlcool(String alcool) {
        this.alcool = alcool;
    }

    public String getAlimentazione() {
        return alimentazione;
    }

    public void setAlimentazione(String alimentazione) {
        this.alimentazione = alimentazione;
    }

    public String getAttivita() {
        return attivita;
    }

    public void setAttivita(String attivita) {
        this.attivita = attivita;
    }

    public String getCaffeina() {
        return caffeina;
    }

    public void setCaffeina(String caffeina) {
        this.caffeina = caffeina;
    }

    public String getEsecuzioneParto() {
        return esecuzioneParto;
    }

    public void setEsecuzioneParto(String esecuzioneParto) {
        this.esecuzioneParto = esecuzioneParto;
    }

    public String getFumatore() {
        return fumatore;
    }

    public void setFumatore(String fumatore) {
        this.fumatore = fumatore;
    }

    public String getRiposo() {
        return riposo;
    }

    public void setRiposo(String riposo) {
        this.riposo = riposo;
    }

    public String getScuola() {
        return scuola;
    }

    public void setScuola(String scuola) {
        this.scuola = scuola;
    }

    public String getSviluppo() {
        return sviluppo;
    }

    public void setSviluppo(String sviluppo) {
        this.sviluppo = sviluppo;
    }

    public String getDroga() {
        return droga;
    }

    public void setDroga(String droga) {
        this.droga = droga;
    }

    public String getStress() {
        return stress;
    }

    public void setStress(String stress) {
        this.stress = stress;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getAllergia() {
        return allergia;
    }

    public void setAllergia(String allergia) {
        this.allergia = allergia;
    }

    public String getAnamFamiliare() {
        return anamFamiliare;
    }

    public void setAnamFamiliare(String anamFamiliare) {
        this.anamFamiliare = anamFamiliare;
    }

    public String getAnamProssima() {
        return anamProssima;
    }

    public void setAnamProssima(String anamProssima) {
        this.anamProssima = anamProssima;
    }

    public String getAnamRemota() {
        return anamRemota;
    }

    public void setAnamRemota(String anamRemota) {
        this.anamRemota = anamRemota;
    }
}
