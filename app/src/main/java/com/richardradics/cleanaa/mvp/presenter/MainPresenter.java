package com.richardradics.cleanaa.mvp.presenter;

import com.richardradics.cleanaa.repository.api.model.openweatherwrapper.WeatherItem;
import com.richardradics.cleanaa.app.AppConfig;
import com.richardradics.cleanaa.interactor.GetWeathers;
import com.richardradics.cleanaa.interactor.GetWeathersImp;
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

    @Bean(GetWeathersImp.class)
    GetWeathers getWeathers;

    @Override
    public void setView(View view) {
        this.mainView = (MainView) view;
    }


    @Override
    public void start() {
        mainView.showLoading("Loading");
        getWeathers.getWeathers(AppConfig.TEST_DATA.BP_LATITUDE, AppConfig.TEST_DATA.BP_LONGITUDE, AppConfig.TEST_DATA.DEFAULT_COUNT, new GetWeathers.Callback() {
            @Override
            public void onWeatherListItemLoaded(List<WeatherItem> weatherItemList) {
                mainView.setListViewModels(convertToMainModel(weatherItemList));
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

    public List<MainListViewModel> convertToMainModel(List<WeatherItem> weatherItemList) {
        List<MainListViewModel> modelList = new ArrayList<>();
        try {
            int pictureId = 0;

            for (WeatherItem item : weatherItemList) {
                MainListViewModel mainListViewModel = new MainListViewModel();

                mainListViewModel.setImageUrl("http://lorempixel.com/400/200/sports/" + String.valueOf(pictureId + 1));
                mainListViewModel.setTitle(item.getName());

                modelList.add(mainListViewModel);

                pictureId++;
                if (10 < pictureId) { //lorempixels gives only 10 image :)
                    pictureId = 0;
                }
            }
        } catch (Exception e) {
            cleanErrorHandler.logExpception(e);
        }
        return modelList;
    }

}
