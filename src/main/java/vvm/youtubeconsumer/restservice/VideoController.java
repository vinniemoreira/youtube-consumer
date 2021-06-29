package vvm.youtubeconsumer.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;

@RestController
public class VideoController {
    // You need to set this value for your code to compile.
    // For example: ... DEVELOPER_KEY = "YOUR ACTUAL KEY";
    private static final String DEVELOPER_KEY = "API_KEY";

    private static final String APPLICATION_NAME = "Youtube Consumer";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null).setApplicationName(APPLICATION_NAME).build();
    }

    // TODO: Extract response to object. Save entity on database. Validate input,
    // etc
    @GetMapping("/searches")
    public String searches(@RequestParam(value = "q", defaultValue = "") String query,
            @RequestParam(value = "maxResults", defaultValue = "5") long maxResults)
            throws GeneralSecurityException, IOException, GoogleJsonResponseException {

        YouTube youtubeService = getService();
        // Define and execute the API request
        YouTube.Search.List request = youtubeService.search()
                .list(new ArrayList<String>(Collections.nCopies(1, "snippet")));

        SearchListResponse response = request.setKey(DEVELOPER_KEY).setMaxResults(maxResults).setQ(query).execute();

        return response.toPrettyString();
    }
}