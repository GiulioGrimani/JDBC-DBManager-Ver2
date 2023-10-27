
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * La classe contiene un campo statico privato di tipo <b>Properties</b>
 * che parametrizza la connessione al database caricando una serie di parametri
 * dal file config.properties posto nello stesso package.
*/

public class DBTools {
	private static Properties properties = getProperties();

	public static Connection getConnection() {
		Connection connection = null;
		String endpoint = properties.getProperty("endpoint");
		String port = properties.getProperty("port");
		String schema = properties.getProperty("schema");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");

		try {

			connection = DriverManager.getConnection("jdbc:mysql://" + endpoint + ":" + port + "/" + schema, user,
					password);
			return connection;
		} catch (Exception e) {
			System.out.println("Connection Failed! Check output console");
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
				System.out.println("Connection closed !!");
			} else {
				System.out.println("Connection was null!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Memorizza i parametri definiti nel file <i>config.properties</i> all'interno
	 * del campo statico di tipo <b>Properties</b> della classe.
	 */
	private static Properties getProperties() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = DBTools.class.getResourceAsStream("config.properties");
			// load a properties file
			prop.load(input);
			input.close();
			return prop;

		} catch (IOException ex) {
			ex.printStackTrace();
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
