package com.example.a19_5_9_testoooooo2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class QuizViewHolder extends RecyclerView.ViewHolder{
    public TextView textViewQuestion;
    public TextView textViewTime;
    public QuizViewHolder(@NonNull View itemView){
        super(itemView);
        textViewQuestion = itemView.findViewById(R.id.textView_qustion);
        textViewTime = itemView.findViewById(R.id.textView_time);

    }
}
