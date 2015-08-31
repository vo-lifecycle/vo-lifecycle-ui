package org.volifecycle.ui.bean;

import java.util.List;
import java.util.Map;

import org.volifecycle.lifecycle.LifeCycleManager;

/**
 * Object container wich contains all lifecycle managers
 * 
 * @author an anthony attia <anthony.attia1@gmail.com>
 *
 */
public class LifeCycleContainer {

    private List<LifeCycleManager<?, ?>> managerList;
    private Map<String, ?> mapStatByLifeCycleId;

    /**
     * @return the managerList.
     */
    public List<LifeCycleManager<?, ?>> getManagerList() {
        return managerList;
    }

    /**
     * @param managerList
     *            the managerList to set.
     */
    public void setManagerList(final List<LifeCycleManager<?, ?>> managerList) {
        this.managerList = managerList;
    }

    /**
     * @return the mapStatByLifeCycleId
     */
    public Map<String, ?> getMapStatByLifeCycleId() {
        return mapStatByLifeCycleId;
    }

    /**
     * @param mapStatByLifeCycleId
     *            the mapStatByLifeCycleId to set
     */
    public void setMapStatByLifeCycleId(Map<String, ?> mapStatByLifeCycleId) {
        this.mapStatByLifeCycleId = mapStatByLifeCycleId;
    }

}
