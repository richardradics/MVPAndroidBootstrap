package com.richardradics.cleanaa.interactor;

import com.richardradics.cleanaa.domain.City;
import com.richardradics.cleanaa.repository.api.model.openweatherwrapper.WeatherItem;

import java.util.List;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
public interface GetCities {

    void getCities(Double latitude, Double longitude, Integer count, Callback callback);

    interface Callback {
        void onCitiesoaded(List<City> cityList);
        void onError(Exception e);
    }

}
