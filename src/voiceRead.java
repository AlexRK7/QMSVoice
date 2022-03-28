import java.sql.*;

public class voiceRead {
    private Connection connect() {

        String url = "jdbc:sqlite:C:\\UCS\\QMSVoice\\rk7_voice\\db\\voice.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public String getGreaterThanZero() {
        String sql = "SELECT visit, qmsNumber, voiceState "
                + "FROM orders WHERE voiceState = 0 AND kdsState = 'ready' Limit 1";
        String qmsNum = null;
        Boolean voiceState = false;
        Integer visit;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                visit = rs.getInt("visit") ;
                 qmsNum = rs.getString("qmsNumber");
                voiceState = rs.getBoolean("voiceState");
                System.out.println("+/+/+/+/+/+/+/+??????????????????????????????????????????????????????????????????????" + qmsNum);
            }
        } catch (SQLException e) {
            System.out.println("Greater than zero error" + e.getMessage());
        }
        return qmsNum;
    }

    public void updateVoiceState() {
        String updQmsNumber = getGreaterThanZero();
        //Voice voice = new Voice();
       // String voiceQMS = voice.voiceP();
        System.out.println("+/+/+/+/+/+/+/+" + updQmsNumber);
        String sql =  "UPDATE orders SET voiceState = '1'  "
               + "WHERE  qmsNumber = " + updQmsNumber +" ";
        try (Connection conn = this.connect();
        PreparedStatement pstmt =  conn.prepareStatement(sql)){
            pstmt.executeUpdate();

        } catch (SQLException e){
            System.out.println("UpdateVoiceState error" + e.getMessage());
        }

    }

    }
