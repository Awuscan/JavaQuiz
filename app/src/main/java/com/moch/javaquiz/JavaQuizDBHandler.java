package com.moch.javaquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class JavaQuizDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "JavaQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME  = "Questions";
    private static final String ID = "id";
    private static final String COLUMN_QUESTION  = "question";
    private static final String COLUMN_OPTION1  = "option1";
    private static final String COLUMN_OPTION2 = "option2";
    private static final String COLUMN_OPTION3  ="option3";
    private static final String COLUMN_OPTION4  = "option4";
    private static final String COLUMN_ANSWER1  = "answer1";
    private static final String COLUMN_ANSWER2  = "answer2";
    private static final String COLUMN_ANSWER3  = "answer3";
    private static final String COLUMN_ANSWER4  = "answer4";
    private static final String COLUMN_IMAGE  = "image";

    private SQLiteDatabase db;

    public JavaQuizDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                TABLE_NAME + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION + " TEXT, " +
                COLUMN_OPTION1 + " TEXT, " +
                COLUMN_OPTION2 + " TEXT, " +
                COLUMN_OPTION3 + " TEXT, " +
                COLUMN_OPTION4 + " TEXT, " +
                COLUMN_ANSWER1 + " INTEGER," +
                COLUMN_ANSWER2 + " INTEGER," +
                COLUMN_ANSWER3 + " INTEGER," +
                COLUMN_ANSWER4 + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("A is correct","A", "B", "C", "D", true, false, false, false);
        addQuestion(q1);
        Question q2 = new Question("B is correct", "A", "B", "C", "D",false, true, false, false);
        addQuestion(q2);
        Question q3 = new Question("C and D is correct", "A", "B", "C", "D",false, false, true, true);
        addQuestion(q3);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION, question.getQuestion());
        cv.put(COLUMN_OPTION1, question.getOption1());
        cv.put(COLUMN_OPTION2, question.getOption2());
        cv.put(COLUMN_OPTION3, question.getOption3());
        cv.put(COLUMN_OPTION4, question.getOption4());
        cv.put(COLUMN_ANSWER1, boolToInt(question.isAnswer1()));
        cv.put(COLUMN_ANSWER2, boolToInt(question.isAnswer2()));
        cv.put(COLUMN_ANSWER3, boolToInt(question.isAnswer3()));
        cv.put(COLUMN_ANSWER4, boolToInt(question.isAnswer4()));
        db.insert(TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(COLUMN_OPTION4)));
                question.setAnswer1(intToBool(c.getInt(c.getColumnIndex(COLUMN_ANSWER1))));
                question.setAnswer2(intToBool(c.getInt(c.getColumnIndex(COLUMN_ANSWER2))));
                question.setAnswer3(intToBool(c.getInt(c.getColumnIndex(COLUMN_ANSWER3))));
                question.setAnswer4(intToBool(c.getInt(c.getColumnIndex(COLUMN_ANSWER4))));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    private boolean intToBool(int i){
        return i == 1;
    }

    private int boolToInt(boolean state){
        return state ? 1 : 0;
    }

}
