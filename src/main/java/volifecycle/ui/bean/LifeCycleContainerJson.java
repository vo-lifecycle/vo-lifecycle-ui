package volifecycle.ui.bean;

import java.util.List;

/**
 * 
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
public class LifeCycleContainerJson {

    private List<LifeCycleContainerLight> manager;

    /**
     * @return the list
     */
    public List<LifeCycleContainerLight> getList() {
        return manager;
    }

    /**
     * @param list
     *            the list to set
     */
    public void setList(List<LifeCycleContainerLight> manager) {
        this.manager = manager;
    }

}
