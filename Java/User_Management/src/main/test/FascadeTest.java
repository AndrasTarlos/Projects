import company.Company;
import company.Department;
import employees.HRPerson;
import fascades.Fascade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DatahandlerJSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FascadeTest {

    Fascade fascade;
    DatahandlerJSON datahandlerJSON;
    Company company;

    @BeforeEach
    void setUp() {
        datahandlerJSON = DatahandlerJSON.getDatahandlerJSONInstance();
        fascade = datahandlerJSON.readFascadeJSON();
        company = datahandlerJSON.readCompanyJSON();
        fascade.setCompany(company);
    }

    @Test
    @DisplayName("T01 Check if department list is not null")
    void getAllDepartment1() {
        ArrayList<Department> list = fascade.getAllDepartment();
        assertNotNull(list);
    }

    @Test
    @DisplayName("T02 Check if department list contains items")
    void getAllDepartment2() {
        ArrayList<Department> list = fascade.getAllDepartment();
        assertTrue(list.size() > 0);
    }

    @Test
    @DisplayName("T03 Check if the company name is correctly saved in the JSON file")
    void getCompanyName() {
        assertEquals(company.getCompanyName(), "Our company");
    }

    @Test
    @DisplayName("T04 Check if getAllDepartmentNames() returns the correct Strings")
    void getAllDepartmentNames() {
        List<String> stringList = fascade.getAllDepartmentNames();
        ArrayList<Department> departmentList = fascade.getAllDepartment();
        for (int i = 0; i < stringList.size(); i++) {
            assertEquals(departmentList.get(i).getName(), stringList.get(i));
        }
    }

    @Test
    @DisplayName("T05 Check if fascade method returns the correct department of the person")
    void getPersonsCurrentDepartment() {
        assertEquals(fascade.getPersonsCurrentDepartment(fascade.getPersonByFullName("admin admin")), fascade.getSearchedDepartment("Management"));
    }

    @Test
    @DisplayName("T06 Check if getAllPerson() returns a not null value")
    void getAllPerson1() {
        assertNotNull(fascade.getAllPerson());
    }

    @Test
    @DisplayName("T07 Check if getAllPerson() returns has items")
    void getAllPerson2() {
        assertTrue(fascade.getAllPerson().size() > 0);
    }

    @Test
    @DisplayName("T08 Check if the two sorting types work (A-Z | Z-A)")
    void getAllPersonSorted() {
        List<HRPerson> list = fascade.getAllPersonSortedZA();
        Collections.reverse(list);
        assertEquals(fascade.getAllPersonSortedAZ(), list);
    }

    @Test
    @DisplayName("T09 Check if correct HRPerson object gets returned with the search term")
    void getSearchedPerson1() {
        assertEquals(fascade.getSearchedPerson("admin").get(0).getFirstName(), "admin");
    }

    @Test
    @DisplayName("T10 Check if correct HRPerson object gets returned with the search term")
    void getSearchedPerson2() {
        assertEquals(fascade.getSearchedPerson("leander").get(0).getFirstName(), "Modred");
    }

    @Test
    @DisplayName("T11 Check if getPersonsFullName() returns the correct person object")
    void getPersonsFullName1() {
        assertEquals(fascade.getPersonByFullName("admin admin").getFirstName(), "admin");
    }

    @Test
    @DisplayName("T12 Check if getPersonsFullName() returns the correct person object")
    void getPersonsFullName2() {
        assertEquals(fascade.getPersonByFullName("Xose Tushar").getFirstName(), "Xose");
    }

    @Test
    @DisplayName("T13 Check if switchPersonDepartmentTo really saves the person to the new department")
    void switchPersonDepartmentTo() {
        HRPerson p = fascade.getPersonByFullName("admin admin");
        Department d = fascade.getAllDepartment().get(0);
        fascade.switchPersonDepartmentTo(d, p);
        assertEquals(p.getDepartmentName(), d.getName());
    }

    @Test
    @DisplayName("T14 Check if getSearchedTeam returns a team object")
    void getSearchedTeam() {
        assertNotNull(fascade.getSearchedTeam("The Water Coolers"));
    }

    @Test
    @DisplayName("T15 Check if the team of selected person is changed")
    void setTeamOfPerson() {
        fascade.setTeamOfPerson(fascade.getPersonByFullName("admin admin"), "The Water Coolers");
        assertEquals(fascade.getPersonByFullName("admin admin").getParticipation().getTeam().getDesignation(), "The Water Coolers");
    }

    @Test
    @DisplayName("T16 Check if getSearchedJobFunction() returns the correct JobFunction object")
    void getSearchedJobFunction() {
        assertEquals(fascade.getSearchedJobFunction("Secretary").getDesignation(), "Secretary");
    }

    @Test
    @DisplayName("T17 Check if setJobFunctionOfPerson() correctly changes the job function the person possesses")
    void setJobFunctionOfPerson() {
        HRPerson p = fascade.getPersonByFullName("admin admin");
        fascade.setJobFunctionOfPerson(p, "Receptionist");
        assertEquals(p.getParticipation().getFunction().getDesignation(), "Receptionist");
    }
}