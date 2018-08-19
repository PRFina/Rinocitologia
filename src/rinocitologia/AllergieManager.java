package rinocitologia;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AllergieManager {

    private final Map<String, int[]> pollini;

    /**
     * Initializes pollen calendar.
     */
    public AllergieManager(){
        pollini = new HashMap<>();

        int[] cupressacee = new int[36];
        Arrays.fill(cupressacee, 0);
        for(int i = 0; i < 12; i++){
            cupressacee[i] = 1;
        }
        cupressacee[4] = 2;
        cupressacee[9] = 2;
        for(int i = 5; i < 9; i++){
            cupressacee[i] = 3;
        }
        pollini.put("Cupressacee/Taxacee", cupressacee);


        int[] nocciolo = new int[36];
        Arrays.fill(nocciolo, 0);
        for(int i = 2; i < 11; i++){
            nocciolo[i] = 1;
        }
        nocciolo[5] = 2;
        nocciolo[6] = 2;
        pollini.put("Nocciolo", nocciolo);


        int[] ontano = new int[36];
        Arrays.fill(ontano, 0);
        for(int i = 2; i < 10; i++){
            ontano[i] = 1;
        }
        ontano[5] = 2;
        ontano[6] = 2;
        for(int i = 13; i < 17; i++){
            ontano[i] = 1;
        }
        pollini.put("Ontano", ontano);

        int[] pioppo = new int[36];
        Arrays.fill(pioppo, 0);
        for(int i = 5; i < 11; i++){
            pioppo[i] = 1;
        }
        pioppo[7] = 3;
        pioppo[8] = 2;
        pollini.put("Pioppo", pioppo);

        int[] frassino = new int[36];
        Arrays.fill(frassino, 0);
        for(int i = 5; i < 13; i++){
            frassino[i] = 1;
        }
        for(int i = 8; i < 12; i++){
            frassino[i] = 2;
        }
        pollini.put("Frassino comune", frassino);

        int[] betulla = new int[36];
        Arrays.fill(betulla, 0);
        for(int i = 8; i < 11; i++){
            betulla[i] = 3;
        }
        betulla[11] = 2;
        betulla[7] = 2;
        for(int i = 12; i < 15; i++){
            betulla[i] = 1;
        }
        pollini.put("Betulla", betulla);


        int[] salice = new int[36];
        Arrays.fill(salice, 0);
        for(int i = 7; i < 12; i++){
            salice[i] = 1;
        }
        pollini.put("Salice", salice);


        int[] carpino = new int[36];
        Arrays.fill(carpino, 0);
        for(int i = 7; i < 18; i++){
            carpino[i] = 1;
        }
        carpino[19] = 1;
        for(int i = 8; i < 13; i++){
            carpino[i] = 3;
        }
        pollini.put("Carpino nero", carpino);


        int[] quercia = new int[36];
        Arrays.fill(quercia, 0);
        for(int i = 8; i < 15; i++){
            quercia[i] = 1;
        }
        for(int i = 9; i < 13; i++){
            quercia[i] = 2;
        }
        for(int i = 10; i < 12; i++){
            quercia[i] = 3;
        }
        pollini.put("Quercia", quercia);


        int[] graminacee = new int[36];
        Arrays.fill(graminacee, 0);
        for(int i = 7; i < 28; i++){
            graminacee[i] = 1;
        }
        for(int i = 10; i < 20; i++){
            graminacee[i] = 2;
        }
        for(int i = 12; i < 16; i++){
            graminacee[i] = 3;
        }
        pollini.put("Graminacee", graminacee);


        int[] poligonacee = new int[36];
        Arrays.fill(poligonacee, 0);
        for(int i = 9; i < 16; i++){
            poligonacee[i] = 1;
        }
        pollini.put("Poligonacee", poligonacee);


        int[] orniello = new int[36];
        Arrays.fill(orniello, 0);
        for(int i = 9; i < 18; i++){
            orniello[i] = 1;
        }
        for(int i = 10; i < 15; i++){
            orniello[i] = 2;
        }
        for(int i = 11; i < 14; i++){
            orniello[i] = 3;
        }
        pollini.put("Orniello", orniello);

        int[] urticacee = new int[36];
        Arrays.fill(urticacee, 0);
        for(int i = 9; i < 35; i++){
            urticacee[i] = 1;
        }
        for(int i = 11; i < 28; i++){
            urticacee[i] = 2;
        }
        for(int i = 16; i < 25; i++){
            urticacee[i] = 3;
        }
        urticacee[14] = 3;
        pollini.put("Urticacee", urticacee);


        int[] castagno = new int[36];
        Arrays.fill(castagno, 0);
        for(int i = 14; i < 21; i++){
            castagno[i] = 1;
        }
        castagno[16] = 2;
        castagno[17] = 3;
        castagno[18] = 2;
        pollini.put("Castagno", castagno);


        int[] platano = new int[36];
        Arrays.fill(platano, 0);
        platano[8] = 2;
        platano[10] = 2;
        platano[11] = 1;
        platano[9] = 3;
        pollini.put("Platano", platano);


        int[] pinacee = new int[36];
        Arrays.fill(pinacee, 0);
        for(int i = 9; i < 21; i++){
            pinacee[i] = 1;
        }
        for(int i = 25; i < 35; i++){
            pinacee[i] = 1;
        }
        for(int i = 11; i < 17; i++){
            pinacee[i] = 2;
        }
        for(int i = 12; i < 15; i++){
            pinacee[i] = 3;
        }
        for(int i = 26; i < 29; i++){
            pinacee[i] = 2;
        }
        pinacee[30] = 2;
        pollini.put("Pinacee", pinacee);


        int[] piantaggine = new int[36];
        Arrays.fill(piantaggine, 0);
        for(int i = 9; i < 28; i++){
            piantaggine[i] = 1;
        }
        for(int i = 12; i < 26; i++){
            piantaggine[i] = 2;
        }
        for(int i = 14; i < 24; i++){
            piantaggine[i] = 3;
        }
        pollini.put("Piantaggine", piantaggine);


        int[] assenzio = new int[36];
        Arrays.fill(assenzio, 0);
        for(int i = 19; i < 23; i++){
            assenzio[i] = 1;
        }
        assenzio[22] = 2;
        assenzio[26] = 2;
        pollini.put("Assenzio", assenzio);

        System.out.println(Arrays.toString(nocciolo));
        printPollini();
    }

    /**
     * Print utility for pollini
     */
    public void printPollini(){
        for (Map.Entry<String, int[]> entry : pollini.entrySet()) {
            System.out.println(entry.getKey() + ": " + Arrays.toString(entry.getValue()));
        }
    }

    /**
     * Getter for pollini
     * @return
     */
    public Map<String, int[]> getPollini() {
        return pollini;
    }

    /**
     * Based on pollen name, month of the symptoms, return pollen concentration.
     * @param polline
     * @param month
     * @return
     */
    public int evaluateMonth(String polline, int month){
        int[] listaMesi = pollini.get(polline);
        int fineMese = listaMesi[month * 3 - 1]; //fine mese (3/3)
        int metaMese = listaMesi[month * 3 - 2]; //metà mese (2/3)
        int inizioMese = listaMesi[month * 3 - 3]; //inizio mese (1/3)
        int media = (inizioMese + metaMese + fineMese)/3;
        getPresenza(media);
        return month;
    }

    /**
     * Based on pollen name, month of the symptoms, return pollen concentration.
     * @param polline
     * @param month
     * @return
     */
    public String evaluateMonthString(String polline, int month){
        int[] listaMesi = pollini.get(polline);
        int fineMese = listaMesi[month * 3 - 1]; //fine mese (3/3)
        int metaMese = listaMesi[month * 3 - 2]; //metà mese (2/3)
        int inizioMese = listaMesi[month * 3 - 3]; //inizio mese (1/3)
        int media = (inizioMese + metaMese + fineMese)/3;
        getPresenza(media);
        return getPresenza(media);
    }

    /**
     * Based on pollen name, month and day of the symptoms, return pollen concentration.
     * @param polline
     * @param month
     * @param day
     * @return
     */
    public int evaluateMonth(String polline, int month, int day){
        int[] listaMesi = pollini.get(polline);
        int evaluation = 0;
        if(day >= 1 && day <= 10)
            evaluation = listaMesi[month * 3 - 3]; //inizio mese (1/3)
        if(day >= 11 && day <= 20)
            evaluation = listaMesi[month * 3 - 2]; //metà mese (2/3)
        if(day >= 21 && day <= 31)
            evaluation = listaMesi[month * 3 - 1]; //fine mese (3/3)

        getPresenza(evaluation);
        return evaluation;
    }

    /**
     * Based on pollen name, month and day of the symptoms, return pollen concentration.
     * @param polline
     * @param month
     * @param day
     * @return
     */
    public String evaluateMonthString(String polline, int month, int day){
        int[] listaMesi = pollini.get(polline);
        int evaluation = 0;
        if(day >= 1 && day <= 10)
            evaluation = listaMesi[month * 3 - 3]; //inizio mese (1/3)
        if(day >= 11 && day <= 20)
            evaluation = listaMesi[month * 3 - 2]; //metà mese (2/3)
        if(day >= 21 && day <= 31)
            evaluation = listaMesi[month * 3 - 1]; //fine mese (3/3)

        getPresenza(evaluation);
        return getPresenza(evaluation);
    }

    /**
     * Converts the grade in a string.
     * 0 = concentrazione nulla
     * 1 = concentrazione bassa
     * 2 = concentrazione media
     * 3 = concentrazione alta
     * @param presenza
     * @return
     */
    public String getPresenza(int presenza){
        String presenzaString = new String();
        /*
        switch (presenza) {
            case 0:  presenzaString = "Concentrazione pollinica nulla";
                break;
            case 1:  presenzaString = "Concentrazione pollinica bassa";
                break;
            case 2:  presenzaString = "Concentrazione pollinica media";
                break;
            case 3:  presenzaString = "Concentrazione pollinica alta";
                break;
            default: presenzaString = "Concentrazione pollinica non reperibile";
                break;
        }
        */
        if(presenza >= 0 && presenza < 0.50){
            presenzaString = "Concentrazione pollinica nulla";
        }
        if(presenza >= 0.50 && presenza < 1.50){
            presenzaString = "Concentrazione pollinica bassa";
        }
        if(presenza >= 1.50 && presenza < 2.50){
            presenzaString = "Concentrazione pollinica media";
        }
        if(presenza >= 2.50 && presenza < 3.50){
            presenzaString = "Concentrazione pollinica alta";
        }
        System.out.println(presenzaString);
        return  presenzaString;
    }
}
