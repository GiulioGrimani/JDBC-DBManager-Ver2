
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QueryExecutor {

	// Select * from actor
	public List<Actor> getAllActors() {

		Connection connection = DBTools.getConnection();
		List<Actor> result = new ArrayList<Actor>();

		String query = "SELECT * FROM actor";

		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Actor actor = getFullActorFromResultSet(rs);
				result.add(actor);
			}
			DBTools.closeConnection(connection);
			return result;

		} catch (Exception e) {
			System.out.println("NON FUNZIONA LA QUERY!");
			System.out.println(e.getMessage());
			DBTools.closeConnection(connection);
			return null;
		}
	}

	/*
	 * SELECT first_name, last_name FROM actor JOIN film_actor on actor.actor_id =
	 * film_actor.actor_id JOIN film on film.film_id = film_actor.film_id WHERE
	 * title = "INPUT UTENTE";
	 */
	public List<Actor> getActorsInFilm(String title) {

		Connection connection = DBTools.getConnection();
		List<Actor> result = new ArrayList<Actor>();

		String query = "SELECT actor.first_name, actor.last_name FROM film_actor INNER JOIN film ON film_actor.film_id = film.film_id INNER JOIN actor ON film_actor.actor_id = actor.actor_id WHERE film.title = ?;";

		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, title);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Actor actor = new Actor();
				actor.setFirstName(rs.getString("first_name"));
				actor.setLastName(rs.getString("last_name"));
				result.add(actor);
			}

			DBTools.closeConnection(connection);
			return result;

		} catch (SQLException e) {
			System.out.println("NON FUNZIONA LA QUERY!");
			System.out.println(e.getMessage());
			DBTools.closeConnection(connection);
			return null;
		}

	}

	public List<Actor> getThreeMostPopularActors() {

		Connection connection = DBTools.getConnection();
		List<Actor> result = new ArrayList<Actor>();

		String query = "SELECT actor.first_name, actor.last_name, COUNT(film.film_id) AS numFilm FROM film_actor INNER JOIN film ON film_actor.film_id = film.film_id INNER JOIN actor ON film_actor.actor_id = actor.actor_id GROUP BY actor.actor_id ORDER BY numFilm DESC LIMIT 3;";

		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Actor actor = new Actor();
				actor.setFirstName(rs.getString("first_name"));
				actor.setLastName(rs.getString("last_name"));
				result.add(actor);
			}

			DBTools.closeConnection(connection);
			return result;

		} catch (SQLException e) {
			System.out.println("NON FUNZIONA LA QUERY!");
			System.out.println(e.getMessage());
			DBTools.closeConnection(connection);
			return null;
		}
	}

	public List<Actor> getActorsByFirstNameOrLastName(String firstName, String lastName) {

		Connection connection = DBTools.getConnection();
		List<Actor> result = new ArrayList<Actor>();

		String query = "Select * from actor where first_name = ? || last_name = ?;";

		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, firstName);
			ps.setString(2, lastName);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Actor actor = getFullActorFromResultSet(rs);
				result.add(actor);
			}

			DBTools.closeConnection(connection);
			return result;

		} catch (SQLException e) {
			System.out.println("NON FUNZIONA LA QUERY!");
			System.out.println(e.getMessage());
			DBTools.closeConnection(connection);
			return null;
		}
	}

	public List<Actor> getAllActorsWhereNameStartsWith(String prefix) {

		Connection connection = DBTools.getConnection();
		List<Actor> result = new ArrayList<Actor>();

		String query = "SELECT * FROM actor WHERE first_name LIKE ?;";

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, prefix + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Actor actor = getFullActorFromResultSet(rs);
				result.add(actor);
			}
			DBTools.closeConnection(connection);
			return result;

		} catch (Exception e) {
			System.out.println("NON FUNZIONA LA QUERY!");
			System.out.println(e.getMessage());
			DBTools.closeConnection(connection);
			return null;
		}
	}

	private Actor getFullActorFromResultSet(ResultSet rs) throws SQLException {
		Actor a = new Actor();
		a.setId(rs.getInt("actor_id"));
		a.setFirstName(rs.getString("first_name"));
		a.setLastName(rs.getString("last_name"));
		a.setLastUpdate(rs.getTimestamp("last_update"));
		return a;
	}

	public List<Film> getAllFilms() {
		Connection connection = DBTools.getConnection();

		List<Film> films = new ArrayList<Film>();

		String query = "Select * from Film";

		try {
			Statement st = connection.createStatement();
			ResultSet rs = null;
			rs = st.executeQuery(query);

			while (rs.next()) {

				films.add(createFilm(rs));

			}
			DBTools.closeConnection(connection);
			return films;

		} catch (Exception e) {
			System.out.println("NON FUNZIONA LA QUERY!");
			System.out.println(e.getMessage());
			DBTools.closeConnection(connection);
			return null;
		}

	}

	public List<Film> getFilmByActorAndReleaseYear(String firstName, String lastName, int year) {
		Connection connection = DBTools.getConnection();

		List<Film> result = new ArrayList<Film>();

		String query = "SELECT film.* FROM film_actor INNER JOIN film ON film_actor.film_id = film.film_id INNER JOIN actor ON film_actor.actor_id = actor.actor_id WHERE film.release_year = ? AND actor.first_name = ? AND actor.last_name = ?;";

		ResultSet rs = null;

		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setInt(1, year);
			ps.setString(2, firstName);
			ps.setString(3, lastName);

			rs = ps.executeQuery();

			while (rs.next()) {
				result.add(createFilm(rs));
			}

			DBTools.closeConnection(connection);
			return result;

		} catch (SQLException e) {
			System.out.println("NON FUNZIONA LA QUERY!");
			System.out.println(e.getMessage());
			DBTools.closeConnection(connection);
			return null;
		}

	}

	private Film createFilm(ResultSet rs) throws SQLException {
		int filmId = rs.getInt("film_id");
		String title = rs.getString("title");
		String description = rs.getString("description");
		int releaseYear = rs.getInt("release_year");
		int languageId = rs.getInt("language_id");
		int originalLanguageId = rs.getInt("original_language_id");
		int rentalDuration = rs.getInt("rental_duration");
		double rentalRate = rs.getDouble("rental_rate");
		int length = rs.getInt("length");
		double replacementCost = rs.getDouble("replacement_cost");
		Film.Rating rating = Film.Rating.getRatingByDescription(rs.getString("rating"));
		Set<String> specialFeatures = new HashSet<>(Arrays.asList(rs.getString("special_features").split(",")));
		Timestamp lastUpdate = rs.getTimestamp("last_update");

		Film film = new Film();
		film.setFilmId(filmId);
		film.setTitle(title);
		film.setDescription(description);
		film.setReleaseYear(releaseYear);
		film.setLanguageId(languageId);
		film.setOriginalLanguageId(originalLanguageId);
		film.setRentalRate(rentalRate);
		film.setRentalDuration(rentalDuration);
		film.setLength(length);
		film.setReplacementCost(replacementCost);
		film.setRating(rating);
		film.setSpecialFeatures(specialFeatures);
		film.setLastUpdate(lastUpdate);
		return film;
	}

}
