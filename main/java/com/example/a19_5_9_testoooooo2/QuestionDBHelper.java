package com.example.a19_5_9_testoooooo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class QuestionDBHelper extends SQLiteOpenHelper {
    private ArrayList<QuestionBean> question;

    public QuestionDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        question = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table question (id integer primary key autoincrement, question text, score integer, answer integer, ex1 text, ex2 text, ex3 text, ex4 text, type text,time integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table question";
        db.execSQL(sql);
        onCreate(db);
    }
    public long insert(QuestionBean questionBean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question",questionBean.getQuestion());
        values.put("score",questionBean.getScore());
        values.put("answer",questionBean.getAnswer());
        values.put("ex1",questionBean.getEx1());
        values.put("ex2",questionBean.getEx2());
        values.put("ex3",questionBean.getEx3());
        values.put("ex4",questionBean.getEx4());
        values.put("type",questionBean.getType());
        values.put("time",System.currentTimeMillis());
        return db.insert("question",null,values);
    }
    public QuestionBean get(int id){
        SQLiteDatabase db = getReadableDatabase();
        String idStr = String.valueOf(id);
        Cursor cursor = db.query("question",null,"id=?",new String[]{idStr},null,null,null);
        if(cursor.moveToNext()){
            QuestionBean bean = new QuestionBean();
            bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            bean.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
            bean.setScore(cursor.getInt(cursor.getColumnIndex("score")));
            bean.setAnswer(cursor.getInt(cursor.getColumnIndex("answer")));
            bean.setEx1(cursor.getString(cursor.getColumnIndex("ex1")));
            bean.setEx2(cursor.getString(cursor.getColumnIndex("ex2")));
            bean.setEx3(cursor.getString(cursor.getColumnIndex("ex3")));
            bean.setEx4(cursor.getString(cursor.getColumnIndex("ex4")));
            bean.setType(cursor.getString(cursor.getColumnIndex("type")));
            bean.setTime(cursor.getLong(cursor.getColumnIndex("time")));
            return bean;
        } else {
            return null;
        }
    }
    public ArrayList<QuestionBean> get(){
        SQLiteDatabase db= getReadableDatabase();
        Cursor cursor = db.query("question",null,null,null,null,null,null);
        question.clear();
        while(cursor.moveToNext()) {
            QuestionBean bean = new QuestionBean();
            bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            bean.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
            bean.setScore(cursor.getInt(cursor.getColumnIndex("score")));
            bean.setAnswer(cursor.getInt(cursor.getColumnIndex("answer")));
            bean.setEx1(cursor.getString(cursor.getColumnIndex("ex1")));
            bean.setEx2(cursor.getString(cursor.getColumnIndex("ex2")));
            bean.setEx3(cursor.getString(cursor.getColumnIndex("ex3")));
            bean.setEx4(cursor.getString(cursor.getColumnIndex("ex4")));
            bean.setType(cursor.getString(cursor.getColumnIndex("type")));
            bean.setTime(cursor.getLong(cursor.getColumnIndex("time")));
            question.add(bean);
        }
            return question;
    }
    public int update(QuestionBean questionBean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question",questionBean.getQuestion());
        values.put("score",questionBean.getScore());
        values.put("answer",questionBean.getAnswer());
        values.put("ex1",questionBean.getEx1());
        values.put("ex2",questionBean.getEx2());
        values.put("ex3",questionBean.getEx3());
        values.put("ex4",questionBean.getEx4());
        values.put("type",questionBean.getType());
        values.put("time",System.currentTimeMillis());
        String id = String.valueOf(questionBean.getId());
        return db.update("question",values,"id=?",new String[] {id});
    }
    public int delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String idStr = String.valueOf(id);
        return db.delete("question","id=?", new String[]{idStr});

    }
}
