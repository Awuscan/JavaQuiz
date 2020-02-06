package com.moch.javaquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class JavaQuizDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "JavaQuiz.db";
    private static final int DATABASE_VERSION = 11;

    private static final String TABLE_NAME_QUESTIONS = "Questions";
    private static final String ID = "id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_OPTION1 = "option1";
    private static final String COLUMN_OPTION2 = "option2";
    private static final String COLUMN_OPTION3 = "option3";
    private static final String COLUMN_OPTION4 = "option4";
    private static final String COLUMN_ANSWER1 = "answer1";
    private static final String COLUMN_ANSWER2 = "answer2";
    private static final String COLUMN_ANSWER3 = "answer3";
    private static final String COLUMN_ANSWER4 = "answer4";

    private static final String TABLE_NAME_NOTICES = "Notices";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_MESSAGE = "message";

    private SQLiteDatabase db;

    public JavaQuizDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                TABLE_NAME_QUESTIONS + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_OPTION1 + " TEXT, " +
                COLUMN_OPTION2 + " TEXT, " +
                COLUMN_OPTION3 + " TEXT, " +
                COLUMN_OPTION4 + " TEXT, " +
                COLUMN_ANSWER1 + " INTEGER, " +
                COLUMN_ANSWER2 + " INTEGER, " +
                COLUMN_ANSWER3 + " INTEGER, " +
                COLUMN_ANSWER4 + " INTEGER " +
                ")";

        final String SQL_CREATE_NOTICES_TABLE = "CREATE TABLE " +
                TABLE_NAME_NOTICES + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_MESSAGE + " TEXT " +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_NOTICES_TABLE);

        fillNoticesTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NOTICES);
        onCreate(db);
    }

    public void fillQuestionsTable(List<Question> questions) {
        //db.execSQL("DELETE FROM " + TABLE_NAME_QUESTIONS) ;
        for(Question question : questions){
            addQuestion(question);
        }
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("A is correct", "Java", "A.", "B", "C", "D", true, false, false, false);
        addQuestion(q1);
        Question q2 = new Question("B is correct", "Web", "A", "B", "C", "D", false, true, false, false);
        addQuestion(q2);
        Question q3 = new Question("C and D is correct", "Java", "A", "B", "C", "D", false, false, true, true);
        addQuestion(q3);
        Question q4 = new Question("A and D is correct", "Web", "A", "B", "C", "D", true, false, false, true);
        addQuestion(q4);
        Question q5 = new Question("A and D is correct", "Java", "A", "B", "C", "D", true, false, false, true);
        addQuestion(q5);
    }

    private void fillNoticesTable() {
        Notice n1 = new Notice("30-10-2019", "Aktualizacja pytań", "W bazie danych pojawiły się nowe pytania. Aktualna liczba pytań w bazie wynosi 120.");
        addNotice(n1);
        Notice n2 = new Notice("05-01-2020", "Termin Egzaminu", "Termin I: 05.01.2020, godzina 12, sala B300/B305 \nTermin II: 12.01.2020, godzina 12, sala B300/B305 \nProszę nie zapomnieć o dokumencie tożsamości!");
        addNotice(n2);
        Notice n3 = new Notice("08-01-2020", "Wyniki Egzaminu w I terminie", "Wyniki do wglądu w USOS'ie. \nZapraszam na poprawe.");
        addNotice(n3);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION, question.getQuestion());
        cv.put(COLUMN_CATEGORY, question.getCategory());
        cv.put(COLUMN_OPTION1, question.getOption1());
        cv.put(COLUMN_OPTION2, question.getOption2());
        cv.put(COLUMN_OPTION3, question.getOption3());
        cv.put(COLUMN_OPTION4, question.getOption4());
        cv.put(COLUMN_ANSWER1, boolToInt(question.isAnswer1()));
        cv.put(COLUMN_ANSWER2, boolToInt(question.isAnswer2()));
        cv.put(COLUMN_ANSWER3, boolToInt(question.isAnswer3()));
        cv.put(COLUMN_ANSWER4, boolToInt(question.isAnswer4()));
        db.insert(TABLE_NAME_QUESTIONS, null, cv);
    }

    private void addNotice(Notice notice) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, notice.getDate());
        cv.put(COLUMN_TITLE, notice.getTitle());
        cv.put(COLUMN_MESSAGE, notice.getMessage());
        db.insert(TABLE_NAME_NOTICES, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_QUESTIONS, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(COLUMN_QUESTION)));
                question.setCategory(c.getString(c.getColumnIndex(COLUMN_CATEGORY)));
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

    public List<Notice> getAllNotices() {
        List<Notice> noticesList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_NOTICES + " ORDER BY " + ID + " DESC", null);

        if (c.moveToFirst()) {
            do {
                Notice notice = new Notice();
                notice.setDate(c.getString(c.getColumnIndex(COLUMN_DATE)));
                notice.setTitle(c.getString(c.getColumnIndex(COLUMN_TITLE)));
                notice.setMessage(c.getString(c.getColumnIndex(COLUMN_MESSAGE)));
                noticesList.add(notice);
            } while (c.moveToNext());
        }

        c.close();
        return noticesList;
    }

    public List<Question> getCategoryQuestions(String category) {
        List<Question> questionList = new ArrayList<>();

            db = getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_QUESTIONS + " WHERE category=? ORDER BY RANDOM()", new String[]{category});

            if (c.moveToFirst()) {
                do {
                    Question question = new Question();
                    question.setQuestion(c.getString(c.getColumnIndex(COLUMN_QUESTION)));
                    question.setCategory(c.getString(c.getColumnIndex(COLUMN_CATEGORY)));
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

    public List<Question> getCategoryQuestions(String category, int number) {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_QUESTIONS + " WHERE category=? ORDER BY RANDOM() LIMIT ?", new String[]{category, Integer.toString(number) });

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(COLUMN_QUESTION)));
                question.setCategory(c.getString(c.getColumnIndex(COLUMN_CATEGORY)));
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

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COLUMN_CATEGORY + " FROM " + TABLE_NAME_QUESTIONS + " GROUP BY " + COLUMN_CATEGORY, null);

        if (c.moveToFirst()) {
            do {
                categories.add(c.getString(c.getColumnIndex(COLUMN_CATEGORY)));
            } while (c.moveToNext());
        }
        c.close();
        return categories;
    }

    private boolean intToBool(int i) {
        return i >= 1;
    }

    private int boolToInt(boolean state) {
        return state ? 1 : 0;
    }

}