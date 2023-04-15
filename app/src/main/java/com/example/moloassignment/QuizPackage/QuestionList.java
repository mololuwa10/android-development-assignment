package com.example.moloassignment.QuizPackage;

public class QuestionList {
    String question, firstOption, secondOption, thirdOption, fourthOption, answer;
    String selectedAnswer;
    int image;
    private boolean hasImage;
    public QuestionList(int image, String question, String firstOption, String secondOption, String thirdOption, String fourthOption, String answer, String selectedAnswer, boolean hasImage) {
        this.image = image;
        this.question = question;
        this.firstOption = firstOption;
        this.secondOption = secondOption;
        this.thirdOption = thirdOption;
        this.fourthOption = fourthOption;
        this.answer = answer;
        this.selectedAnswer = selectedAnswer;
        this.hasImage = hasImage;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public String getQuestion() {
        return question;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFirstOption() {
        return firstOption;
    }

    public String getSecondOption() {
        return secondOption;
    }

    public String getThirdOption() {
        return thirdOption;
    }

    public String getFourthOption() {
        return fourthOption;
    }

    public String getAnswer() {
        return answer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }
}
