package get.set;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by soumyay on 4/13/2016.
 */
public class QuestionGetSet implements Serializable {
    String question;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    String answer5;
    String correctAnswer;
    String questionid;
    String noofchoices;
    String noofquestions;
    String optionToGoBack;
    String showcorrectAns;
    String status;
    String imagepath1;
    String imagepath2;
    String imagepath3;
    String imagepath4;
    String imagepath5;

    public String getImagepath2() {
        return imagepath2;
    }

    public void setImagepath2(String imagepath2) {
        this.imagepath2 = imagepath2;
    }

    public String getImagepath3() {
        return imagepath3;
    }

    public void setImagepath3(String imagepath3) {
        this.imagepath3 = imagepath3;
    }

    public String getImagepath4() {
        return imagepath4;
    }

    public void setImagepath4(String imagepath4) {
        this.imagepath4 = imagepath4;
    }

    public String getImagepath5() {
        return imagepath5;
    }

    public void setImagepath5(String imagepath5) {
        this.imagepath5 = imagepath5;
    }

    public String getImagepath1() {
        return imagepath1;
    }

    public void setImagepath1(String  imagepath) {
        this.imagepath1 = imagepath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShowcorrectAns() {
        return showcorrectAns;
    }

    public void setShowcorrectAns(String showcorrectAns) {
        this.showcorrectAns = showcorrectAns;
    }

    public String getOptionToGoBack() {
        return optionToGoBack;
    }

    public void setOptionToGoBack(String optionToGoBack) {
        this.optionToGoBack = optionToGoBack;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getNoofchoices() {
        return noofchoices;
    }

    public void setNoofchoices(String noofchoices) {
        this.noofchoices = noofchoices;
    }

    public String getNoofquestions() {
        return noofquestions;
    }

    public void setNoofquestions(String noofquestions) {
        this.noofquestions = noofquestions;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }
}
