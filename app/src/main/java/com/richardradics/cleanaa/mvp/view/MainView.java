package com.richardradics.cleanaa.mvp.view;

import com.richardradics.cleanaa.mvp.view.model.MainListViewModel;
import com.richardradics.core.mvp.View;

import java.util.List;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
public interface MainView extends View {

    public void setListViewModels(List<MainListViewModel> mainListViewModelList);

}
