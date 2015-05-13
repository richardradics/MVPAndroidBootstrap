package com.richardradics.cleanaa.app;

import com.richardradics.cleanaa.mvp.view.model.MainListViewModel;
import com.richardradics.core.database.SnappyDatabase;

import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
@EBean(scope = EBean.Scope.Singleton)
public class CleanDatabase extends SnappyDatabase {

    public static final class Keys {
        private static final String MAIN_LIST_CACHE = "MAINLISTCACHE";
    }

    public void cacheMainListViewModelList(List<MainListViewModel> mainListViewModels) {
        setValue(Keys.MAIN_LIST_CACHE, mainListViewModels);
    }

    public List<MainListViewModel> getCachedMainListModelList() {
        return getObjectList(Keys.MAIN_LIST_CACHE, MainListViewModel.class);
    }

}
