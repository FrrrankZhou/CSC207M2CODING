package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final static String SUCCESS = "success";
    private final static String FAILURE = "404";
    private final OkHttpClient client = new OkHttpClient().newBuilder().build();
    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        // TODO Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.
        // return statement included so that the starter code can compile and run.

        Request request =  new Request.Builder()
                //"https://dog.ceo/api/breed/*/list"
                .url(String.format("https://dog.ceo/api/breed/%s/list", breed))
                .build();
        final Response response;
        final JSONObject received;
        try {
            response = client.newCall(request).execute();
            received = new JSONObject(response.body().string());

            if (received.getString("status").equals(SUCCESS)){
                JSONArray subBreeds =received.getJSONArray("message");
                ArrayList<String> subBreedsList = new ArrayList<>();

                for (int i=0; i<subBreeds.length(); i++) {
                    subBreedsList.add(subBreeds.getString(i));
                }
                return subBreedsList;
            }else {
                throw new BreedNotFoundException(breed);
            }
        } catch (IOException e) {
            throw new BreedNotFoundException(breed);
        }
    }
}