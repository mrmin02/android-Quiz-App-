package com.example.a19_5_9_testoooooo2;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class QuizAdapter extends RecyclerView.Adapter<QuizViewHolder>{
    private ArrayList<QuestionBean> data;
    private QuestionItemClickListener listener;

    public QuizAdapter(ArrayList<QuestionBean> data, QuestionItemClickListener listener){
        this.data = data;
        this.listener = listener;

    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.question_list_item, viewGroup, false);
        return new QuizViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder quizViewHolder, int i) {
        QuestionBean bean = data.get(i);

        quizViewHolder.textViewQuestion.setText(bean.getQuestion());
        Date date = new Date(bean.getTime());
        SimpleDateFormat format =new SimpleDateFormat("yyyy/MM/dd");
        String time = format.format(date);
        quizViewHolder.textViewTime.setText(time+"   id: "+bean.getId());

        quizViewHolder.itemView.setOnClickListener(v->{
            listener.onQuestionItemClickListener(v,i);
        });

    }

    @Override
    public int getItemCount() {
        if(data == null) return 0;
        else return data.size();
    }

}
