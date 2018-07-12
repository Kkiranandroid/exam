package get.set;

/**
 * Created by soumyay on 5/4/2016.
 */
public class ChildGetSet {
    String examTitle;
    String examinationid;
    String duration;
    String count;
    String maxCount=";";


    String ranking;
    String totalscore;
    String examtakendate;
    String noofquestions;
    String correctanswers;
    String wronganswers;
    String totalpercentage;
    String examinationlevel;

    public String getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(String maxCount) {
        this.maxCount = maxCount;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public String getExaminationlevel() {
        return examinationlevel;
    }

    public void setExaminationlevel(String examinationlevel) {
        this.examinationlevel = examinationlevel;
    }



    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getExaminationid() {
        return examinationid;
    }

    public void setExaminationid(String examinationid) {
        this.examinationid = examinationid;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(String totalscore) {
        this.totalscore = totalscore;
    }

    public String getExamtakendate() {
        return examtakendate;
    }

    public void setExamtakendate(String examtakendate) {
        this.examtakendate = examtakendate;
    }

    public String getNoofquestions() {
        return noofquestions;
    }

    public void setNoofquestions(String noofquestions) {
        this.noofquestions = noofquestions;
    }

    public String getCorrectanswers() {
        return correctanswers;
    }

    public void setCorrectanswers(String correctanswers) {
        this.correctanswers = correctanswers;
    }

    public String getWronganswers() {
        return wronganswers;
    }

    public void setWronganswers(String wronganswers) {
        this.wronganswers = wronganswers;
    }



    public void setTotalpercentage(String totalpercentage) {
        this.totalpercentage = totalpercentage;
    }
}
