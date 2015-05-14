package com.richardradics.cleanaa.interactor;

import com.richardradics.cleanaa.repository.api.OpenWeatherClient;
import com.richardradics.cleanaa.app.CleanDatabase;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
@EBean
public class BaseInteractor {

    @Bean
    protected CleanDatabase cleanDatabase;

    @Bean
    protected OpenWeatherClient openWeatherClient;

}
