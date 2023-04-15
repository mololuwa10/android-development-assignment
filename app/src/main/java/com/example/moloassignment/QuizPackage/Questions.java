package com.example.moloassignment.QuizPackage;

import com.example.moloassignment.R;

import java.util.ArrayList;
import java.util.List;

public class Questions {

    private static List<QuestionList> javaQuestions() {
        final List<QuestionList> questionLists = new ArrayList<>();

        QuestionList q1 = new QuestionList(0, "What is the size of float and double in java?", "32 and 64", "32 and 32", "64 and 64", "64 and 32", "32 and 64", "", false);
        questionLists.add(q1);

        questionLists.add(new QuestionList(0, "Number of primitive data types in Java are?", "6", "7", "8", "9", "8", "", false));
        questionLists.add(new QuestionList(0, "When an array is passed to a method, what does the method receive?", "The reference of the array", "The copy of the array", "Length of the array", "Copy of first element", "The reference of the array", "", false));
        questionLists.add(new QuestionList(R.drawable.java_image_question, "How many String Objects are created here", "One", "Two", "Three", "No object will be created", "One", "", true));

        return questionLists;
    }

    private static List<QuestionList> androidQuestions() {
        final List<QuestionList> questionLists = new ArrayList<>();

        questionLists.add(new QuestionList(0,"What is an Activity in Android?", "A graphical user interface (GUI)", "A background task that runs continuously", "A data storage mechanism", "A collection of methods", "A graphical user interface (GUI)", "", false));
        questionLists.add(new QuestionList(0,"What is the difference between a Service and an IntentService in Android?", "IntentService is used for handling asynchronous tasks", "Service is used for handling asynchronous tasks", "Service is used for handling synchronous tasks", "IntentService is used for handling synchronous tasks", "IntentService is used for handling asynchronous tasks", "", false));
        questionLists.add(new QuestionList(0,"What is a Fragment in Android?", "A reusable portion of a user interface in an Activity", "A service that runs in the background", "A separate application that can be installed and run on an Android device", "A type of layout", "A reusable portion of a user interface in an Activity", "", false));
        questionLists.add(new QuestionList(0,"What is an APK in Android?", "A file format for distributing and installing Android applications", "A file format for storing images", "A file format for storing audio", "A file format for storing video", "A file format for distributing and installing Android applications", "", false));
        questionLists.add(new QuestionList(0,"What is the purpose of an Adapter in Android?", "To provide a way to display data in a ListView or RecyclerView", "To provide a way to start a new Activity", "To provide a way to store data in a database", "To provide a way to bind views to data", "To provide a way to display data in a ListView or RecyclerView", "", false));
        questionLists.add(new QuestionList(0,"What is the purpose of the onResume() method in Android?", "To restart an Activity after it has been paused", "To stop an Activity", "To create a new instance of an Activity", "To pause an Activity", "To restart an Activity after it has been paused", "", false));
        questionLists.add(new QuestionList(0,"What is the difference between Serializable and Parcelable in Android?", "Parcelable is faster and more efficient than Serializable", "Serializable is faster and more efficient than Parcelable", "Parcelable can be used for inter-process communication (IPC)", "Serializable can be used for inter-process communication (IPC)", "Parcelable is faster and more efficient than Serializable", "", false));
        questionLists.add(new QuestionList(0,"What is the purpose of the Manifest file in Android?", "To provide essential information about the application to the Android system", "To provide a list of all the Activities in the application", "To provide a list of all the services in the application", "To provide a list of all the layout files in the application", "To provide essential information about the application to the Android system", "", false));
        questionLists.add(new QuestionList(0,"What is a ContentProvider in Android?", "A way to share data between applications", "A way to display data in a ListView", "A way to store data in a SQLite database", "A way to handle user input", "A way to share data between applications", "", false));
        questionLists.add(new QuestionList(0,"What is the purpose of the setContentView() method in Android?", "To set the user interface for an Activity", "To start a new Activity", "To bind views to data", "To store data in a database", "To set the user interface for an Activity", "", false));


        return questionLists;
    }
    public static List<QuestionList> getQuestions(String selectedTopicName) {
        switch(selectedTopicName) {
            case "java":
                return javaQuestions();
            default:
                return androidQuestions();
        }
    }
}
