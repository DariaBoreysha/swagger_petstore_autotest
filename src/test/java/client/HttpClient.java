package client;

import exceptions.AtHttpClientException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;

public class HttpClient {

    private Delete httpDelete;
    private Post httpPost;
    private Get httpGet;

    public HttpClient() {
        httpDelete = new Delete();
        httpPost = new Post();
        httpGet = new Get();
    }

    public Get methodGet() {
        return httpGet;
    }

    public Post methodPost() {
        return httpPost;
    }

    public Delete methodDelete() {
        return httpDelete;
    }

    public static InputStream extractHttpEntityContent(HttpResponse response) {
        HttpEntity entity = extractHttpEntityFromResponse(response);
        InputStream entityContentStream;
        try {
            entityContentStream = entity.getContent();
        } catch (IOException e) {
            throw new AtHttpClientException(e);
        }
        return entityContentStream;
    }

    private static HttpEntity extractHttpEntityFromResponse(HttpResponse response) {
        return response.getEntity();
    }
}
