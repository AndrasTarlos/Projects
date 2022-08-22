package log;

import employees.HRPerson;
import employees.Person;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * <h1>UserAction</h1>
 * @author: Tarlos Andras
 * @version: 1.0
 * @date: 18.06.2022
 * <h2>Description</h2>
 * This class represents different user actions that get saved
 * in the log book.
 */
public class UserAction {
    public static final int CREATE_PERSON = 0;
    public static final int CHANGE_VALUE = 1;
    public static final int SET_ASSIGNMENT = 2;
    public static final int DELETE_PERSON = 3;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private String[] actionDescription = {"Person erstellt", "Daten verändert", "Aufgabe gesetzt", "Person gelöscht"};
    private String entry;

    /**
     * Advanced constructor (to create a new User Action)
     * @param hrPerson an HRPerson object
     * @param person a Person Object
     * @param action int (0-1-2-3)
     */
    public UserAction(HRPerson hrPerson, Person person, int action) {
        String firstName = hrPerson.getFirstName();
        String lastName = hrPerson.getLastName();

        LocalDateTime now = LocalDateTime.now();
        String fN = person.getFirstName();
        String lN = person.getLastName();
        if (action >= 0 && action < actionDescription.length ) {
            entry = dtf.format(now) + " Von: " + firstName + " " + lastName + " | Aktion: "
                    + actionDescription[action] + " | Person: " + fN + " " + lN;
        } else {
            entry = dtf.format(now) + " Von: " + firstName + " " + lastName + " | Aktion: Unbekannte Aktion "
                    + "| Person: " + fN + " " + lN;
        }
    }

    /**
     * Returns the formatted string entry to the logbook
     * @return String
     */
    public String getEntry() {
        return entry;
    }
}
