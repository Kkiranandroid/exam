package get.set;

/**
 * Created by soumyay on 4/11/2016.
 */
public class RecommendedGetSet {
    private String courseImage;
    private String courseName;
    private String coursePrice;
    private  String courseUsers;
    private  String rating;
    private String courseid;

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseUsers() {
        return courseUsers;
    }

    public void setCourseUsers(String courseUsers) {
        this.courseUsers = courseUsers;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
