package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private final HashMap<String, List<String>> subBreedsOf;
    private BreedFetcher fetcher;

    public CachingBreedFetcher(BreedFetcher fetcher) {
        subBreedsOf = new HashMap<>();
        this. fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        if (subBreedsOf.containsKey(breed)) {
            return subBreedsOf.get(breed);
        }else {
            callsMade++;
            List<String> subBreeds = fetcher.getSubBreeds(breed);
            subBreedsOf.put(breed, subBreeds);
            return subBreeds;
        }
    }

    public int getCallsMade() {
        return callsMade;
    }
}