package com.moch.javaquiz;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MySQLDBHandler {

    private static final String urlWebService = "https://192.168.0.100/getAllQuestions.php";

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

    public String getJSON() {

        return null;
    }

    private List<Question> getQuestions(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Question question = new Question(
                    obj.getString(COLUMN_CATEGORY),
                    obj.getString(COLUMN_QUESTION),
                    obj.getString(COLUMN_OPTION1),
                    obj.getString(COLUMN_OPTION2),
                    obj.getString(COLUMN_OPTION3),
                    obj.getString(COLUMN_OPTION4),
                    intToBool(obj.getInt(COLUMN_ANSWER1)),
                    intToBool(obj.getInt(COLUMN_ANSWER2)),
                    intToBool(obj.getInt(COLUMN_ANSWER3)),
                    intToBool(obj.getInt(COLUMN_ANSWER4)));
            questions.add(question);
        }
        return questions;
    }

    private boolean intToBool(int i) {
        return i >= 1;
    }



}

