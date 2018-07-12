package get.set;

/**
 * Created by kirankumar on 4/25/2016.
 */
public class CoursePopupGetSet {

    public String categoryid;
    public String categoryname;
    public boolean isSelected;

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
