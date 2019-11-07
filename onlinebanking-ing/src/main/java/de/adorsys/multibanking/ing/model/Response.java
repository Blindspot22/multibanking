package de.adorsys.multibanking.ing.model;

import de.adorsys.multibanking.ing.http.ResponseHeaders;

import java.util.function.Function;

public class Response<T> {
    private final int statusCode;
    private final T body;
    private final ResponseHeaders headers;

    public Response(int statusCode, T body, ResponseHeaders headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getBody() {
        return body;
    }

    public ResponseHeaders getHeaders() {
        return headers;
    }

    public <U> Response<U> map(Function<? super T, ? extends U> bodyMapper) {
        return new Response<>(statusCode, bodyMapper.apply(body), headers);
    }
}
