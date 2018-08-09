package utility;

import java.io.*;

public class Sequence {

    private String PATH =  System.getProperty("user.home") + File.separator + "data" + File.separator + "sqc.txt";

    public String getPATH() {return PATH;}

    public Sequence(){
        final File folderData = new File(System.getProperty("user.home") + File.separator + "data");
        if(!folderData.exists() && !folderData.mkdir()) {
            //failed to create the folder, probably exit
            throw new RuntimeException("Failed to create save directory.");
        }
    }

    public void writeSeq(int number) throws IOException {
        File yourFile = new File(PATH);
        yourFile.createNewFile(); // if file already exists will do nothing
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

}
