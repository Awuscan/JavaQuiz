package com.moch.javaquiz;

import com.moch.javaquiz.value_objects.Notice;
import com.moch.javaquiz.value_objects.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class jsonHandler {

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

    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_MESSAGE = "message";

    public List<Question> getQuestions(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Question question = new Question(
                    obj.getString(COLUMN_QUESTION),
                    obj.getString(COLUMN_CATEGORY),
                    obj.getString(COLUMN_OPTION1),
                    obj.getString(COLUMN_OPTION2),
                    obj.getString(COLUMN_OPTION3),
                    obj.getString(COLUMN_OPTION4),
                    stringToBool(obj.getString(COLUMN_ANSWER1)),
                    stringToBool(obj.getString(COLUMN_ANSWER2)),
                    stringToBool(obj.getString(COLUMN_ANSWER3)),
                    stringToBool(obj.getString(COLUMN_ANSWER4)));
            questions.add(question);
        }
        return questions;
    }

    public List<Notice> getNotices(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<Notice> notices = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Notice notice = new Notice(
                    obj.getString(COLUMN_DATE),
                    obj.getString(COLUMN_TITLE),
                    obj.getString(COLUMN_MESSAGE));
            notices.add(notice);
        }
        return notices;
    }

    private boolean stringToBool(String s) {
        return !s.equals("0");
    }

}



