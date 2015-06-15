package volifecycle.ui.vo;

import java.util.List;

/**
 * 
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
public class LifeCycle {

    /**
     * init the state list.
     */
    private List<State> state;

    /**
     * @return the stateListCycle
     */
    public List<State> getState() {
        return state;
    }

    /**
     * @param stateListCycle
     *            the stateListCycle to set
     */
    public void setStateListCycle(List<State> state) {
        this.state = state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(List<State> state) {
        this.state = state;
    }

}
