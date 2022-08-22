package company;

import lombok.Getter;
import lombok.Setter;

/**
 * <h1>Department</h1>
 * @author: Andras Tarlos
 * @version: 1.0
 * @date: 18.06.2022
 * <h2>Description</h2>
 * The model class of a team. A department can have multiple different teams.
 */

@Getter
@Setter
public class Team {
    private String designation;
    /**
     * Basic constructor
     */
    public Team() {}
}