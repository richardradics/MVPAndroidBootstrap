package com.richardradics.cleanaa.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.richardradics.cleanaa.R;
import com.richardradics.cleanaa.app.CleanActivity;
import com.richardradics.cleanaa.mvp.presenter.MainPresenter;
import com.richardradics.cleanaa.mvp.view.MainView;
import com.richardradics.cleanaa.mvp.view.model.MainListViewModel;
import com.richardradics.cleanaa.mvp.view.model.MainModelAdapter;
import com.richardradics.cleanaa.util.RecyclerItemClickListener;
import com.richardradics.core.mvp.Presenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends CleanActivity implements MainView {

    @Bean(MainPresenter.class)
    Presenter presenter;

    @ViewById(R.id.mainModelListView)
    RecyclerView mainRecyclerView;

    private MainModelAdapter mainModelAdapter;

    @AfterViews
    void onAfterViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(layoutManager);
        presenter.setView(this);

        mainRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MainListViewModel mainListViewModel = mainModelAdapter.getItemByPosition(position);
                DetailActivity.launch(MainActivity.this, view.findViewById(R.id.image), mainListViewModel.getImageUrl());
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.stop();
    }

    @Override
    @UiThread
    public void setListViewModels(List<MainListViewModel> mainListViewModelList) {
        if (mainModelAdapter == null) {
            mainModelAdapter = new MainModelAdapter(mainListViewModelList);
            mainModelAdapter.notifyDataSetChanged();
            mainRecyclerView.setAdapter(mainModelAdapter);
        }
    }

    @Override
    public void showLoading(String message) {
        progress.showLoading(this, "Loading...");
    }

    @Override
    public void hideLoading(boolean sucess) {
        progress.endLoading(sucess);
    }

    @Override
    public void showActionLabel(String message) {
        cleanErrorHandler.showSnackBar(message);
    }

    @Override
    public void hideActionLabel() {

    }
}
