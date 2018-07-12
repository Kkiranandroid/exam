package get.set;

import Commmon.Methods.StretchedListView;

/**
 * Created by Administrator on 12/12/2016.
 */

public class SubScriptionGetSet {
    private String Id;
    private String CorseName;
    private String CourseImage;
    private String SubDate;
    private String TxnId;
    private String TxnAmt;
    private String examlevel;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCorseName() {
        return CorseName;
    }

    public void setCorseName(String corseName) {
        CorseName = corseName;
    }

    public String getCourseImage() {
        return CourseImage;
    }

    public void setCourseImage(String courseImage) {
        CourseImage = courseImage;
    }

    public String getSubDate() {
        return SubDate;
    }

    public void setSubDate(String subDate) {
        SubDate = subDate;
    }

    public String getTxnId() {
        return TxnId;
    }

    public void setTxnId(String txnId) {
        TxnId = txnId;
    }

    public String getTxnAmt() {
        return TxnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        TxnAmt = txnAmt;
    }

    public String getExamlevel() {
        return examlevel;
    }

    public void setExamlevel(String examlevel) {
        this.examlevel = examlevel;
    }
}
