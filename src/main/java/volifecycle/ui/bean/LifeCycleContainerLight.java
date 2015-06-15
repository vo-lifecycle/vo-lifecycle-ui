package volifecycle.ui.bean;

import java.util.Map;

import org.volifecycle.lifecycle.LifeCycleState;

/**
 * 
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
public class LifeCycleContainerLight {

    private String id;
    private String description;
    private Map<String, LifeCycleState<?>> getStatesById;

    /**
     * @return the idManager
     */
    public String getId() {
        return id;
    }

    /**
     * @param idManager
     *            the idManager to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the descriptio
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param descriptio
     *            the descriptio to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the getStatesById
     */
    public Map<String, LifeCycleState<?>> getGetStatesById() {
        return getStatesById;
    }

    /**
     * @param getStatesById
     *            the getStatesById to set
     */
    public void setGetStatesById(Map<String, LifeCycleState<?>> getStatesById) {
        this.getStatesById = getStatesById;
    }

}
