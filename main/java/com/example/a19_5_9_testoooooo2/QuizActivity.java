package com.example.a19_5_9_testoooooo2;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.VISIBLE;

public class QuizActivity extends AppCompatActivity {
    private QuestionDBHelper dbHelper;
    private QuestionBean bean;

    private TextView questionBody;
    private TextView qScore;
    private int countId=0;
    private Button submit;
    private final int MAXID=30;
    private String mode;

    private TextView textEx1;
    private TextView textEx2;
    private TextView textEx3;
    private TextView textEx4;

    private ImageView imgEx1;
    private ImageView imgEx2;
    private ImageView imgEx3;
    private ImageView imgEx4;

    private RadioGroup textRadio;
    private RadioGroup imgRadio;

    private int[] textAnswer={R.id.textAnswer1,R.id.textAnswer2,R.id.textAnswer3,R.id.textAnswer4};
    private int[] imgAnswer={R.id.imgAnswer1,R.id.imgAnswer2,R.id.imgAnswer3,R.id.imgAnswer4};
    private int userAnswer;
    private int userScore = 0;

    //hardmode
    private String qustionType;
    private EditText hardAnswer;

    //팝업
    private PopupWindow popupWindow;
    private Button ok;
    private TextView henzi;
    private TextView henziScore;
    private boolean finish=false;
    private boolean finishCheck = false;

    public QuizActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mode = getIntent().getStringExtra("mode");
        modeLayout(mode);
        qScore = findViewById(R.id.qScore);
        questionBody = findViewById(R.id.questionBody);
        submit = findViewById(R.id.submit);

        textEx1=findViewById(R.id.textEx1);
        textEx2=findViewById(R.id.textEx2);
        textEx3=findViewById(R.id.textEx3);
        textEx4=findViewById(R.id.textEx4);

        imgEx1=findViewById(R.id.imgEx1);
        imgEx2=findViewById(R.id.imgEx2);
        imgEx3=findViewById(R.id.imgEx3);
        imgEx4=findViewById(R.id.imgEx4);

        textRadio= findViewById(R.id.textRadio);
        imgRadio= findViewById(R.id.imgRadio);
        //하드모드
        hardAnswer = findViewById(R.id.hardAnswer);
        //팝업

        View popupView = getLayoutInflater().inflate(R.layout.popup, null);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(false);

        ok = popupView.findViewById(R.id.ok);
        ok.setOnClickListener(v->{
            Log.i("click ok","누름");
            popupWindow.dismiss();


            if(finish) {
                henzi.setText("최종 " + userScore + "점 !!");
                henziScore.setText("");
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                finish =false;
                finishCheck= false;
                }
            if(finishCheck) {
                Toast.makeText(this, "마지막 문제임", Toast.LENGTH_SHORT).show();
                finish();
            }
            if(!finish)
                finishCheck = true;

        });
        henzi = popupView.findViewById(R.id.henzi);
        henziScore = popupView.findViewById(R.id.henziScore);

