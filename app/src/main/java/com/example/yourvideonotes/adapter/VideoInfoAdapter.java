package com.example.yourvideonotes.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.yourvideonotes.R;
import com.example.yourvideonotes.databinding.AddDialogBinding;
import com.example.yourvideonotes.databinding.ItemVideoBinding;
import com.example.yourvideonotes.model.VideoInfo;
import com.example.yourvideonotes.roomdb.VideoDatabase;
import com.example.yourvideonotes.roomdb.VideoInfoDao;
import com.example.yourvideonotes.util.Util;
import com.example.yourvideonotes.view.MainActivity;
import com.example.yourvideonotes.view.VideoActivity;

import java.util.ArrayList;

public class VideoInfoAdapter extends RecyclerView.Adapter<VideoInfoAdapter.ViewHolder> {

    ArrayList<VideoInfo> videoInfoArrayList;

    public VideoInfoAdapter(ArrayList<VideoInfo> videoInfoArrayList){
        this.videoInfoArrayList = videoInfoArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVideoBinding itemVideoBinding = ItemVideoBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(itemVideoBinding) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String videoTitle = videoInfoArrayList.get(position).videoTitle;
        String videoUrl = videoInfoArrayList.get(position).videoLink;
        String videoExplanation = videoInfoArrayList.get(position).videoExplanation;
        String date = Util.getDate(holder.itemView.getContext());
        int videoId = videoInfoArrayList.get(position).id;
        float videoSecond = videoInfoArrayList.get(position).videoSec;

        holder.binding.videoExplanationText.setText(videoExplanation);
        holder.binding.videoTitleText.setText(videoTitle);
        holder.binding.videoDateText.setText("Saved date: "+date);


        holder.binding.playVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),VideoActivity.class);
                intent.putExtra("url",videoUrl);
                intent.putExtra("title",videoTitle);
                intent.putExtra("exp",videoExplanation);
                intent.putExtra("date",date);
                intent.putExtra("sec",videoSecond);
                intent.putExtra("id",videoId);
                v.getContext().startActivity(intent);

            }
        });

        holder.binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Util.deleteVideo(v.getContext(),videoId);
                videoInfoArrayList.remove(position);
                notifyDataSetChanged();

            }
        });

        holder.binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VideoInfo info = new VideoInfo(videoInfoArrayList.get(position).videoTitle,
                        videoInfoArrayList.get(position).videoLink,
                        videoInfoArrayList.get(position).videoExplanation,
                        videoInfoArrayList.get(position).videoSec,
                        videoInfoArrayList.get(position).date);


                Util.dialogEdit(v.getContext(),info,videoId);
                notifyDataSetChanged();


            }
        });



    }



    @Override
    public int getItemCount() {
        return videoInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemVideoBinding binding;
        public ViewHolder(ItemVideoBinding binding) {
             super(binding.getRoot());
             this.binding = binding;
        }
    }
}
