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
        String date = MainActivity.getDate(holder.itemView.getContext());
        int videoId = videoInfoArrayList.get(position).id;
        float videoSecond = videoInfoArrayList.get(position).videoSec;

        holder.binding.videoExplanationText.setText(videoExplanation);
        holder.binding.videoTitleText.setText(videoTitle);
        holder.binding.videoDateText.setText(date);



        holder.binding.playVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),VideoActivity.class);
                intent.putExtra("url",videoUrl);
                intent.putExtra("title",videoTitle);
                intent.putExtra("exp",videoExplanation);
                intent.putExtra("date",date);
                intent.putExtra("sec",videoSecond);
                v.getContext().startActivity(intent);

            }
        });

        holder.binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoDatabase db = Room.databaseBuilder(v.getContext(), VideoDatabase.class, "videoinfo").allowMainThreadQueries().build();
                VideoInfoDao videoInfoDao = db.videoInfoDao();
                videoInfoDao.deleteVideo(videoId);
                videoInfoArrayList.remove(position);
                notifyDataSetChanged();

            }
        });

        holder.binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(v.getContext());
                LayoutInflater inflater = dialog.getLayoutInflater();
                AddDialogBinding dialogBinding = AddDialogBinding.inflate(inflater);
                View view = dialogBinding.getRoot();
                dialog.setContentView(view);

                VideoDatabase db = Room.databaseBuilder(v.getContext(), VideoDatabase.class, "videoinfo").allowMainThreadQueries().build();
                VideoInfoDao videoInfoDao = db.videoInfoDao();

                // current edittext
                dialogBinding.explanationDialogEdittext.setText(videoExplanation);
                dialogBinding.secondDialogEdittext.setText(""+videoSecond);
                dialogBinding.titleDialogEdittext.setText(videoTitle);
                dialogBinding.linkDialogEdittext.setText(videoUrl);

                dialogBinding.addImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String dialogLinkEdittextStr = dialogBinding.linkDialogEdittext.getText().toString();
                        String dialogExpEdittextStr = dialogBinding.explanationDialogEdittext.getText().toString();
                        String dialogTitleEdittextStr = dialogBinding.titleDialogEdittext.getText().toString();
                        String dialogSecEdittextFloat = dialogBinding.secondDialogEdittext.getText().toString();


                        if (!dialogSecEdittextFloat.isEmpty() && !dialogLinkEdittextStr.isEmpty() && !dialogSecEdittextFloat.isEmpty() && !dialogTitleEdittextStr.isEmpty())
                        {
                            VideoInfo info = new VideoInfo(dialogTitleEdittextStr,dialogLinkEdittextStr,dialogExpEdittextStr,Float.parseFloat(dialogSecEdittextFloat),date);
                            videoInfoDao.updateVideo(videoId,dialogLinkEdittextStr,dialogTitleEdittextStr,dialogExpEdittextStr,Float.parseFloat(dialogSecEdittextFloat));
                            videoInfoArrayList.set(position,info);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(v.getContext(), "Please fill in the blanks", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.show();





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