        submit.setOnClickListener(v->{
            Log.i("message :","클릭");
            if(mode.equals("easy")){//이지모드
                if(qustionType.equals("TEXT")){//문제 텍스트
                    if(textRadio.getCheckedRadioButtonId()==-1){
                        Toast.makeText(this,"정답을 고르세요.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for(int i=0; i<textAnswer.length;i++) {
                        if (textRadio.getCheckedRadioButtonId() == textAnswer[i]) {
                            userAnswer = i;
                        }
                    }
                }else{//문제 이미지
                    if(imgRadio.getCheckedRadioButtonId()==-1){
                        Toast.makeText(this,"정답을 고르세요.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for(int i=0; i<imgAnswer.length;i++) {//이미지 문제
                        if (imgRadio.getCheckedRadioButtonId() == imgAnswer[i]) {
                            userAnswer = i;
                        }
                    }
                }

            }else{//하드모드.
                if(qustionType.equals("TEXT")) {
                    if (hardAnswer.getText().toString().trim().length() == 0) {
                        Toast.makeText(this, "정답을 작성하세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try{//연타했을 경우 null값인 bean 의 데이터를 가져오려고 할 때.
                        switch (bean.getAnswer()) {
                            case 0:
                                if (bean.getEx1().trim().equals(hardAnswer.getText().toString().trim())) {
                                    userAnswer = bean.getAnswer();
                                } else{
                                    userAnswer = -1;
                                }
                                break;
                            case 1:
                                if (bean.getEx2().trim().equals(hardAnswer.getText().toString().trim())) {
                                    userAnswer = bean.getAnswer();
                                } else {
                                    userAnswer = -1;
                                }
                                break;
                            case 2:
                                if (bean.getEx3().trim().equals(hardAnswer.getText().toString().trim())) {
                                    userAnswer = bean.getAnswer();
                                }else{
                                    userAnswer = -1;
                                }

                                break;
                            case 3:
                                if (bean.getEx4().trim().equals(hardAnswer.getText().toString().trim())) {
                                    userAnswer = bean.getAnswer();
                                } else{
                                    userAnswer = -1;
                                }
                                break;
                        }
                    }catch (Exception e){finish();}

                }else{//하드 이미지 문제.
                    if(imgRadio.getCheckedRadioButtonId()==-1){
                        Toast.makeText(this,"정답을 고르세요.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for(int i=0; i<imgAnswer.length;i++) {//이미지 문제
                        if (imgRadio.getCheckedRadioButtonId() == imgAnswer[i]) {
                            userAnswer = i;
                        }
                    }
                }

            }
            Log.i("message :","초기화");
            //보기 버튼, 입력 내용 초기화.
            textRadio.check(-1);
            imgRadio.check(-1);
            hardAnswer.setText("");

            finishCheck = false;
            try{
                if(bean.getAnswer()==userAnswer){//답 비교
                    Log.i("message  ","답 비교함. 정답");
                    //정답 팝업
                    henzi.setText("정답");
                    henzi.setText(bean.getScore()+"점 획득");
                    userScore+=bean.getScore();
                    henziScore.setText(userScore+" 점");
                }else{
                    //오답 팝업
                    Log.i("message  ","답 비교함. 오답");
                    henzi.setText("오답ㅠㅠ");
                    henziScore.setText("");
                }
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                Log.i("id값: ",""+countId);
                newQuestion();
                //이미지 로딩에는 시간이 걸릴 수 있음.
                if(bean != null){
                    if(bean.getType().equals("IMG")){//추가 할 수 있는 것. 이전 문제에서 다음 문제의 데이터를 미리 받아두면..
                        Toast.makeText(this,"이미지 로딩에 시간이 걸릴 수 있습니다.",Toast.LENGTH_SHORT).show();
                    }
                }

            }catch (Exception e){finish();}


        });
        Log.i("id값: ",""+countId);
        dbHelper = new QuestionDBHelper(this, "db", null, 1);

        newQuestion();

        if(qScore.getText().toString().trim().equals("")){
            Toast.makeText(this,"문제 없음",Toast.LENGTH_SHORT).show();
            finish();
        }

    }
    private void newQuestion(){
        bean=null;
        while(bean==null){
            bean = dbHelper.get(countId);
            countId++;
            Log.i("message countId roup: ",""+countId);
            Log.i("id값: ",""+countId);
            if(countId>MAXID){
                finish =true;
                break;
            }
        }//bean에 문제를 가지고 나옴, 끝인 경우도 while 탈출.


        if(countId<MAXID) {//문제를 가지고 나온 경우.

            qScore.setText("" + bean.getScore());
            questionBody.setText(bean.getQuestion());
            if (mode.equals("easy")) {
                if(bean.getType().equals("TEXT")){//easy 모드 객관식
                    qustionType="TEXT";
                    textEx1.setText(bean.getEx1());
                    textEx2.setText(bean.getEx2());
                    textEx3.setText(bean.getEx3());
                    textEx4.setText(bean.getEx4());
                    findViewById(R.id.textQuize).setVisibility(View.VISIBLE);
                    findViewById(R.id.imgQuize).setVisibility(View.INVISIBLE);
                }else{//easy 모드 이미지.
                    qustionType="IMG";
                    try{
                        String[] array = {bean.getEx1(), bean.getEx2(), bean.getEx3(), bean.getEx4()};
                        Uri uri = Uri.parse(array[0]);
                        imgEx1.setImageURI(uri);
                        uri = Uri.parse(array[1]);
                        imgEx2.setImageURI(uri);
                        uri = Uri.parse(array[2]);
                        imgEx3.setImageURI(uri);
                        uri = Uri.parse(array[3]);
                        imgEx4.setImageURI(uri);
                        findViewById(R.id.textQuize).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imgQuize).setVisibility(View.VISIBLE);
                    }catch (Exception e){
                        Toast.makeText(this,"문제의 이미지 파일의 경로가 변경, 삭제 되었습니다.",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }else{//하드모드. 주관식
                if(bean.getType().equals("TEXT")){
                    qustionType="TEXT";
                    findViewById(R.id.hardMode).setVisibility(View.VISIBLE);
                    findViewById(R.id.imgQuize).setVisibility(View.INVISIBLE);
                }else {//하드 이미지.

                    try{
                        qustionType="IMG";
                        String[] array = {bean.getEx1(), bean.getEx2(), bean.getEx3(), bean.getEx4()};
                        Uri uri = Uri.parse(array[0]);
                        imgEx1.setImageURI(uri);
                        uri = Uri.parse(array[1]);
                        imgEx2.setImageURI(uri);
                        uri = Uri.parse(array[2]);
                        imgEx3.setImageURI(uri);
                        uri = Uri.parse(array[3]);
                        imgEx4.setImageURI(uri);
                        findViewById(R.id.imgQuize).setVisibility(View.VISIBLE);
                        findViewById(R.id.hardMode).setVisibility(View.INVISIBLE);
                    }catch (Exception e){//사용자는 그냥 빈 이미지 화면을 봄.
                        Toast.makeText(this,"문제의 이미지 파일의 경로가 변경, 삭제 되었습니다.",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            }
        }
    }
    private void modeLayout(String mode){
        if(mode.equals("easy")){
            findViewById(R.id.hardMode).setVisibility(View.INVISIBLE);
        }else{//hard mode
            findViewById(R.id.textQuize).setVisibility(View.INVISIBLE);
            findViewById(R.id.hardMode).setVisibility(View.VISIBLE);
        }
    }

}

