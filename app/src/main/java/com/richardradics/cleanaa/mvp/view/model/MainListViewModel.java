package com.richardradics.cleanaa.mvp.view.model;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
public class MainListViewModel {

    private Long id;
    private String imageUrl;
    private String title;

    public MainListViewModel() {

    }

    public MainListViewModel(Long id, String imageUrl, String title) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
