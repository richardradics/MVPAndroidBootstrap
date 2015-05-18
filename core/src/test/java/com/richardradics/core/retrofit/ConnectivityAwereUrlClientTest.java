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
import org.robolectric.shadows.ShadowLog;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Path;


import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;


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
    BaseRetryHandler baseRetryHandler;
    MockWebServer mockWebServer;

    @Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;

        mockWebServer = new MockWebServer();
        mockWebServer.play();

        baseRetryHandler = BaseRetryHandler_.getInstance_(RuntimeEnvironment.application);
        connectivityAwareUrlClient = ConnectivityAwareUrlClient_.getInstance_(RuntimeEnvironment.application);
        connectivityAwareUrlClient.setWrappedClient(new OkClient(new OkHttpClient()));
        connectivityAwareUrlClient.setRetryHandler(baseRetryHandler);


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

    @Test
    public void test400Retry() throws Exception {

        assertThat(connectivityAwareUrlClient).isNotNull();
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400));
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400));
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400));

        boolean isThrown = false;
        try {

            sodaService.cola("pepsi");
            fail("shoud not reach this");
        }catch (RetrofitError error){
            if(error.getCause() instanceof ConnectionError){
                isThrown = true;
            }else{
                fail("shoud not reach this");
            }
        }

        assertThat(isThrown).isTrue();
    }

    @Test
    public void test401Retry() throws Exception {

        assertThat(connectivityAwareUrlClient).isNotNull();
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(401));
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(401));
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(401));

        boolean isThrown = false;
        try {

            sodaService.cola("pepsi");
            fail("shoud not reach this");
        }catch (RetrofitError error){
            if(error.getCause() instanceof ConnectionError){
                isThrown = true;
            }else{
                fail("shoud not reach this");
            }
        }

        assertThat(isThrown).isTrue();
    }

    @Test
    public void test500Retry() throws Exception {

        assertThat(connectivityAwareUrlClient).isNotNull();
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500));
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500));
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500));

        boolean isThrown = false;
        try {

            sodaService.cola("pepsi");
            fail("shoud not reach this");
        }catch (RetrofitError error){
            if(error.getCause() instanceof ConnectionError){
                isThrown = true;
            }else{
                fail("shoud not reach this");
            }
        }

        assertThat(isThrown).isTrue();
    }

    @Test
    public void test403Retry() throws Exception {

        assertThat(connectivityAwareUrlClient).isNotNull();
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(403));
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(403));
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(403));

        boolean isThrown = false;
        try {

            sodaService.cola("pepsi");
            fail("shoud not reach this");
        }catch (RetrofitError error){
            if(error.getCause() instanceof ConnectionError){
                isThrown = true;
            }else{
                fail("shoud not reach this");
            }
        }

        assertThat(isThrown).isTrue();
    }

    @Test
    public void test403Success1attempt() throws Exception {

        assertThat(connectivityAwareUrlClient).isNotNull();
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(403));

        mockWebServer.enqueue(new MockResponse()
                .setBody("\"You got the right one, baby\""));

        assertThat(sodaService.cola("pepsi")).isEqualTo("You got the right one, baby");

    }

    @Test
    public void test403Success3attempt() throws Exception {

        assertThat(connectivityAwareUrlClient).isNotNull();
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(403));

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(403));

        mockWebServer.enqueue(new MockResponse()
                .setBody("\"You got the right one, baby\""));

        assertThat(sodaService.cola("pepsi")).isEqualTo("You got the right one, baby");

    }


    @Test
    public void test404Retry() throws ConnectionError {

        assertThat(connectivityAwareUrlClient).isNotNull();
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404));
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404));
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404));

        boolean isThrown = false;
        try {

            sodaService.cola("pepsi");
            fail("shoud not reach this");
        }catch (RetrofitError error){
            if(error.getCause() instanceof ConnectionError){
                isThrown = true;
            }else{
                fail("shoud not reach this");
            }
        }

        assertThat(isThrown).isTrue();

    }

    @Test
    public void testSocketTimeOutRetry() throws Exception {

        assertThat(connectivityAwareUrlClient).isNotNull();
        mockWebServer.enqueue(new MockResponse()
                .setBody("\"You got the right one, baby\""));
        assertThat(sodaService.cola("pepsi")).isEqualTo("You got the right one, baby");

    }

}
