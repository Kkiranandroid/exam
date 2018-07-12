package get.set;

/**
 * Created by ramagouda on 4/25/2016.
 */
public class FaqGetSet {
    String quesion;
    String answer;
    String faqImage="";

    public void setQuesion(String quesion) {
        this.quesion = quesion;
    }
    public String getQuesion() {
        return quesion;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getAnswer() {
        return answer;
    }

    public void setFaqImage(String faqImage) {
        this.faqImage = faqImage;
    }

    public String getFaqImage() {
        return faqImage;
    }
}
