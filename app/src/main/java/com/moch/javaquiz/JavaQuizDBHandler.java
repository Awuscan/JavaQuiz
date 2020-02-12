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
    private static final int DATABASE_VERSION = 19;

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
                COLUMN_IMAGE + " TEXT, " +
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
        db.execSQL("DELETE FROM " + TABLE_NAME_QUESTIONS);
        for (Question question : questions) {
            addQuestion(question);
        }
    }

    public void fillNoticesTable(List<Notice> notices) {
        db.execSQL("DELETE FROM " + TABLE_NAME_NOTICES);
        for (Notice notice : notices) {
            addNotice(notice);
        }
    }

    private void fillTasksTable() {
        Task t0 = new Task(1, "Temat: HTML/CSS", "", "");
        addTask(t0);

        Task t1 = new Task(1, "1. Layout strony.\n W oparciu o teksty źródłowe zamieszczone w punktach 1.1 - 1.3 zbudować strony o strukturze jak na rysunku stosując różne techniki:\n" +
                "- bloki <div>,\n" +
                "- znaczniki semantyczne języka HTML 5,\n" +
                "- tabele", "labs/1.png", "");
        addTask(t1);

        Task t2 = new Task(1, "1.1. Layout - zastosowanie znacznika <div>, - sposób preferowany\n", "", "!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "   <meta charset=\"utf-8\">\n" +
                "<title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "   <div id=\"container\" style=\"width:100%\">\n" +
                "      <div id=\"header\" style=\"background-color:#CCC;text-align:center;\">\n" +
                "         <h1 style=\"margin-bottom:0;\">Title</h1>\n" +
                "      </div>\n" +
                "      <div id=\"left menu\" style=\"background-color:#DDD;height:200px;width:20%;float:left;\">\n" +
                "         Left menu\n" +
                "      </div>\n" +
                "      <div id=\"content\" style=\"background-color:#FFF;height:200px;width:60%;float:left;\">\n" +
                "         Content\n" +
                "      </div>\n" +
                "      <div id=\"footer\" style=\"background-color:#CCC;clear:both;text-align:center;\">\n" +
                "         <img src=\"F_icon.svg\" height=\"40\" width=\"40\">\n" +
                "         &nbsp;&nbsp;&nbsp;\n" +
                "         <img src=\"T_icon.svg\" height=\"40\" width=\"40\">\n" +
                "      </div>\n" +
                "   </div>\n" +
                "</body>\n" +
                "</html>");
        addTask(t2);

        Task t3 = new Task(1, "1.2. Layout - zastosowanie znaczników semantycznych języka HTML 5 - sposób preferowany\n", "", "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "   <meta charset=\"utf-8\">\n" +
                "   <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "   <div id=\"container\" style=\"width:100%\">\n" +
                "      <header style=\"background-color:#CCC;\">\n" +
                "         <h1 style=\"margin-bottom:0;text-align:center;\">Title</h1>\n" +
                "      </header>\n" +
                "      <nav style=\"background-color:#DDD;height:200px;width:20%;float:left;\">\n" +
                "         Left menu\n" +
                "      </nav>\n" +
                "      <section style=\"background-color:#FFF;height:200px;width:80%;float:left;\"> " +
                "         Content\n" +
                "      </section>\n" +
                "      <footer style=\"background-color:#CCC;clear:both;text-align:center;\">\n" +
                "         <div><img src=\"F_icon.svg\" height=\"40\" width=\"40\">\n" +
                "          &nbsp;&nbsp;&nbsp;\n" +
                "         <img src=\"T_icon.svg\" height=\"40\" width=\"40\"></div>\n" +
                "      </footer>\n" +
                "   </div>\n" +
                "</body>\n" +
                "</html>");
        addTask(t3);

        Task t4 = new Task(1, "1.3. Layout - zastosowanie tabel - sposób przestarzały!\n", "", "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "   <meta charset=\"utf-8\">\n" +
                "   <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "   <table style=\"width:100%; border:0px solid; border-collapse:collapse;\">\n" +
                "      <tr>\n" +
                "         <td colspan=\"2\" style=\"background-color:#CCC;text-align:center;\">\n" +
                "            <h1 style=\"margin-bottom:0;margin-top:0;\">Title</h1>\n" +
                "         </td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "         <td style=\"background-color:#DDD;height:200px; width:20%;\">\n" +
                "            Left menu </td>\n" +
                "         <td style=\"background-color:#FFF;height:200px;width:80%;\">\n" +
                "            Content\n" +
                "         </td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "         <td colspan=\"2\" style=\"background-color:#CCC;text-align:center;\">\n" +
                "            <img src=\"F_icon.svg\" height=\"40\" width=\"40\">\n" +
                "            &nbsp;&nbsp;&nbsp;\n" +
                "            <img src=\"T_icon.svg\" height=\"40\" width=\"40\">\n" +
                "         </td>\n" +
                "      </tr>\n" +
                "   </table>\n" +
                "</body>\n" +
                "</html>");
        addTask(t4);

        Task t5 = new Task(1, "F_icon.svg", "", "<svg version=\"1.1\" id=\"Layer_1\" xmlns=http://www.w3.org/2000/svg xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n" +
                "x=\"0px\" y=\"0px\"\n" +
                "width=\"66.72325px\" height=\"66.72325px\" viewBox=\"0 0 66.72325 66.72325\" enable-background=\"new 0 0 66.7232566.72325\" xml:space=\"preserve\">\n" +
                "<path fill=\"#3C5A99\"d=\"M60.874,64.43c1.963,0,3.556-1.592,3.556-3.556V3.556C64.43,1.592,62.837,0,60.874,0H3.556C1.592,0,0,1.592,0,3.556v57.318c0,1.963,1.592,3.556,3.556,3.556H60.874z\"/>\n" +
                "<path fill=\"#FFFFFF\"d=\"M44.455,64.43V39.479h8.375l1.254-9.724h-9.629v-6.208c0-2.815,0.782-4.733,4.819-4.733l5.148-0.002v-8.697c-0.891-0.119-3.946-0.383-7.503-0.383c-7.424,0-12.506,4.532-12.506,12.853v7.171h-8.396v9.724h8.396V64.43H44.455z\"/>\n" +
                "</svg>");
        addTask(t5);

        Task t6 = new Task(1, "T_icon.svg", "", "<svg version=\"1.1\" id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\"\n" +
                "xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"75px\" height=\"61.04725px\"viewBox=\"0 0 75 61.04725\" enable-background=\"new 0 0 75 61.04725\" xml:space=\"preserve\">\n" +
                "<g transform=\"translate(-539.18 -568.86)\">\n" +
                "<path fill=\"#1DA1F2\"d=\"M562.61,629.407c28.115,0,43.49-23.292,43.49-43.489c0-0.662-0.014-1.32-0.043-1.977c2.984-2.157,5.578-4.85,7.625-7.915c-2.739,1.218-5.686,2.037-8.778,2.407c3.156-1.893,5.579-4.886,6.722-8.455c-2.954,1.751-6.224,3.023-9.706,3.711c-2.79-2.972-6.76-4.829-11.157-4.829c-8.441,0-15.286,6.845-15.286,15.282c0,1.2,0.134,2.366,0.396,3.485c-12.704-0.639-23.969-6.722-31.508-15.97c-1.313,2.259-2.069,4.883-2.069,7.683c0,5.303,2.698,9.984,6.802,12.723c-2.508-0.077-4.863-0.766-6.922-1.911c-0.002,0.063-0.002,0.127-0.002,0.195c0,7.402,5.269,13.583,12.263,14.983c-1.284,0.35-2.636,0.538-4.03,0.538c-0.983,0-1.941-0.097-2.873-0.276c1.946,6.073,7.589,10.493,14.279,10.616c-5.232,4.101-11.822,6.543-18.984,6.543c-1.232,0-2.45-0.07-3.646-0.211C545.945,626.877,553.977,629.406,562.61,629.407\"/></g>\n" +
                "</svg");
        addTask(t6);

        Task t7 = new Task(2, "Temat: JavaScript", "", "");
        addTask(t7);

        Task t8 = new Task(2, "• Wyłączona obsługa skryptów", "", "<head>\n" +
                "<style>\n" +
                "   div.js _ sup _ depend{color:green;}\n" +
                "</style>\n" +
                "<noscript>\n" +
                "<style>\n" +
                "   div.js_supp _ depend{color:red;}\n" +
                "</style>\n" +
                "</noscript>\n" +
                "</head>\n" +
                "<body>\n" +
                "   <div class=\"js _ sup _ depend\"><h2>Your browser <span id=\"js _ depend\">" +
                "      <noscript>does not support JavaScript!!!</noscript></span></h2></div>\n" +
                "   <script type=\"text/javascript\">\n" +
                "      document.getElementById(\"js _ depend\").innerHTML = \"support JavaScript!\";\n" +
                "   </script>\n" +
                "</body>");
        addTask(t8);

        Task t9 = new Task(2, "• Zmienne globalne i lokalne", "", "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "   <script type=\"text/javascript\">\n" +
                "      var a = 20;\n" +
                "      document.write(\"a = \" + a + \"<br />\");\n" +
                "      {\n" +
                "         let a = 2;\n" +
                "         document.write(\"a = \" + a + \"<br />\");\n" +
                "      }\n" +
                "      document.write(\"a = \" + a + \"<br />\");\n" +
                "      var b = 'OK';\n" +
                "      var c = true;\n" +
                "      function myFunction() {\n" +
                "         c = 55;\n" +
                "         var a = 1; //Sprawdzić, co stanie się po usunięciu \"var\" przed \"a\"\n" +
                "         var d = 'NO';\n" +
                "         result = a + c;\n" +
                "         return result;\n" +
                "      }\n" +
                "      var fun = myFunction();\n" +
                "      document.write(\"a &rarr; \" + typeof (a) + \"<br />\");\n" +
                "      document.write(\"b &rarr; \" + typeof (b) + \"<br />\");\n" +
                "      document.write(\"c &rarr; \" + typeof (c) + \"<br />\" + \"<br />\");\n" +
                "      document.write(\"a = \" + a + \"<br />\");\n" +
                "      document.write(\"a + c = \", fun + \"<br />\");\n" +
                "      document.write(\"c = \" + c + \"<br />\");\n" +
                "      document.write(\"b = \" + b + \"<br />\");\n" +
                "      document.write(\"d = \" + d + \"<br />\"); //Czy ta linia jest widoczna w przeglądarce?\n" +
                "   </script>\n" +
                "   <br>Strona zmodyfikowana:\n" +
                "   <script type=\"text/javascript\">\n" +
                "      document.write(document.lastModified);\n" +
                "   </script>\n" +
                "</body>\n" +
                "</html>");
        addTask(t9);

    }

    private void fillQuestionsTable() {
        Log.d("Response", "fillQuestionsTable");

        Question q1 = new Question("Pytanie\nPoprawna odpowiedz: A", "Java", "Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D", true, false, false, false);
        addQuestion(q1);
        Question q2 = new Question("Pytanie\nPoprawna odpowiedz: B i C", "Java", "Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D", false, true, true, false);
        addQuestion(q2);
        Question q3 = new Question("Pytanie\nPoprawna odpowiedz: C", "Java", "Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D", false, false, true, false);
        addQuestion(q3);
        Question q4 = new Question("Pytanie\nPoprawna odpowiedz: A, B i D", "Java", "Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D", true, true, false, true);
        addQuestion(q4);
        Question q5 = new Question("Pytanie\nPoprawna odpowiedz: C i D", "Java", "Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D", false, false, true, true);
        addQuestion(q5);
        Question q6 = new Question("Pytanie\nPoprawna odpowiedz: B", "WEB", "Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D", false, true, false, false);
        addQuestion(q6);
        Question q7 = new Question("Pytanie\nPoprawna odpowiedz: A, B, C i D", "WEB", "Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D", true, true, true, true);
        addQuestion(q7);
        Question q8 = new Question("Pytanie\nPoprawna odpowiedz: D", "WEB", "Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D", false, false, false, true);
        addQuestion(q8);
        Question q9 = new Question("Pytanie\nPoprawna odpowiedz: A i D", "WEB", "Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D", true, false, false, true);
        addQuestion(q9);
        Question q10 = new Question("Pytanie\nPoprawna odpowiedz: B i C", "WEB", "Odpowiedź A", "Odpowiedź B", "Odpowiedź C", "Odpowiedź D", false, true, true, false);
        addQuestion(q10);


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
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_QUESTIONS + " WHERE " + COLUMN_CATEGORY + "=? ORDER BY RANDOM() LIMIT ?", new String[]{category, Integer.toString(number)});

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

    public List<String> getAllCategoriesAndCount() {
        List<String> categories = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COLUMN_CATEGORY + ", COUNT(*) " + " FROM " + TABLE_NAME_QUESTIONS + " GROUP BY " + COLUMN_CATEGORY, null);

        if (c.moveToFirst()) {
            do {
                categories.add(c.getString(c.getColumnIndex(COLUMN_CATEGORY)) + ", " + String.valueOf(c.getInt(1) + " pytań" ));
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
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_TASKS + " WHERE " + COLUMN_LAB + "=? ORDER BY " + ID + " ASC", new String[]{Integer.toString(labNumber)});

        if (c.moveToFirst()) {
            do {
                Task task = new Task();
                task.setLab(c.getInt(c.getColumnIndex(COLUMN_LAB)));
                task.setTask(c.getString(c.getColumnIndex(COLUMN_TASK)));
                task.setImage(c.getString(c.getColumnIndex(COLUMN_IMAGE)));
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