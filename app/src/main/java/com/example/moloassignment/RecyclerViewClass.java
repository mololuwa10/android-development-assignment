package com.example.moloassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewClass extends RecyclerView.Adapter<RecyclerViewClass.ViewHolder> {
    private ArrayList<File> fileArrayList;
    public RecyclerViewClass(ArrayList<File> fileDataSet) {
        fileArrayList = fileDataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.fileImageView);
            textView = (TextView) itemView.findViewById(R.id.fileTextView);
        }
    }

    @NonNull
    @Override
    public RecyclerViewClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewClass.ViewHolder holder, int position) {
        File file = fileArrayList.get(position);
        holder.imageView.setImageResource(R.drawable.baseline_file_copy_24);
        holder.textView.setText(file.getName());
    }

    @Override
    public int getItemCount() {
        return fileArrayList.size();
    }
}
