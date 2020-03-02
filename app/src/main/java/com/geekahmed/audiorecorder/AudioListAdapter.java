package com.geekahmed.audiorecorder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.AudioViewHolder> {

    private ArrayList<File> allFiles;
    private TimeAgo timeAgo;
    private onItemListClick onItemListClick;
    public AudioListAdapter(ArrayList<File> allFiles, onItemListClick onItemListClick){
        this.allFiles = allFiles;
        this.onItemListClick = onItemListClick;

    }
    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        timeAgo = new TimeAgo();
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        holder.list_title.setText(allFiles.get(position).getName());
        holder.list_date.setText(timeAgo.getTimeAgo(allFiles.get(position).lastModified()));

    }

    @Override
    public int getItemCount() {
        return allFiles.size();
    }

    public class AudioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView list_image;
        private TextView list_title;
        private TextView list_date;
        private ImageButton deleteBtn;

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);

            list_image = itemView.findViewById(R.id.list_image_view);
            list_title = itemView.findViewById(R.id.list_title);
            list_date = itemView.findViewById(R.id.list_date);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            itemView.setOnClickListener(this);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemListClick != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onItemListClick.onDeleteClick(allFiles.get(getAdapterPosition()), getAdapterPosition());
                        }
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {
            onItemListClick.onClickListener(allFiles.get(getAdapterPosition()), getAdapterPosition());

        }
    }

    public interface onItemListClick {
        void onClickListener(File file, int position);
        void onDeleteClick(File file,int position);
    }


}
