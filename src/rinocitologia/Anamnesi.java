package rinocitologia;

import javafx.util.converter.DateTimeStringConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Anamnesi {
    private String allergiaGen, tipoAllergiaGen, allergiaFra, tipoAllergiaFra, poliposiGen, asmaFra, asmaGen, poliposiFra = "";

    private String appuntiFam = "";

    private String ostruzione, rinorrea, espansioneRinorrea, pruritoNasale, starnuto, olfatto, ovattamento, ipoacusia, acufeni, vertigini, febbre, farmaci = "";

    private Boolean lacrimazione = false;
    private Boolean fotofobia = false;
    private Boolean prurito = false;
    private Boolean bruciore = false;

    private String appuntiPat = "";

    private String pirNasale, valNasale, setto, turbinati, polSx, polDx, essudato, ipertrofia = "";

    private String appuntiAlterazioniLIO, appuntiEsameOtoscopico, appuntiEsameRinom = "";

    private ArrayList<String> allergie;

    private String allergia="";

    private String baseSx, baseDx, baseSxDx, decongSx, decongDx, decongSxDx = "";

    private String time;

    private int mese;

    public Anamnesi(){ this.setTime(); }

    public String getAllergiaGen() {
        return allergiaGen;
    }

    public void setAllergiaGen(String allergiaGen) {
        this.allergiaGen = allergiaGen;
    }

    public String getTipoAllergiaGen() {
        return tipoAllergiaGen;
    }

    public void setTipoAllergiaGen(String tipoAllergiaGen) {
        this.tipoAllergiaGen = tipoAllergiaGen;
    }

    public String getAllergiaFra() {
        return allergiaFra;
    }

    public void setAllergiaFra(String allergiaFra) {
        this.allergiaFra = allergiaFra;
    }

    public String getTipoAllergiaFra() {
        return tipoAllergiaFra;
    }

    public void setTipoAllergiaFra(String tipoAllergiaFra) {
        this.tipoAllergiaFra = tipoAllergiaFra;
    }

    public String getPoliposiGen() {
        return poliposiGen;
    }

    public void setPoliposiGen(String poliposiGen) {
        this.poliposiGen = poliposiGen;
    }

    public String getAsmaFra() {
        return asmaFra;
    }

    public void setAsmaFra(String asmaFra) {
        this.asmaFra = asmaFra;
    }

    public String getAsmaGen() {
        return asmaGen;
    }

    public void setAsmaGen(String asmaGen) {
        this.asmaGen = asmaGen;
    }

    public String getPoliposiFra() {
        return poliposiFra;
    }

    public void setPoliposiFra(String poliposiFra) {
        this.poliposiFra = poliposiFra;
    }

    public String getAppuntiFam() {
        return appuntiFam;
    }

    public void setAppuntiFam(String appuntiFam) {
        this.appuntiFam = appuntiFam;
    }

    public String getOstruzione() {
        return ostruzione;
    }

    public void setOstruzione(String ostruzione) {
        this.ostruzione = ostruzione;
    }

    public String getRinorrea() {
        return rinorrea;
    }

    public void setRinorrea(String rinorrea) {
        this.rinorrea = rinorrea;
    }

    public String getPruritoNasale() {
        return pruritoNasale;
    }

    public void setPruritoNasale(String pruritoNasale) {
        this.pruritoNasale = pruritoNasale;
    }

    public String getStarnuto() {
        return starnuto;
    }

    public void setStarnuto(String starnuto) {
        this.starnuto = starnuto;
    }


    public String getOlfatto() {
        return olfatto;
    }

    public void setOlfatto(String olfatto) {
        this.olfatto = olfatto;
    }

    public String getOvattamento() {
        return ovattamento;
    }

    public void setOvattamento(String ovattamento) {
        this.ovattamento = ovattamento;
    }

    public String getIpoacusia() {
        return ipoacusia;
    }

    public void setIpoacusia(String ipoacusia) {
        this.ipoacusia = ipoacusia;
    }

    public String getAcufeni() {
        return acufeni;
    }

    public void setAcufeni(String acufeni) {
        this.acufeni = acufeni;
    }

    public String getVertigini() {
        return vertigini;
    }

    public void setVertigini(String vertigini) {
        this.vertigini = vertigini;
    }

    public Boolean getLacrimazione() {
        return lacrimazione;
    }

    public void setLacrimazione(Boolean lacrimazione) {
        this.lacrimazione = lacrimazione;
    }

    public Boolean getFotofobia() {
        return fotofobia;
    }

    public void setFotofobia(Boolean fotofobia) {
        this.fotofobia = fotofobia;
    }

    public Boolean getPrurito() {
        return prurito;
    }

    public void setPrurito(Boolean prurito) {
        this.prurito = prurito;
    }

    public Boolean getBruciore() {
        return bruciore;
    }

    public void setBruciore(Boolean bruciore) {
        this.bruciore = bruciore;
    }

    public String getAppuntiPat() {
        return appuntiPat;
    }

    public void setAppuntiPat(String appuntiPat) {
        this.appuntiPat = appuntiPat;
    }

    public String getPirNasale() {
        return pirNasale;
    }

    public void setPirNasale(String pirNasale) {
        this.pirNasale = pirNasale;
    }

    public String getValNasale() {
        return valNasale;
    }

    public void setValNasale(String valNasale) {
        this.valNasale = valNasale;
    }

    public String getSetto() {
        return setto;
    }

    public void setSetto(String setto) {
        this.setto = setto;
    }

    public String getTurbinati() {
        return turbinati;
    }

    public void setTurbinati(String turbinati) {
        this.turbinati = turbinati;
    }

    public String getEssudato() {
        return essudato;
    }

    public void setEssudato(String essudato) {
        this.essudato = essudato;
    }

    public String getIpertrofia() {
        return ipertrofia;
    }

    public void setIpertrofia(String ipertrofia) {
        this.ipertrofia = ipertrofia;
    }

    public String getAppuntiAlterazioniLIO() {
        return appuntiAlterazioniLIO;
    }

    public void setAppuntiAlterazioniLIO(String appuntiAlterazioniLIO) { this.appuntiAlterazioniLIO = appuntiAlterazioniLIO; }

    public String getAppuntiEsameOtoscopico() {
        return appuntiEsameOtoscopico;
    }

    public void setAppuntiEsameOtoscopico(String appuntiEsameOtoscopico) { this.appuntiEsameOtoscopico = appuntiEsameOtoscopico; }

    public String getAppuntiEsameRinom() {
        return appuntiEsameRinom;
    }

    public void setAppuntiEsameRinom(String appuntiEsameRinom) {
        this.appuntiEsameRinom = appuntiEsameRinom;
    }


    public String getEspansioneRinorrea() { return espansioneRinorrea; }

    public void setEspansioneRinorrea(String espansione) { this.espansioneRinorrea = espansione; }


    public String getPolSx() { return polSx; }

    public void setPolSx(String polSx) { this.polSx = polSx; }

    public String getPolDx() { return polDx; }

    public void setPolDx(String polDx) { this.polDx = polDx; }

    public String getAllergia() { return allergia; }

    public void setAllergia() {
        String allergia = "";
        for(String el:allergie){
            allergia = allergia + ", " + el;
        }
        if(allergia.length()>0){
            allergia = allergia.substring(1);
            this.allergia = allergia;
        } else {
            this.allergia = "Non presenti.";
        }
    }

    public void setMese(int mese){ this.mese = mese; }

    public int getMese() { return mese; }

    public ArrayList<String> getAllergie() { return allergie; }

    public String getBaseSx() { return baseSx; }

    public void setBaseSx(String baseSx) { this.baseSx = baseSx; }

    public String getBaseDx() { return baseDx; }

    public void setBaseDx(String baseDx) { this.baseDx = baseDx; }

    public String getBaseSxDx() { return baseSxDx; }

    public void setBaseSxDx(String baseSxDx) { this.baseSxDx = baseSxDx; }

    public String getDecongSx() { return decongSx; }

    public void setDecongSx(String decongSx) { this.decongSx = decongSx; }

    public String getDecongDx() { return decongDx; }

    public void setDecongDx(String decongDx) { this.decongDx = decongDx; }

    public String getDecongSxDx() { return decongSxDx; }

    public void setDecongSxDx(String decongSxDx) { this.decongSxDx = decongSxDx; }

    public void setAllergie(ArrayList<String> allergie) { this.allergie = allergie; }

    public String getFebbre() { return febbre; }

    public void setFebbre(String febbre) { this.febbre = febbre; }

    public String getFarmaci() {return farmaci; }

    public void setFarmaci(String farmaci) { this.farmaci = farmaci; }

    public void setInfo(String allergiaGenitori, String tipoAllergiaGen, String allergiaFratelli, String tipoAllergiaFratelli, String poliposiGenitori,
                        String asmaFratelli, String asmaGenitori, String poliposiFratelli, String appuntiFamiglia, String ostruzione, String rinorrea,
                        String espansione, String pruritoNasale, String starnutazione, String olfatto, String ovattamento, String ipoacusia, String acufeni,
                        String vertigini, String febbre, String farmaci, boolean lacrimazione, boolean fotofobia, boolean prurito, boolean bruciore, String appuntiPat, String pirNasale,
                        String valNasale, String setto, String turbinati, String polSx, String polDx, String essudato, String ipertrofia, String alterazioniLIO,
                        String esameOtoscopico, String esameRinom, ArrayList<String> allergie, String baseSx, String baseDx,
                        String decongSx, String decongDx, String baseSxDx, String decongSxDx) {
        this.setAllergiaGen(allergiaGenitori);
        this.setTipoAllergiaGen(tipoAllergiaGen);
        this.setAllergiaFra(allergiaFratelli);
        this.setTipoAllergiaFra(tipoAllergiaFratelli);
        this.setPoliposiGen(poliposiGenitori);
        this.setPoliposiFra(poliposiFratelli);
        this.setAsmaGen(asmaGenitori);
        this.setAsmaFra(asmaFratelli);
        this.setAppuntiFam(appuntiFamiglia);
        this.setOstruzione(ostruzione);
        this.setRinorrea(rinorrea);
        this.setEspansioneRinorrea(espansione);
        this.setPruritoNasale(pruritoNasale);
        this.setStarnuto(starnutazione);
        this.setOlfatto(olfatto);
        this.setOvattamento(ovattamento);
        this.setIpoacusia(ipoacusia);
        this.setAcufeni(acufeni);
        this.setVertigini(vertigini);
        this.setFebbre(febbre);
        this.setFarmaci(farmaci);
        this.setLacrimazione(lacrimazione);
        this.setFotofobia(fotofobia);
        this.setPrurito(prurito);
        this.setBruciore(bruciore);
        this.setAppuntiPat(appuntiPat);
        this.setPirNasale(pirNasale);
        this.setValNasale(valNasale);
        this.setSetto(setto);
        this.setTurbinati(turbinati);
        this.setPolSx(polSx);
        this.setPolDx(polDx);
        this.setEssudato(essudato);
        this.setIpertrofia(ipertrofia);
        this.setAppuntiAlterazioniLIO(alterazioniLIO);
        this.setAppuntiEsameOtoscopico(esameOtoscopico);
        this.setAppuntiEsameRinom(esameRinom);
        this.setAllergie(allergie);
        this.setAllergia();
        this.setBaseSx(baseSx);
        this.setDecongSx(decongSx);
        this.setBaseDx(baseDx);
        this.setDecongDx(decongDx);
        this.setBaseSxDx(baseSxDx);
        this.setDecongSxDx(decongSxDx);

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        this.time = df.format(dateobj);

        System.out.println(df.format(dateobj));
    }


    public String getTime() {
        return time;
    }

    public void setTime() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        LocalDate localDate = dateobj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.setMese(localDate.getMonthValue());
        this.time = df.format(dateobj);

        System.out.println(df.format(dateobj));
    }

    public String booleanToString(boolean b) {
        return b ? "yes" : "no";
    }

    @Override
    public String toString() {
        String anamnesi = "Anamnesi familiare\n";
        if(allergiaGen != null)
            anamnesi += "Allergia Genitori: " + allergiaGen + "\n";
        if((tipoAllergiaGen != null)&&(tipoAllergiaGen !=""))
            anamnesi += "Tipo Allergia Genitori: " + tipoAllergiaGen + "\n";
        if((allergiaFra != null)&&(tipoAllergiaFra !=""))
            anamnesi += "Allergia Fratelli: " + allergiaFra + "\n";
        if(tipoAllergiaFra != null)
            anamnesi += "Tipo Allergia Fratelli: " + tipoAllergiaFra + "\n";
        if(poliposiGen != null)
            anamnesi += "Poliposi Genitori: " + poliposiGen + "\n";
        if(poliposiFra != null && poliposiFra != "")
            anamnesi += "Poliposi Fratelli: " + poliposiFra + "\n";
        if(asmaFra != null)
            anamnesi += "Asma Fratelli: " + asmaFra + "\n";
        if(asmaGen != null)
            anamnesi += "Asma Genitori: " + asmaGen + "\n";
        if(appuntiFam != null && appuntiFam != "")
            anamnesi += "Appunti Anamnesi Familiare: " + appuntiFam + "\n";

        anamnesi += "\nPatologica prossima - Sintomi\n";

        if(ostruzione != null)
            anamnesi += "Ostruzione: " + ostruzione + "\n";
        if(rinorrea != null)
            anamnesi += "Rinorrea: " + rinorrea + "\n";
        if((espansioneRinorrea != null)&&(espansioneRinorrea !=""))
            anamnesi += "Espansione Rinorrea: " + espansioneRinorrea + "\n";
        if(pruritoNasale != null)
            anamnesi += "Prurito Nasale: " + pruritoNasale + "\n";
        if(starnuto != null)
            anamnesi += "Starnuto: " + starnuto + "\n";
        if(olfatto != null)
            anamnesi += "Olfatto: " + olfatto + "\n";
        if(ovattamento != null)
            anamnesi += "Ovattamento: " + ovattamento + "\n";
        if(ipoacusia != null)
            anamnesi += "Ipoacusia: " + ipoacusia + "\n";
        if(acufeni != null)
            anamnesi += "Acufeni: " + acufeni + "\n";
        if(vertigini != null && vertigini != "")
            anamnesi += "Vertigini: " + vertigini + "\n";
        if(febbre != null && febbre != "")
            anamnesi += "Febbre: " + febbre + "\n";
        if(farmaci != null && farmaci != "")
            anamnesi += "Farmaci: " + farmaci + "\n";

        anamnesi += "Lacrimazione: " + booleanToString(lacrimazione) + "\n";
        anamnesi += "Fotofobia: " + booleanToString(fotofobia) + "\n";
        anamnesi += "Prurito : " + booleanToString(prurito) + "\n";
        anamnesi += "Bruciore: " + booleanToString(bruciore) + "\n";

        if(appuntiPat != null && appuntiPat != "")
            anamnesi += "Appunti Anamnesi Patologica Prossima: " + appuntiPat + "\n";

        anamnesi += "\nEsame obbiettivo strumentale del naso\n";

        if(pirNasale != null)
            anamnesi += "Piramide Nasale: " + pirNasale + "\n";
        if(valNasale != null)
            anamnesi += "Valvola Nasale: " + valNasale + "\n";

        anamnesi += "\nEndoscopia nasale, Esame Rinomanometrico, Esame Otoscopico e Allergologico\n\n";

        if(setto != null)
            anamnesi += "Setto Nasale: " + setto + "\n";
        if(turbinati != null)
            anamnesi += "Turbinati: " + turbinati + "\n";
        if(polSx != null)
            anamnesi += "Poliposi lato sinistro del naso: " + polSx + "\n";
        if(polDx != null)
            anamnesi += "Poliposi lato destro del naso: " + polDx + "\n";
        if(essudato != null)
            anamnesi += "Essudato: " + essudato + "\n";
        if(ipertrofia != null && ipertrofia != "")
            anamnesi += "Ipertrofia: " + ipertrofia + "\n";
        if(appuntiAlterazioniLIO != null)
            anamnesi += "Appunti Alterazioni LIO: " + appuntiAlterazioniLIO + "\n";
        if(appuntiEsameOtoscopico != null)
            anamnesi += "Appunti Esame Otoscopico: " + appuntiEsameOtoscopico + "\n";
        if(appuntiEsameRinom != null && appuntiEsameRinom != "")
            anamnesi += "Appunti Esame Rinomanometrico: " + appuntiEsameRinom + "\n";
        anamnesi += "Allergia/e: " + allergia + "\n";
        if(baseSx != null)
            anamnesi += "Base Sx: " + baseSx + "\n";
        if(baseDx != null)
            anamnesi += "Base Dx: " + baseDx + "\n";
        if(baseSxDx != null)
            anamnesi += "Base Sx e Dx: " + baseSxDx + "\n";
        if(decongSx != null)
            anamnesi += "Decongestione Sx: " + decongSx + "\n";
        if(decongDx != null)
            anamnesi += "Decongestione Dx: " + decongDx + "\n";
        if(decongSxDx != null && decongSxDx != "")
            anamnesi += "Decongestione Sx e Dx: " + decongSxDx + "\n";
        return anamnesi;

    }
}
