package get.set;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by soumyay on 5/4/2016.
 */
public class ParentGetSet {
    private String examTitle="";
    private String examTakenOn="";
    private String numOfAttempts="";
    private  String rank="";
    private String Score="";
    public int visibility= View.GONE;
    ArrayList<ChildGetSet> childData;

    public ArrayList<ChildGetSet> getChildData() {
        return childData;
    }

    public void setChildData(ArrayList<ChildGetSet> childData) {
        this.childData = childData;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getExamTakenOn() {
        return examTakenOn;
    }

    public void setExamTakenOn(String examTakenOn) {
        this.examTakenOn = examTakenOn;
    }

    public String getNumOfAttempts() {
        return numOfAttempts;
    }

    public void setNumOfAttempts(String numOfAttempts) {
        this.numOfAttempts = numOfAttempts;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }


}
