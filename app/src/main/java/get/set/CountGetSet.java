package get.set;

import Commmon.Methods.StretchedListView;

/**
 * Created by soumyay on 5/11/2016.
 */
public class CountGetSet {
    String noOfattCount;
    String examLevel;
    String examId;
    String examdate;

    public String getExamdate() {
        return examdate;
    }

    public void setExamdate(String examdate) {
        this.examdate = examdate;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamLevel() {
        return examLevel;
    }

    public void setExamLevel(String examLevel) {
        this.examLevel = examLevel;
    }

    public String getNoOfattCount() {
        return noOfattCount;
    }

    public void setNoOfattCount(String noOfattCount) {
        this.noOfattCount = noOfattCount;
    }
}
