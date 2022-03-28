import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

public class requestGetOrder {
    public String postMessageGetOrder(String guid) throws IOException {
        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();
        final URL url = new URL("https://10.0.0.111:8086/rk7api/v0/xmlinterface.xml");

        System.out.println("************************************************************************************************************************");
        System.out.println("Хүсэлт илгээж буй захиалгын quid: => " + guid);

        String username = "http_user1";
        String password = "9";
        String auth = username + ":" + password;
        byte[] bytes = auth.getBytes(StandardCharsets.UTF_8);
        String base64Encoded = Base64.getEncoder().encodeToString(bytes);
        String authHeaderValue = "Basic " + new String(base64Encoded);
//		System.out.println("Base64 encoded text: => " + base64Encoded);

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", authHeaderValue);
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Content-Type", "application/xml; charset=UTF-8");

        conn.setDoOutput(true);
        conn.setConnectTimeout(500);

        // Send post request
        conn.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

        final String msg = "<RK7Query>\r\n" + " <RK7Command CMD=\"GetOrder\">\r\n" + "  <Order guid=\"" + guid
                + "\"/>\r\n" + " </RK7Command>\r\n" + "</RK7Query>";

        System.out.println("request body: => " + msg);

        wr.writeBytes(msg);
//		 send request
        wr.flush();
        // close
        wr.close();

        // read response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String str;
        while ((str = in.readLine()) != null) {
            content.append(str);
        }
        in.close();

        return content.toString();
    }


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

    public String getKdsStateDB(int visit) {
        String sql = "SELECT kdsState "
                + "FROM orders WHERE visit = " + visit + "";
        String kdsState = null;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                kdsState = rs.getString("kdsState");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return kdsState;
    }

    public void updateKdsState(String kdsStateDB, String kdsState, String qmsNumber) {
        String sql = "UPDATE orders SET kdsState = "+"'" + kdsState +"'"+ ""
                + "WHERE  qmsNumber = " + qmsNumber + " ";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("========" + e.getMessage());
        }
    }

}
