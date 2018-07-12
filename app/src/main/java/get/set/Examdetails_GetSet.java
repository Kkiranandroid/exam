package get.set;

/**
 * Created by soumyay on 4/20/2016.
 */
public class Examdetails_GetSet {

String examTitle;
    String examDuration;
    String examId;
String maxtimeusercantakeexam;
    String examtype;


    public String getExamtype() {
        return examtype;
    }

    public void setExamtype(String examtype) {
        this.examtype = examtype;
    }

    public String getMaxtimeusercantakeexam() {
        return maxtimeusercantakeexam;
    }

    public void setMaxtimeusercantakeexam(String maxtimeusercantakeexam) {
        this.maxtimeusercantakeexam = maxtimeusercantakeexam;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(String examDuration) {
        this.examDuration = examDuration;
    }
}
