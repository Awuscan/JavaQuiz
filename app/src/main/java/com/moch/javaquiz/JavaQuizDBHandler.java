package com.moch.javaquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.moch.javaquiz.value_objects.Notice;
import com.moch.javaquiz.value_objects.Question;
import com.moch.javaquiz.value_objects.Task;

import java.util.ArrayList;
import java.util.List;

public class JavaQuizDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "JavaQuiz.db";
    private static final int DATABASE_VERSION = 13;

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

    private static final String TABLE_NAME_TASKS = "Tasks";
    private static final String COLUMN_LAB = "lab";
    private static final String COLUMN_TASK = "task";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_CODE = "code";

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

        final String SQL_CREATE_TASKS_TABLE = "CREATE TABLE " +
                TABLE_NAME_TASKS + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LAB + " INT, " +
                COLUMN_TASK + " TEXT, " +
                COLUMN_IMAGE + " INT, " +
                COLUMN_CODE + " TEXT " +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_NOTICES_TABLE);
        db.execSQL(SQL_CREATE_TASKS_TABLE);

        fillTasksTable();
        fillQuestionsTable();
        fillNoticesTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NOTICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TASKS);
        onCreate(db);
    }

    public void fillQuestionsTable(List<Question> questions) {
        db.execSQL("DELETE FROM " + TABLE_NAME_QUESTIONS) ;
        for(Question question : questions){
            addQuestion(question);
        }
    }

    public void fillNoticesTable(List<Notice> notices) {
        db.execSQL("DELETE FROM " + TABLE_NAME_NOTICES) ;
        for(Notice notice : notices){
            addNotice(notice);
        }
    }

    private void fillTasksTable() {
        Task t1 = new Task(1,"1.1",0,"<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"container\" style=\"width:100%\">\n" +
                "<div id=\"header\" style=\"background-color:#CCC;text-align:center;\">\n" +
                "<h1 style=\"margin-bottom:0;\">Title</h1>\n" +
                "</div>\n" +
                "<div id=\"left menu\" style=\"background-color:#DDD;height:200px;width:20%;float:left;\">\n" +
                "Left menu\n" +
                "</div>\n" +
                "<div id=\"content\" style=\"background-color:#FFF;height:200px;width:60%;\n" +
                "float:left;\">\n" +
                "Content\n" +
                "</div>\n" +
                "<div id=\"footer\" style=\"background-color:#CCC;clear:both;\n" +
                "text-align:center;\">\n" +
                "<img src=\"F_icon.svg\" height=\"40\" width=\"40\">\n" +
                "&nbsp;&nbsp;&nbsp;\n" +
                "<img src=\"T_icon.svg\" height=\"40\" width=\"40\">\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
        addTask(t1);
        Task t2 = new Task(1,"1.2",0,"XD");
        addTask(t2);
        Task t3 = new Task(2,"2.1",0,"<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"container\" style=\"width:100%\">\n" +
                "<header style=\"background-color:#CCC;\">\n" +
                "<h1 style=\"margin-bottom:0;text-align:center;\">Title</h1>\n" +
                "</header>\n" +
                "<nav style=\"background-color:#DDD;height:200px;width:20%;float:left;\">\n" +
                "Left menu\n" +
                "</nav>\n" +
                "<section style=\"background-color:#FFF;height:200px;width:80%;float:left;\"> Content\n" +
                "</section>\n" +
                "<footer style=\"background-color:#CCC;clear:both;text-align:center;\">\n" +
                "<div><img src=\"F_icon.svg\" height=\"40\" width=\"40\">\n" +
                "&nbsp;&nbsp;&nbsp;\n" +
                "<img src=\"T_icon.svg\" height=\"40\" width=\"40\"></div>\n" +
                "</footer>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
        addTask(t3);
        Task t4 = new Task(2,"2.2",0,"XD");
        addTask(t4);

    }

    private void fillQuestionsTable() {
        Log.d("Response", "fillQuestionsTable");

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

    private void addTask(Task task) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LAB, task.getLab());
        cv.put(COLUMN_TASK, task.getTask());
        cv.put(COLUMN_IMAGE, task.getImage());
        cv.put(COLUMN_CODE, task.getCode());
        db.insert(TABLE_NAME_TASKS, null, cv);
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
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_QUESTIONS + " WHERE " + COLUMN_CATEGORY + "=? ORDER BY RANDOM()", new String[]{category});

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
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_QUESTIONS + " WHERE " + COLUMN_CATEGORY + "=? ORDER BY RANDOM() LIMIT ?", new String[]{category, Integer.toString(number) });

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

    public List<Integer> getAllLabs() {
        List<Integer> labs = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COLUMN_LAB + " FROM " + TABLE_NAME_TASKS + " GROUP BY " + COLUMN_LAB, null);

        if (c.moveToFirst()) {
            do {
                labs.add(c.getInt(c.getColumnIndex(COLUMN_LAB)));
            } while (c.moveToNext());
        }
        c.close();
        return labs;
    }

    public List<Task> getLabTasks(int labNumber) {
        List<Task> taskList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_TASKS + " WHERE " + COLUMN_LAB + "=? ORDER BY " + COLUMN_TASK + " ASC", new String[]{Integer.toString(labNumber)});

        if (c.moveToFirst()) {
            do {
                Task task = new Task();
                task.setLab(c.getInt(c.getColumnIndex(COLUMN_LAB)));
                task.setTask(c.getString(c.getColumnIndex(COLUMN_TASK)));
                task.setImage(c.getInt(c.getColumnIndex(COLUMN_IMAGE)));
                task.setCode(c.getString(c.getColumnIndex(COLUMN_CODE)));
                taskList.add(task);
            } while (c.moveToNext());
        }

        c.close();
        return taskList;
    }


    private boolean intToBool(int i) {
        return i >= 1;
    }

    private int boolToInt(boolean state) {
        return state ? 1 : 0;
    }

}