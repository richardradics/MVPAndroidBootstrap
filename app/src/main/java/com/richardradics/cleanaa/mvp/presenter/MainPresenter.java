package com.richardradics.cleanaa.mvp.presenter;

import com.richardradics.cleanaa.domain.City;
import com.richardradics.cleanaa.interactor.GetCitiesImp;
import com.richardradics.cleanaa.repository.api.model.openweatherwrapper.WeatherItem;
import com.richardradics.cleanaa.app.AppConfig;
import com.richardradics.cleanaa.interactor.GetCities;
import com.richardradics.cleanaa.mvp.view.MainView;
import com.richardradics.cleanaa.mvp.view.model.MainListViewModel;
import com.richardradics.core.mvp.Presenter;
import com.richardradics.core.mvp.View;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
@EBean(scope = EBean.Scope.Singleton)
public class MainPresenter extends BasePresenter implements Presenter {

    private MainView mainView;

    @Bean(GetCitiesImp.class)
    GetCities getCities;

    @Override
    public void setView(View view) {
        this.mainView = (MainView) view;
    }


    @Override
    public void start() {
        mainView.showLoading("Loading");
        getCities.getCities(AppConfig.TEST_DATA.BP_LATITUDE, AppConfig.TEST_DATA.BP_LONGITUDE, AppConfig.TEST_DATA.DEFAULT_COUNT, new GetCities.Callback() {
            @Override
            public void onCitiesoaded(List<City> citiesList) {
                mainView.setListViewModels(convertToMainModel(citiesList));
                mainView.hideLoading(true);
            }

            @Override
            public void onError(Exception e) {
                mainView.showActionLabel("Error during fetching data!");
                mainView.hideLoading(false);
            }
        });
    }


    @Override
    public void stop() {
        //TODO if something needed to
    }

    @Override
    @UiThread
    public void onError(Exception exception) {
        mainView.showActionLabel(exception.getMessage());
    }

    public List<MainListViewModel> convertToMainModel(List<City> cityList) {
        List<MainListViewModel> modelList = new ArrayList<>();
        try {
            int pictureId = 0;

            for (City item : cityList) {
                MainListViewModel mainListViewModel = new MainListViewModel();

                mainListViewModel.setId(item.getId());
                mainListViewModel.setImageUrl("http://lorempixel.com/400/200/city/" + String.valueOf(pictureId + 1));
                mainListViewModel.setTitle(item.getName());

                modelList.add(mainListViewModel);

                pictureId++;
                if (9 < pictureId) { //lorempixels gives only 10 image :)
                    pictureId = 0;
                }
            }
        } catch (Exception e) {
            cleanErrorHandler.logExpception(e);
        }
        return modelList;
    }

}
