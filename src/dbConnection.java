import java.sql.*;

public class dbConnection {
     public static void createNewDatabase(String filename){
         String url = "jdbc:sqlite:C:\\UCS\\QMSVoice\\rk7_voice\\db\\" + filename;
         try (Connection conn = DriverManager.getConnection(url)){
             if (conn != null){
                 DatabaseMetaData meta = conn.getMetaData();
                 System.out.println("The driver name is " + meta.getDriverName());
                 System.out.println("A new database has been created.");
             }
         } catch (SQLException e){
             System.out.println(e.getMessage());
         }
     }

public  void createNewDb(){
         createNewDatabase("voice.db");
}

public static void createNewTable(){
         String url = "jdbc:sqlite:C:\\UCS\\QMSVoice\\rk7_voice\\db\\voice.db";

         String sql = "CREATE TABLE IF NOT EXISTS orders (\n"
                 + "	visit integer PRIMARY KEY,\n"
                 + "	qmsNumber text NOT NULL,\n"
                 + "	kdsState text ,\n"
                 + "	voiceState boolean\n"
                 + ");";

    try (Connection conn = DriverManager.getConnection(url);
              Statement stmt = conn.createStatement()) {
             stmt.execute(sql);
         } catch (SQLException e){
             System.out.println(e.getMessage());
         }

}

}