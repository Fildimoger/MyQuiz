package com.example.myquiz;

import android.provider.BaseColumns;

// Contract class for defining the database schema
public class QuizContract {

    // Default constructor
    public QuizContract(){}

    // Inner class representing the table structure
    public static class QuestionTable implements BaseColumns{

        // Table name
        public static final String TABLE_NAME = "quiz_questions";

        // Column names
        public static final String COLUMN_QUESTION = "questions";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer_nr";

    }
}
