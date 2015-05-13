package com.richardradics.cleanaa.interactor;

import com.richardradics.cleanaa.api.model.openweatherwrapper.WeatherItem;

import java.util.List;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
public interface GetWeathers {

    void getWeathers(Double latitude, Double longitude, Integer count, Callback callback);

    interface Callback {
        void onWeatherListItemLoaded(List<WeatherItem> weatherItemList);
        void onError(Exception e);
    }

}
