package client;

import exceptions.AtHttpClientException;
import methods.Delete;
import methods.Get;
import methods.Post;
import methods.Put;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;

public class HttpClient {

    private Delete methodDelete;
    private Post methodPost;
    private Get methodGet;
    private Put methodPut;

    public HttpClient() {
        methodDelete = new Delete();
        methodPost = new Post();
        methodGet = new Get();
        methodPut = new Put();
    }

    public Put methodPut() {
        return methodPut;
    }

    public Get methodGet() {
        return methodGet;
    }

    public Post methodPost() {
        return methodPost;
    }

    public Delete methodDelete() {
        return methodDelete;
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
