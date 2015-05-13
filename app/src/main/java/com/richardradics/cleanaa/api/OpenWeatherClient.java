package com.richardradics.cleanaa.api;

import android.util.Log;

import com.richardradics.cleanaa.api.model.openweatherwrapper.OpenWeatherWrapper;
import com.richardradics.cleanaa.app.CleanDatabase;
import com.richardradics.cleanaa.exception.GetWeathersException;
import com.richardradics.cleanaa.interactor.GetWeathers;
import com.richardradics.core.retrofit.BaseRetrofitClient;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
@EBean(scope = EBean.Scope.Singleton)
public class OpenWeatherClient extends BaseRetrofitClient {

    private static final String TAG = "WeatherClient";
    private static final String ENDPOINT = "http://api.openweathermap.org";

    @Bean
    CleanDatabase cleanDatabase;


    private static OpenWeatherAPI openWeatherAPI;

    @AfterInject
    void onAfterInjectFinished() {
        openWeatherAPI = initRestAdapter(ENDPOINT, OpenWeatherAPI.class);
    }


    public void getWeatherItemsAsync(final Double latitude, final Double longitude, final Integer count) {
        openWeatherAPI.getWeatherItems(latitude, longitude, count, new Callback<OpenWeatherWrapper>() {
            @Override
            public void success(OpenWeatherWrapper openWeatherWrapper, Response response) {
                retries.set(0);
            }

            @Override
            public void failure(RetrofitError error) {
                if (retry(error, retries)) {
                    getWeatherItems(latitude, longitude, count);
                } else {
                    networkErrorHandler.handlerError(error);
                }
            }
        });
    }

    public OpenWeatherWrapper getWeatherItems(Double latitude, Double longitude, Integer count) {
        try {
            OpenWeatherWrapper openWeatherWrapper = openWeatherAPI.getWeatherItems(latitude, longitude, count);
            return openWeatherWrapper;
        } catch (RetrofitError retrofitError) {
            if (retry(retrofitError, retries)) {
                return getWeatherItems(latitude, longitude, count);
            } else {
                return throwGetWeathersException(retrofitError);
            }
        } catch (Exception exception) {
            return throwGetWeathersException(exception);
        }
    }

    private OpenWeatherWrapper throwGetWeathersException(Exception retrofitError) {
        Log.e(TAG, "Error during fetching weatheritems");
        GetWeathersException getWorkShopsException = new GetWeathersException();
        getWorkShopsException.setStackTrace(retrofitError.getStackTrace());
        throw getWorkShopsException;
    }

}
