package utility;


public class CodiceFiscale {

    private String CF;
    private int day;
    private int month;
    private int year;
    private String sex;

    /**
     * Initializes the class instantiating attributes from the string "CF" (aka Codice Fiscale)
     * @param CF
     */
    public CodiceFiscale(String CF){
        this.CF = CF;
        this.year = Integer.parseInt(CF.substring(6,8));
        this.month = extractMonth(CF.substring(8,9));
        int provisoryDay = Integer.parseInt(CF.substring(9,11));
        this.sex = checkSex(provisoryDay);
        this.day = generateDay(provisoryDay);

    }

    /**
     * Getter for CF
     * @return
     */
    public String getCF() {
        return CF;
    }

    /**
     * Setter for CF
     * @param CF
     */
    public void setCF(String CF) {
        this.CF = CF;
    }

    /**
     * Getter for Day of birth
     * @return
     */
    public int getDay() {
        return day;
    }

    /**
     * Setter for Day of birth
     * @param day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Getter for Month of birth
     * @return
     */
    public int getMonth() {
        return month;
    }

    /**
     * Setter for Month of birth
     * @param month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Getter for Year of birth
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     * Setter for Year of birth
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Getter for Sex
     * @return
     */
    public String getSex() {
        return sex;
    }

    /**
     * Setter for Sex
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Utility to extract Month of birth from CF (aka Codice Fiscale)
     * @param month
     * @return
     */
    public int extractMonth(String month){
        int risultato;
        switch(month){
            case "A":
                risultato = 1;
                break;
            case "B":
                risultato = 2;
                break;
            case "C":
                risultato = 3;
                break;
            case "D":
                risultato = 4;
                break;
            case "E":
                risultato = 5;
                break;
            case "H":
                risultato = 6;
                break;
            case "L":
                risultato = 7;
                break;
            case "M":
                risultato = 8;
                break;
            case "P":
                risultato = 9;
                break;
            case "R":
                risultato = 10;
                break;
            case "S":
                risultato = 11;
                break;
            case "T":
                risultato = 12;
                break;
            default:
                risultato = 0;
                break;
        }
        return risultato;
    }

    /**
     * Utility to extract Sex from CF (aka Codice Fiscale).
     * If "day of birth" is less than 31 then Male, else Female.
     * @param day
     * @return
     */
    private String checkSex(int day){
        String sex;
        if (day > 0 && day < 31)
            sex = "M";
        else
            sex = "F";
        return sex;
    }

    /**
     * If sex is male (day < 31) , day is correct.
     * Else is day - 40.
     * @param provisoryDay
     * @return
     */
    private int generateDay(int provisoryDay){
        if(provisoryDay > 31){
            provisoryDay = provisoryDay - 40;
        }
        return provisoryDay;
    }

    @Override
    public String toString() {
        return "CodiceFiscale{" +
                "CF='" + CF + '\'' +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", sex='" + sex + '\'' +
                '}';
    }

}