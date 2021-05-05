package com.coolcats.coolcatsapi3rdparty.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.coolcats.coolcatsapi3rdparty.databinding.JikanItemLayoutBinding;
import com.coolcats.coolcatsapi3rdparty.model.JikanItem;

import java.util.List;

public class JikanAdapter extends RecyclerView.Adapter<JikanAdapter.JikanViewHolder> {

    private List<JikanItem> results;

    public JikanAdapter(List<JikanItem> results) {
        this.results = results;
    }

    public void setResults(List<JikanItem> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JikanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        JikanItemLayoutBinding binding = JikanItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new JikanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull JikanViewHolder holder, int position) {

        JikanItem item = results.get(position);

        holder.binding.titleTextview.setText(item.getTitle());

        Glide.with(holder.binding.getRoot())
                .applyDefaultRequestOptions(RequestOptions.centerCropTransform())
                .load(item.getImageUrl())
                .into(holder.binding.posterImageview);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class JikanViewHolder extends RecyclerView.ViewHolder {
        JikanItemLayoutBinding binding;
        public JikanViewHolder(@NonNull JikanItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
