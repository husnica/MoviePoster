package com.doubleshotdevs.movieposter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class PostersAdapter extends RecyclerView.Adapter<PostersAdapter.PostersViewHolder> {

    private List<Posters> posters;
    private posterListener posterListener;

    public PostersAdapter(List<Posters> posters, posterListener posterListener) {
        this.posters = posters;
        this.posterListener = posterListener;
    }

    @NonNull
    @Override
    public PostersAdapter.PostersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostersViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_container_poster,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull PostersAdapter.PostersViewHolder holder, int position) {
        holder.bindPosters(posters.get(position));

    }

    @Override
    public int getItemCount() {
        return posters.size();
    }

    public List<Posters> getSelectedPosters(){
        List<Posters> selectedPosters = new ArrayList<>();
        for (Posters poster : posters){
            if (poster.isSelected){
                selectedPosters.add(poster);
            }
        }
        return selectedPosters;
    }

    class PostersViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layoutPosters;
        View viewbackground;
        RoundedImageView imagePosters;
        TextView textName, textCreatedBy, textStory;
        RatingBar ratingBar;
        ImageView imageSelected;

        public PostersViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutPosters = itemView.findViewById(R.id.layoutPosters);
            viewbackground = itemView.findViewById(R.id.viewBackground);
            imagePosters = itemView.findViewById(R.id.imagePosters);
            textName = itemView.findViewById(R.id.textName);
            textCreatedBy = itemView.findViewById(R.id.textCreateBy);
            textStory = itemView.findViewById(R.id.textStory);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imageSelected = itemView.findViewById(R.id.imageSelected);

        }

        void bindPosters(final Posters posters) {
            imagePosters.setImageResource(posters.image);
            textName.setText(posters.name);
            textStory.setText(posters.story);
            textCreatedBy.setText(posters.createdBy);
            ratingBar.setRating(posters.rating);

            // Show if the poster is selected
            if (posters.isSelected) {
                viewbackground.setBackgroundResource(R.drawable.poster_selected_background);
                imageSelected.setVisibility(View.VISIBLE);

            } else {
                viewbackground.setBackgroundResource(R.drawable.poster_background);
                imageSelected.setVisibility(View.GONE);
            }

            layoutPosters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (posters.isSelected) {
                        viewbackground.setBackgroundResource(R.drawable.poster_background);
                        imageSelected.setVisibility(View.GONE);
                        posters.isSelected = false;
                        if (getSelectedPosters().isEmpty()) {
                            posterListener.OnPosterAction(false);
                        }
                    } else {
                        viewbackground.setBackgroundResource(R.drawable.poster_selected_background);
                        imageSelected.setVisibility(View.VISIBLE);
                        posters.isSelected = true;
                        posterListener.OnPosterAction(true);
                    }
                }
            });
        }
    }
}





