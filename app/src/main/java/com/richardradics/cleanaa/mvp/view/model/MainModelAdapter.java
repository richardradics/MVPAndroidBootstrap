package com.richardradics.cleanaa.mvp.view.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.richardradics.cleanaa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
public class MainModelAdapter extends RecyclerView.Adapter<MainModelAdapter.MainModelViewHolder> {

    private List<MainListViewModel> models;

    public MainModelAdapter() {
        models = new ArrayList<>();
    }

    public MainModelAdapter(List<MainListViewModel> models) {
        this.models = models;
    }

    @Override
    public MainModelAdapter.MainModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View modelView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main, parent, false);
        return new MainModelViewHolder(modelView);
    }

    @Override
    public void onBindViewHolder(MainModelViewHolder holder, int position) {
        MainListViewModel model = models.get(position);

        holder.titleTextView.setText(model.getTitle());
        Picasso.with(holder.view.getContext()).load(model.getImageUrl()).into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    public void clear() {
        models.clear();
    }

    public void addAll(List<MainListViewModel> modelList) {
        for (MainListViewModel m : modelList) {
            if (!models.contains(m)) {
                models.add(m);
            }
        }
    }

    public MainListViewModel getItemByPosition(int position) {
        return models.get(position);
    }

    public class MainModelViewHolder extends RecyclerView.ViewHolder {
        View view;

        ImageView imageView;
        TextView titleTextView;

        public MainModelViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.image);
            titleTextView = (TextView) itemView.findViewById(R.id.text);
        }
    }


}
