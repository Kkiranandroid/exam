package get.set;

/**
 * Created by shveta on 4/1/2016.
 */
public class ExamDetailGetSet {
    public String title;
    public String examtakendate;
    public String totalscore;
    public String ranking;
    public String noofattempts;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExamtakendate() {
        return examtakendate;
    }

    public void setExamtakendate(String examtakendate) {
        this.examtakendate = examtakendate;
    }

    public String getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(String totalscore) {
        this.totalscore = totalscore;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getNoofattempts() {
        return noofattempts;
    }

    public void setNoofattempts(String noofattempts) {
        this.noofattempts = noofattempts;
    }
}
