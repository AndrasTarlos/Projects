package employees;

import company.Team;
import lombok.Getter;
import lombok.Setter;
/**
 * <h1>Participation</h1>
 * @author: Tarlos Andras
 * @version: 1.0
 * @date: 21.06.2022
 * <h2>Description</h2>
 * This class includes methods for JobFunctions.
 */
@Getter
@Setter
public class Participation {
    private JobFunction function = new JobFunction();
    private Team team = new Team();

    /**
     * Default Constructor of Participation
     */
    public Participation() {}
}