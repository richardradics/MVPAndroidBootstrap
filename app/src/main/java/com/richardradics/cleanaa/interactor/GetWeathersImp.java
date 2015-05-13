package com.richardradics.cleanaa.interactor;

import com.richardradics.cleanaa.api.model.openweatherwrapper.OpenWeatherWrapper;
import com.richardradics.cleanaa.api.model.openweatherwrapper.WeatherItem;
import com.richardradics.cleanaa.exception.GetWeathersException;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
@EBean
public class GetWeathersImp extends BaseInteractor implements GetWeathers {

    private Callback callback;

    @Override
    @Background
    @DebugLog
    public void getWeathers(Double latitude, Double longitude, Integer count, Callback callback) {
        this.callback = callback;
        try {
            OpenWeatherWrapper openWeatherWrapper = openWeatherClient.getWeatherItems(latitude, longitude, count);
            onItemsLoaded(openWeatherWrapper.getList());
        } catch (GetWeathersException getWeathersException) {
            onError(getWeathersException);
        }
    }

    @UiThread
    @DebugLog
    public void onItemsLoaded(List<WeatherItem> weatherItemList) {
        callback.onWeatherListItemLoaded(weatherItemList);
    }

    @UiThread
    public void onError(Exception e) {
        callback.onError(e);
    }

}
