package get.set;

import java.util.ArrayList;

public class HomeCategoryGetSet {

	public String categoryId;
	public String categroyName;
	public String coursecount;

	public String getCoursecount() {
		return coursecount;
	}

	public void setCoursecount(String coursecount) {
		this.coursecount = coursecount;
	}

	public ArrayList<CategoryGetSetClass> menList =  new ArrayList<CategoryGetSetClass>();


	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategroyName() {
		return categroyName;
	}

	public void setCategroyName(String categroyName) {
		this.categroyName = categroyName;
	}
	
	
	
	public ArrayList<CategoryGetSetClass> getMenList() {
		return menList;
	}

	public void setMenList(ArrayList<CategoryGetSetClass> menList) {
		this.menList = menList;
	}
}
