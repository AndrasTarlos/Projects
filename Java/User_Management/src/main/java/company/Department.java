package company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import employees.HRPerson;
import employees.Person;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>Department</h1>
 * @author: Andras Tarlos
 * @version: 1.0
 * @date: 18.06.2022
 * <h2>Description</h2>
 * The model class of a department. A company can have multiple different departments.
 */
@Getter
@Setter
public class Department {
    private String name;
    private ArrayList<HRPerson> members = new ArrayList<>();

    /**
     * Basic constructor of department
     */
    public Department() {}

    /**
     * Advanced constructor of department
     * @param name of the department
     */
    public Department(String name){
        setName(name);
    }

    /**
     * Add a person to the member list
     * @param member an HRPerson
     */
    public void addMember(HRPerson member) {
        members.add(member);
    }

    /**
     * Returns a member of this department
     * @param index of List<HRPerson>
     * @return HRPerson
     */
    public HRPerson getMember(int index){
        return members.get(index);
    }

    /**
     * Removes a member from the List<HRPerson>
     * @param person to be removes (Object)
     */
    public void removeMember(HRPerson person) {
        members.remove(person);
    }

    /**
     * Returns the number of members in the list
     * @return size of members list
     */
    @JsonIgnore
    public int getNumberOfMembers(){
        return members.size();
    }
}