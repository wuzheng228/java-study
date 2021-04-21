package reflectionAndAnno.section73;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost:3306/java_test?useSSL=FALSE&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String username = "root";
    static final String password = "123";

    private static Connection instance = null;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class<?> clazz = Class.forName(DRIVER);
            connection = DriverManager.getConnection(url, username, password);

            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
