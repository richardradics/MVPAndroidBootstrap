package com.richardradics.cleanaa.repository;

import com.richardradics.cleanaa.domain.City;

import java.util.List;

/**
 * Created by radicsrichard on 15. 05. 14..
 */
public interface CleanRepository {

    public List<City> getCities(Double latitude, Double longitude, Integer count);

}
