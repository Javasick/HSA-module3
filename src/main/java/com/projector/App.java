package com.projector;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class.getSimpleName());
    public static final String TRACKING_ID = "G-6HS77F82QK";
    public static final String API_SECRET = "5Vc_-1gZQaurQURM6DtWPA";

    public static void main(String[] args) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(getUri());
        request.setEntity(getPayload());
        HttpResponse response = client.execute(request);
        log.info("Event tracked, status code {}", response.getStatusLine().getStatusCode());
    }

    private static URI getUri() {
        URIBuilder builder = new URIBuilder();
        builder
                .setScheme("https")
                .setHost("www.google-analytics.com")
                .setPath("/mp/collect")
                .addParameter("measurement_id", TRACKING_ID) // Tracking ID / Property ID.
                .addParameter("api_secret", API_SECRET);
        URI uri = null;
        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Problem building URI", e);
        }
        return uri;
    }

    private static BasicHttpEntity getPayload() {
        BasicHttpEntity entity = new BasicHttpEntity();
        String payload = new JSONObject()
                .put("client_id", "123.456")
                .put("events", new JSONObject[]{
                        new JSONObject()
                                .put("name", "AwesomeEvent")
                })
                .toString();
        log.info(payload);
        entity.setContent(new ByteArrayInputStream((payload.getBytes(StandardCharsets.UTF_8))));
        return entity;
    }
}
