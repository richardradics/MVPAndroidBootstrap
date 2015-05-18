package com.richardradics.cleanaa.repository.api;

import com.richardradics.cleanaa.app.AppConfig;
import com.richardradics.cleanaa.domain.City;
import com.richardradics.cleanaa.exception.GetCitiesException;
import com.richardradics.cleanaa.util.AppRobolectricRunner;
import com.richardradics.commons.test.TestHelper;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowLog;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Created by radicsrichard on 15. 05. 18..
 */
@RunWith(AppRobolectricRunner.class)
@PrepareForTest({OpenWeatherClient.class})
public class OpenWeatherClientTest {

    MockWebServer mockWebServer;
    OpenWeatherClient openWeatherClient;

    @Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;
        mockWebServer = new MockWebServer();
        mockWebServer.start(9999);
        TestHelper.setFinalStatic(OpenWeatherClient.class.getField("ENDPOINT"), mockWebServer.getUrl("/").toString());
        openWeatherClient = OpenWeatherClient_.getInstance_(RuntimeEnvironment.application);
    }

    @Test
    public void testWeatherClient() throws Exception {

        mockWebServer.enqueue(new MockResponse().setHeader("Server", "mockwebserver").setBody(TestHelper.getStringFromResource("openweatherresponse.txt")));

        List<City> cities = openWeatherClient.getCities(AppConfig.TEST_DATA.BP_LATITUDE, AppConfig.TEST_DATA.BP_LONGITUDE, 25);
        assertThat(openWeatherClient).isNotNull();
        assertThat(cities).isNotEmpty();
        TestHelper.printCollection(cities);
    }

    @Test
    public void testWeatherClientWithRetry() throws Exception {


        mockWebServer.enqueue(new MockResponse().setHeader("Server", "mockwebserver")
                .setResponseCode(403));
        mockWebServer.enqueue(new MockResponse().setHeader("Server", "mockwebserver").setBody(TestHelper.getStringFromResource("openweatherresponse.txt")));

        List<City> cities = openWeatherClient.getCities(AppConfig.TEST_DATA.BP_LATITUDE, AppConfig.TEST_DATA.BP_LONGITUDE, 25);

        assertThat(openWeatherClient).isNotNull();
        assertThat(cities).isNotEmpty();
        TestHelper.printCollection(cities);
    }


    @Test(expected = GetCitiesException.class)
    public void testWeatherClientWithConnectionError() throws Exception {


        mockWebServer.enqueue(new MockResponse().setHeader("Server", "mockwebserver")
                .setResponseCode(403));
        mockWebServer.enqueue(new MockResponse().setHeader("Server", "mockwebserver")
                .setResponseCode(403));


        List<City> cities = openWeatherClient.getCities(AppConfig.TEST_DATA.BP_LATITUDE, AppConfig.TEST_DATA.BP_LONGITUDE, 25);

    }

    @After
    public void afterTest() throws Exception {
        mockWebServer.shutdown();
    }

}