package com.richardradics.core.retrofit;

import com.richardradics.core.error.NetworkErrorHandler;
import com.richardradics.core.navigation.Navigator;
import com.squareup.okhttp.OkHttpClient;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.net.CookieHandler;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by radicsrichard on 15. 04. 28..
 */
@EBean
public class BaseRetrofitClient {

    @Bean
    protected Navigator navigator;


    protected static OkHttpClient okHttpClient;

    @Bean
    protected BaseRetryHandler defaultRetryHandler;

    @Bean
    protected ConnectivityAwareUrlClient connectivityAwareUrlClient;

    @Bean
    protected NetworkErrorHandler networkErrorHandler;

    public <T> T initRestAdapter(String ENDPOINT, Class<T>  restInterface, BaseRetryHandler baseRetryHandler) {
        okHttpClient = new OkHttpClient();
        okHttpClient.setCookieHandler(CookieHandler.getDefault());
        okHttpClient.setConnectTimeout(10, TimeUnit.MINUTES);
        connectivityAwareUrlClient.setWrappedClient(new OkClient(okHttpClient));
        connectivityAwareUrlClient.setRetryHandler(baseRetryHandler);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setClient(connectivityAwareUrlClient)
                        //  .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(restInterface);
    }

    public <T> T initRestAdapter(String ENDPOINT, Class<T> restInterface) {
        return initRestAdapter(ENDPOINT, restInterface, defaultRetryHandler);
    }


}
