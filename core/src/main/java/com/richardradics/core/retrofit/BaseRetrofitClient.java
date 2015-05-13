package com.richardradics.core.retrofit;

import android.util.Log;

import com.richardradics.core.error.NetworkErrorHandler;
import com.richardradics.core.navigation.Navigator;
import com.squareup.okhttp.OkHttpClient;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.net.CookieHandler;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by radicsrichard on 15. 04. 28..
 */
@EBean
public class BaseRetrofitClient {

    @Bean
    protected Navigator navigator;

    protected int DEFAULT_RETRY_COUNT = 3;
    protected static OkHttpClient okHttpClient;

    protected AtomicInteger retries = new AtomicInteger(0);

    private static final String TAG = "BaseRetrofitClient";

    @Bean
    protected ConnectivityAwareUrlClient connectivityAwareUrlClient;

    @Bean
    protected NetworkErrorHandler networkErrorHandler;

    public <T> T initRestAdapter(String ENDPOINT, Class restInterface) {

        okHttpClient = new OkHttpClient();
        okHttpClient.setCookieHandler(CookieHandler.getDefault());
        okHttpClient.setConnectTimeout(10, TimeUnit.MINUTES);
        connectivityAwareUrlClient.setWrappedClient(new OkClient(okHttpClient));

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setClient(connectivityAwareUrlClient)
                        //  .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create((Class<T>) restInterface);
    }

    protected boolean retry(RetrofitError error, AtomicInteger retries) {
        //inkrementaljuk a retryk szamat
        retries.incrementAndGet();
        Log.i(TAG, "request retry check: " + retries.get());

        //nincs internet az url kliens altal
        if (error.getCause() instanceof NoConnectivityException) {
            Log.v(TAG, "No internet!");
            onNoInternet();
            return false;
        }

        Response r = error.getResponse();

        //403-as hiba check
        if (r != null && r.getStatus() == 403) {
            Log.v(TAG, "403 hiba! reloggin!");
            if (retries.get() < DEFAULT_RETRY_COUNT) {

                //Itt lehet reloginnolni

                return true;

            } else {

                //A login kiserletek szama elerte a maximumot.

                return false;
            }
        }

        //404-es hiba check
        if (r != null && r.getStatus() == 404) {
            Log.v(TAG, "404 hiba!");
            if (retries.get() < DEFAULT_RETRY_COUNT) { // ujraprobalkozas
                return true;
            } else { // nincs tobb lehetoseg
                retries.set(0);
                return false;
            }
        }

        //egyeb hiba check
        if (error.isNetworkError()) { //halozati alapu
            if (error.getCause() instanceof SocketTimeoutException) {//connection timeout check
                Log.v(TAG, "retry - socket timeout exception!");
                if (retries.get() < DEFAULT_RETRY_COUNT) { // ujraprobalkozas
                    return true;
                } else { // nincs tobb lehetoseg
                    retries.set(0);
                    return false;
                }
            } else {//no connection check
                Log.v(TAG, "retry - no connection check!");
                if (retries.get() < DEFAULT_RETRY_COUNT) { // ujraprobalkozas
                    return true;
                } else { // nincs tobb lehetoseg
                    retries.set(0);
                    return false;
                }
            }
        } else { //non network error check
            Log.v(TAG, "retry - non error check!");
            retries.set(0);
            return false;
        }
    }

    protected void onNoInternet() {
        if (navigator.isInForeground()) {
            navigator.getCurrentActivityOnScreen().onNoConnectivityError();
        }
        networkErrorHandler.handlerError(new NoConnectivityException("Nincs internet!"));
    }

}
