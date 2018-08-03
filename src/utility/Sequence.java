package utility;

import java.io.*;

public class Sequence {

    private String PATH =  System.getProperty("user.home") + File.separator + "data" + File.separator + "sqc.txt";

    public String getPATH() {return PATH;}

    public void writeSeq(int number) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(PATH));
        writer.write(Integer.toString(number));
        //System.out.println(PATH);
        writer.close();
    }

    public int readSeq() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(PATH));
        String line = reader.readLine();
        reader.close();
        return Integer.parseInt(line);
    }

    public static void main(String[] args){
        Sequence seq = new Sequence();
        try {
            File f = new File(seq.PATH);
            int number = 0;

            if (f.exists()){
                number = seq.readSeq() + 1;
                seq.writeSeq(number);
            } else {
                seq.writeSeq(number);

            }
            System.out.println(seq.readSeq());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
