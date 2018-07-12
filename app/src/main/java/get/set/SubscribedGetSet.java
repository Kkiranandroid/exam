package get.set;

import java.util.ArrayList;

/**
 * Created by shveta on 4/1/2016.
 */
public class SubscribedGetSet {
    public String coursename;
    public String courseimage;
    public String subscribedon;
    public String ranking;
    public String outof;

    public String subscribedCourseId;

    public String getSubscribedCourseId() {
        return subscribedCourseId;
    }

    public void setSubscribedCourseId(String subscribedCourseId) {
        this.subscribedCourseId = subscribedCourseId;
    }

    public ArrayList<ExamDetailGetSet> examDetails;

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getSubscribedon() {
        return subscribedon;
    }

    public void setSubscribedon(String subscribedon) {
        this.subscribedon = subscribedon;
    }

    public String getCourseimage() {
        return courseimage;
    }

    public void setCourseimage(String courseimage) {
        this.courseimage = courseimage;
    }

    public String getOutof() {
        return outof;
    }

    public void setOutof(String outof) {
        this.outof = outof;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public ArrayList<ExamDetailGetSet> getExamDetails() {
        return examDetails;
    }

    public void setExamDetails(ArrayList<ExamDetailGetSet> examDetails) {
        this.examDetails = examDetails;
    }
}
