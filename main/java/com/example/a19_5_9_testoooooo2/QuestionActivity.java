package com.example.a19_5_9_testoooooo2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class QuestionActivity extends AppCompatActivity {
    private boolean check = true;
    private EditText question;
    private EditText questionScore;
    private Button delete;

    private ToggleButton toggleButton;
    private int[] textAnswer = {R.id.textAnswer1, R.id.textAnswer2, R.id.textAnswer3, R.id.textAnswer4};

    private int[] imgAnswer = {R.id.imgAnswer1, R.id.imgAnswer2, R.id.imgAnswer3, R.id.imgAnswer4};


    private int answer;
    private int[] tEx = {R.id.textEx1, R.id.textEx2, R.id.textEx3, R.id.textEx4};
    private int[] iEx = {R.id.imgEx1, R.id.imgEx2, R.id.imgEx3, R.id.imgEx4};
    private EditText[] textEx;
    private ImageView[] imgEx;
    private String[] imgUrl;

    private RadioGroup textRadio;
    private RadioGroup imgRadio;

    private QuestionDBHelper dbHelper;
    private QuestionBean bean;
    private int id;

    private ConstraintLayout textLayout;
    private ConstraintLayout imgLayout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Log.i("message", "11111111111111");
        question = findViewById(R.id.question);
        questionScore = findViewById(R.id.questionScore);
        textRadio = findViewById(R.id.textRadio);
        imgRadio = findViewById(R.id.imgRadio);
        toggleButton = findViewById(R.id.toggleButton);
        imgUrl = new String[4];
        toggleButton.setOnCheckedChangeListener((c, b) -> {
            if (b) {
                textLayout.setVisibility(View.GONE);
                imgLayout.setVisibility(View.VISIBLE);
            } else {
                textLayout.setVisibility(View.VISIBLE);
                imgLayout.setVisibility(View.GONE);
            }
        });

        textLayout = findViewById(R.id.textQuize);
        imgLayout = findViewById(R.id.imgLayout);

        delete = findViewById(R.id.deleteButton);
        delete.setOnClickListener(v->{
            if(id!=-1){
                dbHelper.delete(id);
                finish();
            }
        });


        textEx = new EditText[tEx.length];
        for (int i = 0; i < textEx.length; i++) {
            textEx[i] = findViewById(tEx[i]);
        }
        imgEx = new ImageView[iEx.length];
        for (int i = 0; i < imgEx.length; i++) {
            imgEx[i] = findViewById(iEx[i]);
        }
        Log.i("message", "2222222222222");

        for (int i = 0; i < imgEx.length; i++) {
            imgEx[i].setClickable(true);
            int j = i;
            imgEx[i].setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, j);
            });
        }
        dbHelper = new QuestionDBHelper(this, "db", null, 1);
        id = getIntent().getIntExtra("id", -1);
        if (id == -1) {// 아이디값 체크
            bean = new QuestionBean();
        } else {
            bean = dbHelper.get(id);
            if (bean.getType().equals("IMG")) {
                toggleButton.setChecked(true);
                imgRadio.check(imgAnswer[bean.getAnswer()]);
                String []array={bean.getEx1(),bean.getEx2(),bean.getEx3(),bean.getEx4()};
                try{
                    for(int i = 0; i<array.length;i++){
                        Uri uri = Uri.parse(array[i]); // try catch 묶
                        imgEx[i].setImageURI(uri);
                    }
                }catch(Exception e){
                    Log.i("message","사진 삭제됨");
                }finally {
                    for(int x=0;x<array.length;x++){
                        if (imgEx[x].getDrawable() == null) {
                            Log.i("message","사진 삭제됨");
                            Toast.makeText(this,"사진의 경로가 변경되었습니다.",Toast.LENGTH_SHORT).show();
                            dbHelper.delete(id);
                            finish();
                        }
                    }
                }
            } else {
                textRadio.check(textAnswer[bean.getAnswer()]);
                textEx[0].setText(bean.getEx1());
                textEx[1].setText(bean.getEx2());
                textEx[2].setText(bean.getEx3());
                textEx[3].setText(bean.getEx4());
            }
            question.setText(bean.getQuestion());
            questionScore.setText("" + bean.getScore());


        }
        if (toggleButton.getText().toString().equals("TEXT")) {
            textLayout.setVisibility(View.VISIBLE);
            imgLayout.setVisibility(View.GONE);
        } else {
            textLayout.setVisibility(View.GONE);
            imgLayout.setVisibility(View.VISIBLE);
        }
    }

    public void onSave(View v) {
        if (!check())
            return;

        bean.setQuestion(question.getText().toString());
        bean.setScore(Integer.parseInt(questionScore.getText().toString()));
        Log.i("score", "스코어-------" + Integer.parseInt(questionScore.getText().toString()));
        bean.setAnswer(answer);
        bean.setType(toggleButton.getText().toString().trim());
        if (toggleButton.getText().toString().equals("TEXT")) {
            bean.setEx1(textEx[0].getText().toString());
            bean.setEx2(textEx[1].getText().toString());
            bean.setEx3(textEx[2].getText().toString());
            bean.setEx4(textEx[3].getText().toString());
        } else {
            //이미지 URL 가져와서 세팅
            bean.setEx1(imgUrl[0]);
            bean.setEx2(imgUrl[1]);
            bean.setEx3(imgUrl[2]);
            bean.setEx4(imgUrl[3]);

        }
        if (id != -1) {
            dbHelper.update(bean);
        } else {
            dbHelper.insert(bean);
        }

        Log.i("save", "세이브함 ---------------");
        setResult(Activity.RESULT_OK);
        finish();
    }

    private boolean check() {
        if (question.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "문제 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (questionScore.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "배점을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (toggleButton.getText().equals("TEXT")) {
            if (textRadio.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "정답을 고르세요.", Toast.LENGTH_SHORT).show();
                return false;
            }
            for (int i = 0; i < textEx.length; i++) {
                if (textEx[i].getText().toString().trim().length() == 0) {
                    Toast.makeText(this, "보기를 작성하세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            for (int i = 0; i < textAnswer.length; i++) {
                if (textRadio.getCheckedRadioButtonId() == textAnswer[i]) {
                    answer = i;
                }

            }
            Log.i("answer:", " " + answer);
        } else if (toggleButton.getText().equals("IMG")) {
            if (imgRadio.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "정답을 고르세요.", Toast.LENGTH_SHORT).show();
                return false;
            }
            for (int i = 0; i < textEx.length; i++) {
                if (imgEx[i].getDrawable() == null) {
                    Toast.makeText(this, "보기를 작성하세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            for (int i = 0; i < imgAnswer.length; i++) {
                if (imgRadio.getCheckedRadioButtonId() == imgAnswer[i]) {
                    answer = i;
                }
            }

            Log.i("answer:", " " + answer);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode >= 0 && requestCode < 4) {
            if(resultCode== RESULT_OK){
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    imgEx[requestCode].setImageURI(data.getData());

                    imgUrl[requestCode] = data.getData().toString();
                    Log.i("message Code : ",requestCode+"");
                    // 이미지 표시

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}



