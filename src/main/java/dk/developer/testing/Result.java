package dk.developer.testing;

import org.jboss.resteasy.mock.MockHttpResponse;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.fromStatusCode;

public class Result {
    private final MockHttpResponse response;

    Result(MockHttpResponse response) {
        this.response = response;
    }

    public Response.Status status() {
        return fromStatusCode(response.getStatus());
    }

    public String content() {
        return response.getContentAsString();
    }
}
