package company;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>Company</h1>
 * @author: Andras Tarlos
 * @version: 1.0
 * @date: 18.06.2022
 * <h2>Description</h2>
 * This is the model class that represents a company as its whole.
 */
@Getter
@Setter
public class Company {
    private String name;
    private final ArrayList<Department> departments = new ArrayList<>();

    /**
     * Basic constructor of company
     */
    public Company() {}

    /**
     * Advanced constructor of company
     * @param name of the company
     */
    public Company(String name){
        setCompanyName(name);
    }

    /**
     * GETTER of company name
     * @return a String
     */
    public String getCompanyName(){
        return name;
    }

    /**
     * SETTER of company name
     * @param name of company
     */
    public void setCompanyName(String name) {
        this.name = name;
    }

    /**
     * Adds a department object to the list of departments
     * @param department an object
     */
    public void addDepartment(Department department){
        departments.add(department);
    }

    /**
     * Returns the index-th element of the department list
     * @param index in the list
     * @return a Department
     */
    public Department getDepartment(int index){
        return departments.get(index);
    }

    /**
     * Returns the index-th elements name of the department list
     * @param index in the list
     * @return name of the department
     */
    public String getDepartmentsName(int index){
        return departments.get(index).getName();
    }

    /**
     * Removes a department from department list
     * @param department an object of Department
     */
    public void removeDepartment(Department department){
        departments.remove(department);
    }

    /**
     * Returns the number of departments this company has
     * @return int
     */
    @JsonIgnore
    public int getNumberOfDepartments(){
        return departments.size();
    }
}