package client;

import exceptions.AtHttpClientException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;

public class HttpClient {

    /**
     * Вынести методы по созданию запросов, добавлению параметров, хэдеров и т.д. в отдельные классы,
     * а тут оставить только sendGetRequest, sendPostRequest, sendDeleteRequest?
     * <p>
     * public HttpResponse sendGetRequest(){
     * return httpPost.send(*параметры*);
     * }
     * <p>
     * +Дописать исключения для каждого класса, которые бы наследовались от AtHttpClientException?
     */
    private Delete httpDelete;
    private Post httpPost;
    private Get httpGet;

    public HttpClient() {
        httpDelete = new Delete();
        httpPost = new Post();
        httpGet = new Get();
    }

    public Get getMethod() {
        return httpGet;
    }

    public Post postMethod() {
        return httpPost;
    }

    public Delete deleteMethod() {
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
