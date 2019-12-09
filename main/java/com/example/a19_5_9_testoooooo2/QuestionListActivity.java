package com.example.a19_5_9_testoooooo2;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class QuestionListActivity extends AppCompatActivity implements QuestionItemClickListener{
    private EditText questionPWD;
    private String pwd = "2222";
    private Button settingButton;
    private ConstraintLayout questionList;
    private ConstraintLayout loginLayout;

    private RecyclerView list;
    private QuizAdapter adapter;
    private ArrayList<QuestionBean> bean;
    private QuestionDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        questionPWD = findViewById(R.id.questionPWD);
        questionList = findViewById(R.id.questionList);
        loginLayout = findViewById(R.id.loginLayout);
        settingButton = findViewById(R.id.setting);

        questionList.setVisibility(View.INVISIBLE);
        loginLayout.setVisibility(View.VISIBLE);

        dbHelper = new QuestionDBHelper(this,"db",null,1);
        bean = dbHelper.get();

        list = findViewById(R.id.recyclerView);
        adapter = new QuizAdapter(bean, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);

    }
    public void editGo(View v){
        if(questionPWD.getText().toString().equals(pwd)){
            questionList.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.INVISIBLE);

        }else{
            finish();
        }
    }
    public void settingGo(View v){
        Intent i = new Intent(this, QuestionActivity.class);
        startActivityForResult(i,1);
    }

    @Override
    public void onQuestionItemClickListener(View v, int index) {
        Log.i("click","----클릭받음---------");
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("id",bean.get(index).getId());

        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // memoActivity 가 끝 난 후. 데이터를 받는 곳.
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode != 1) return;//뒤로 가기 버튼으로 종료 했을 경우 return

        this.bean = dbHelper.get();
        adapter.notifyDataSetChanged();// 동기화.  웹옵서버 에 데이터 세트가 변경되었음을 알림.
    }
}
