package utility;

import rinocitologia.Patient;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteHelper {

    private static String fileName;

    public SqliteHelper(String fileName){
        this.fileName = fileName;
    }

    //public static void createNewDatabase(String fileName) {
    public static void createNewDatabase() {
        String url = "jdbc:sqlite:"  + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the test database
     *
     */
    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + fileName;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS pazienti (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	surname text NOT NULL,\n"
                + "	sex text,\n"
                + "	codicefiscale text,\n"
                + "	year INTEGER,\n"
                + "	day INTEGER,\n"
                + "	month INTEGER\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + fileName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row into the pazienti table
     *
     * @param name
     * @param surname
     */
    public void insertPatient(String name, String surname) {
        String sql = "INSERT INTO pazienti(name,surname) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, surname);


            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Update data of a paziente specified by the surname
     *
     * @param surname
     * @param name name of the warehouse
     * @param cf
     * @param year
     * @param month
     * @param day
     */
    public void updatePatient(String oldSurname, String name, String surname, String cf, int year, int month, int day, String sex) {
        String sql = "UPDATE pazienti SET codicefiscale = ? , "
                + "year = ? ,"
                + "month = ? ,"
                + "day = ? ,"
                + "name = ? ,"
                + "surname = ? ,"
                + "sex = ? "
                + "WHERE surname = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, cf);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            pstmt.setInt(4, day);
            pstmt.setString(5, name);
            pstmt.setString(6, surname);
            pstmt.setString(7, sex);

            pstmt.setString(8, oldSurname);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String[]> selectAllCfNotNull(){
        String sql = "select name, surname, codicefiscale, sex, year, month, day\n" +
                "from pazienti\n" +
                "where codicefiscale IS NOT NULL;\n";

        List<String[]> listOfPazienti = new ArrayList<String[]>();
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs  = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                String[] paziente = new String[7];

                for (int i = 0; i < paziente.length; i++) {
                    paziente[i] = rs.getString(i + 1);
                }
                listOfPazienti.add(paziente);

                /*
                System.out.println(rs.getString("name") +  "\t" +

                        rs.getString("surname") + "\t" +
                        rs.getString("sex") + "\t" +
                        rs.getString("codicefiscale") + "\t" +
                        rs.getInt("year") + "\t" +
                        rs.getInt("month") + "\t" +
                        rs.getInt("day"));
                */

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listOfPazienti;
    }


}
