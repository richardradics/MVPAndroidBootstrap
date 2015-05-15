package com.richardradics.core.retrofit;

import com.richardradics.core.util.CoreRobolectricRunner;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RuntimeEnvironment;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Path;


import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Created by radicsrichard on 15. 05. 15..
 */
@RunWith(CoreRobolectricRunner.class)
@PrepareForTest(HttpMethod.class)
public class ConnectivityAwereUrlClientTest {

    interface SodaService {
        @GET("/{brand}")
        Object cola(@Path("brand") String brand);
    }

    ConnectivityAwareUrlClient connectivityAwareUrlClient;
    SodaService sodaService;
    MockWebServer mockWebServer;

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.play();


        connectivityAwareUrlClient = ConnectivityAwareUrlClient_.getInstance_(RuntimeEnvironment.application);
        connectivityAwareUrlClient.setWrappedClient(new OkClient(new OkHttpClient()));
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(connectivityAwareUrlClient)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(mockWebServer.getUrl("/").toString()).build();
        sodaService = restAdapter.create(SodaService.class);
    }

    @Test
    public void testMockWebserver() throws Exception {


        assertThat(connectivityAwareUrlClient).isNotNull();
        mockWebServer.enqueue(new MockResponse()
                .setBody("\"You got the right one, baby\""));
        assertThat(sodaService.cola("pepsi")).isEqualTo("You got the right one, baby");

    }


}
