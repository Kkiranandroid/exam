package get.set;

/**
 * Created by Administrator on 1/28/2017.
 */

public class CategoryGesetClassNew {

    public String categoryId;
    public String categoryName;
    public String categoryImage;

    public String categoryRatings;
    public String categorytotalfees;
    public String categorybasicfees;
    public  String courseId;
    public String categoryintermediatefees;
    public String categoryadvancefees;
    public String categoryNoOfCourse;
    public String categoryTitle;
    public String categoryUsers;
    public CategoryGesetClassNew(){

    }

    public CategoryGesetClassNew( String categoryId,String categoryName,String categoryImage, String categorytotalfees,String categorybasicfees,String categoryintermediatefees,String categoryadvancefees,String categoryRatings,String categoryUsers){
        this.categoryId=categoryId;
        this.categoryName=categoryName;
        this.categoryImage=categoryImage;

        this.categoryRatings=categoryRatings;
        this.categorytotalfees=categorytotalfees;
        this.categorybasicfees=categorybasicfees;

        this.categoryintermediatefees=categoryintermediatefees;
        this.categoryadvancefees=categoryadvancefees;
        this.categoryUsers=categoryUsers;

    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCategoryNoOfCourse() {
        return categoryNoOfCourse;
    }

    public void setCategoryNoOfCourse(String categoryNoOfCourse) {
        this.categoryNoOfCourse = categoryNoOfCourse;
    }



    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategorytotalfees() {
        return categorytotalfees;
    }

    public void setCategorytotalfees(String categorytotalfees) {
        this.categorytotalfees = categorytotalfees;
    }

    public String getCategorybasicfees() {
        return categorybasicfees;
    }

    public void setCategorybasicfees(String categorybasicfees) {
        this.categorybasicfees = categorybasicfees;
    }

    public String getCategoryintermediatefees() {
        return categoryintermediatefees;
    }

    public void setCategoryintermediatefees(String categoryintermediatefees) {
        this.categoryintermediatefees = categoryintermediatefees;
    }

    public String getCategoryadvancefees() {
        return categoryadvancefees;
    }

    public void setCategoryadvancefees(String categoryadvancefees) {
        this.categoryadvancefees = categoryadvancefees;
    }




    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }



    public String getCategoryRatings() {
        return categoryRatings;
    }

    public void setCategoryRatings(String categoryRatings) {
        this.categoryRatings = categoryRatings;
    }

    public String getCategoryUsers() {
        return categoryUsers;
    }

    public void setCategoryUsers(String categoryUsers) {
        this.categoryUsers = categoryUsers;
    }


    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if(obj instanceof CategoryGetSetClass)
        {
            CategoryGetSetClass temp = (CategoryGetSetClass) obj;
            if(this.categoryName == temp.categoryName&& this.categoryId== temp.categoryId && this.categoryImage == temp.categoryImage && this.categorytotalfees == temp.categorytotalfees&&
                    this.categorybasicfees == temp.categorybasicfees&&this.categoryintermediatefees == temp.categoryintermediatefees
                    &&this.categoryadvancefees == temp.categoryadvancefees
                    &&this.categoryRatings == temp.categoryRatings
                    &&this.categoryUsers == temp.categoryUsers)
                return true;
        }
        return false;

    }
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub

        // return (this.title.hashCode() + this.author.hashCode() + this.url.hashCode() + this.description.hashCode());
        return (this.categoryName.hashCode() + this.categoryId.hashCode()+this.categoryImage.hashCode() +this.categorytotalfees.hashCode() +
                this.categorybasicfees.hashCode() +this.categoryintermediatefees.hashCode()+
                this.categoryadvancefees.hashCode() +
                this.categoryRatings.hashCode()+ this.categoryUsers.hashCode());
    }
}
