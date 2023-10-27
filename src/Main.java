import java.util.List;

/*
 * Rendiamo le cose più Object Oriented:
 * 1. creiamo una classe che si occupa soltanto
 * di gestire la connessione al database
 * 2. creiamo il model mappando le entità sul DB
 * 3. creiamo una classe che si occupa di fare query
 * 4. usiamo il main per usare la classe che fa query
 */
public class Main {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {

		QueryExecutor qe = new QueryExecutor();

		List<Actor> actors = qe.getActorsInFilm("ACADEMY DINOSAUR");
		print(actors);

//		print(qe.getActorsByFirstNameOrLastName("Penelope", "Guiness"));

//		print(qe.getAllActorsWhereNameStartsWith("PEN"));

//		print(qe.getThreeMostPopularActors());

	}

	private static void print(List<?> myList) {
		for (Object o : myList) {
			System.out.println(o);
			System.out.println();
		}
		System.out.println();
	}

}
