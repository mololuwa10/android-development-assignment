package com.example.moloassignment.FilePackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moloassignment.R;

import java.io.File;
import java.util.ArrayList;

public class FileViewAdapter extends RecyclerView.Adapter<FileViewAdapter.FileViewHolder>{
    private ArrayList<MyFile> fileList;
    public FileViewAdapter(ArrayList<MyFile> fileList) {
        this.fileList = fileList;
    }

    @NonNull
    @Override
    public FileViewAdapter.FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewAdapter.FileViewHolder holder, int position) {
        MyFile file = fileList.get(position);
        holder.imageView.setImageResource(R.drawable.baseline_file_copy_24);
        holder.fileTextView.setText(file.getFile().getName());
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {
        public TextView fileTextView;
        public ImageView imageView;
        public FileViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fileImageView);
            fileTextView = itemView.findViewById(R.id.fileTextView);
        }
    }
}
