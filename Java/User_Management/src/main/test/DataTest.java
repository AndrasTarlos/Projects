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

class DataTest {

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
    @DisplayName("P01 Adds a department and checks if it exists")
    void addDepartment() {
        fascade.createDepartment("Probst Test");
        assertNotNull(fascade.getSearchedDepartment("Probst Test"));
    }

    @Test
    @DisplayName("P02 Edit a departments name and checks if it was changed")
    void editDepartment() {
        fascade.createDepartment("Probst Test");
        fascade.getSearchedDepartment("Probst Test").setName("Neuer Probst Test");
        assertNotNull(fascade.getSearchedDepartment("Neuer Probst Test"));
    }

    @Test
    @DisplayName("P03 Delete a department by name and checks if it isn't saved anymore")
    void deleteDepartment() {
        fascade.createDepartment("Probst Test");
        fascade.deleteDepartment("Probst Test");
        assertNull(fascade.getSearchedDepartment("Probst Test"));
    }

    @Test
    @DisplayName("P04 Adds a jobFunction and checks if it exists")
    void addJobFunction() {
        fascade.createJobFunction("Probst Test");
        assertNotNull(fascade.getSearchedJobFunction("Probst Test"));
    }

    @Test
    @DisplayName("P05 Edit a jobFunction name and checks if it was changed")
    void editJobFunction() {
        fascade.createJobFunction("Probst Test");
        fascade.getSearchedJobFunction("Probst Test").setDesignation("Neuer Probst Test");
        assertNotNull(fascade.getSearchedJobFunction("Neuer Probst Test"));
    }

    @Test
    @DisplayName("P06 Delete a jobFunction by name and checks if it isn't saved anymore")
    void deleteJobFunction() {
        fascade.createJobFunction("Probst Test");
        fascade.deleteJobFunction("Probst Test");
        assertNull(fascade.getSearchedJobFunction("Probst Test"));
    }

    @Test
    @DisplayName("P07 Adds a team and checks if it exists")
    void addTeam() {
        fascade.createTeam("Probst Test");
        assertNotNull(fascade.getSearchedTeam("Probst Test"));
    }

    @Test
    @DisplayName("P08 Edit a team name and checks if it was changed")
    void editTeam() {
        fascade.createTeam("Probst Test");
        fascade.getSearchedTeam("Probst Test").setDesignation("Neuer Probst Test");
        assertNotNull(fascade.getSearchedTeam("Neuer Probst Test"));
    }

    @Test
    @DisplayName("P09 Delete a team by name and checks if it isn't saved anymore")
    void deleteTeam() {
        fascade.createTeam("Probst Test");
        fascade.deleteTeam("Probst Test");
        assertNull(fascade.getSearchedTeam("Probst Test"));
    }
}