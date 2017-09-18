package dk.developer.testing;

import dk.developer.clause.Get;
import dk.developer.clause.Post;
import dk.developer.clause.With;
import dk.developer.validation.ValidationExceptionMapper;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;

public class RestServiceTestHelper {
    public static Get<String, Result> from(Class<?> type) {
        return url -> {
            MockHttpRequest request = get(url);
            return result(type, request);
        };
    }

    private static Result result(Class<?> type, MockHttpRequest request) {
        Dispatcher dispatcher = createDispatcher(type);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        return new Result(response);
    }

    public static With<String, Post<String, Result>> to(Class<?> type) {
        return content -> url -> {
            MockHttpRequest request = post(url)
                    .content(content.getBytes())
                    .contentType(MediaType.APPLICATION_JSON_TYPE);
            return result(type, request);
        };
    }

    private static Dispatcher createDispatcher(Class<?> type) {
        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
        POJOResourceFactory noDefaults = new POJOResourceFactory(type);
        dispatcher.getRegistry().addResourceFactory(noDefaults);
        registerProviders(dispatcher);
        return dispatcher;
    }

    private static void registerProviders(Dispatcher dispatcher) {
        ResteasyProviderFactory providerFactory = dispatcher.getProviderFactory();
        providerFactory.registerProvider(ValidationExceptionMapper.class);
    }

    private static MockHttpRequest get(String url) {
        try {
            return MockHttpRequest.get(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static MockHttpRequest post(String url) {
        try {
            return MockHttpRequest.post(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
