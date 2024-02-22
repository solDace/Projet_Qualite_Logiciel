package ca.ulaval.glo4002.game.interfaces.rest.filter;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.MultivaluedMap;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class CORSFilterTest {

    @Mock
    ContainerRequestContext request;
    @Mock
    ContainerResponseContext response;
    @Mock
    MultivaluedMap<String, Object> headers;

    private CORSFilter filter;

    @BeforeEach
    public void setUp() {
        filter = new CORSFilter();
        Mockito.when(response.getHeaders()).thenReturn(headers);
    }

    @Test
    public void whenFilterResponse_thenResponseHeadersAllowAccessControlIsUpdated() throws IOException {

        filter.filter(request, response);

        Mockito.verify(headers).add(eq("Access-Control-Allow-Origin"), anyString());
        Mockito.verify(headers).add(eq("Access-Control-Allow-Headers"), anyString());
        Mockito.verify(headers).add(eq("Access-Control-Allow-Credentials"), anyString());
        Mockito.verify(headers).add(eq("Access-Control-Allow-Methods"), anyString());
    }
}
