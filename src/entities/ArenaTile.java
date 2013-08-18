package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stantonbohmbach
 * Date: 7/15/13
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class ArenaTile {

    private List<Attack> attacksHitting;
    private List<Robot> robotsOccupying;

    public ArenaTile() {
        attacksHitting = new ArrayList<Attack>();
        robotsOccupying = new ArrayList<Robot>();
    }

    public void addRobot(Robot r) {
        if (r == null) return;

        synchronized (robotsOccupying) {
            robotsOccupying.add(r);
        }
    }

    public void removeRobot(Robot r) {
        if (r == null) return;

        synchronized (robotsOccupying) {
            robotsOccupying.remove(r);
        }
    }

    public void addAttack(Attack a) {
        if (a == null) return;

        synchronized (attacksHitting) {
            attacksHitting.add(a);
        }
    }

    public void removeAttack(Attack a) {
        if (a == null) return;

        synchronized (attacksHitting) {
            attacksHitting.remove(a);
        }
    }

    public List<Attack> getAttacksHitting() {
        return attacksHitting;
    }

    public List<Robot> getRobotsOccupying() {
        return robotsOccupying;
    }
}
