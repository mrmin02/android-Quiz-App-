package com.example.a19_5_9_testoooooo2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private Button start;
    private RadioButton easy;
    private RadioButton hard;
    private static final int REQ_STORAGE = 1;
    private boolean canGo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start= findViewById(R.id.startButton);
        easy= findViewById(R.id.easy);
        hard= findViewById(R.id.hard);

    }
    public void start(View v){
        Intent i = new Intent(this,QuizActivity.class );
        if(easy.isChecked()){
            i.putExtra("mode","easy");
        }else if(hard.isChecked()){
            i.putExtra("mode","hard");
        }else{
            Toast.makeText(this,"난이도를 결정하세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        onStorage();
        if(canGo){
            startActivity(i);
        }
    }
    public void edit(View v){
        onStorage();
        if(canGo){
            Intent i = new Intent(this,QuestionListActivity.class);
            startActivity(i);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode != REQ_STORAGE)return;
        if(permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i("message", "권한 있음.");
            canGo = true;
        }
        else{
            Toast.makeText(this,"STORAGE 권한이 없습니다.",Toast.LENGTH_SHORT).show();
        }
    }

    public void onStorage(){
        if(Build.VERSION.SDK_INT >=23){
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if(permission != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQ_STORAGE);
                return;
            }else{
                canGo = true;
            }
        }
    }

}
