package launcher.services.interfaces;

import java.util.Optional;
import java.util.Set;

public interface Search {

	Optional<Set<String>> search(String word, Long counter);
}
