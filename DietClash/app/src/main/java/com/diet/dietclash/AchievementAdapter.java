package com.diet.dietclash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementHolder> {

    private List<Achievement> achievementsList;


    public static class AchievementHolder extends RecyclerView.ViewHolder {

        private TextView achievementTitle;
        private TextView achievementDescription;
        private TextView achievementProgress;
        private ProgressBar achievementProgressBar;

        public AchievementHolder(@NonNull View itemView) {
            super(itemView);
            achievementTitle = itemView.findViewById(R.id.achievement_title);
            achievementDescription = itemView.findViewById(R.id.achievement_desc);
            achievementProgress = itemView.findViewById(R.id.achievement_percentage);
            achievementProgressBar = itemView.findViewById(R.id.achievement_progress_bar);
        }
    }

    //Constructor
    public AchievementAdapter(List<Achievement> achievements) throws NullPointerException {
        if(achievements == null){
            throw new NullPointerException("Achievement list cannot be null");
        }
        achievementsList = achievements;
    }

    @Override
    public AchievementAdapter.AchievementHolder onCreateViewHolder(ViewGroup parent,int viewType){
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        return new AchievementHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementHolder holder, int position) {
        holder.achievementTitle.setText(achievementsList.get(position).getTitle());
        holder.achievementDescription.setText(achievementsList.get(position).getDescription());
        String displayPercentage = (int)achievementsList.get(position).getPercentage() + "%";
        holder.achievementProgress.setText(displayPercentage);
        holder.achievementProgressBar.setProgress(achievementsList.get(position).getProgress(),
                true);
        holder.achievementProgressBar.setMax(achievementsList.get(position).getGoal());
    }

    @Override
    public int getItemCount() {
        return achievementsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_item;
    }
}
