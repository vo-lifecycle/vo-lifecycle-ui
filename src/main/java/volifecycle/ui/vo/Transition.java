package volifecycle.ui.vo;

import java.io.Serializable;
import java.util.List;

import org.volifecycle.lifecycle.LifeCycleActionStorage;

/**
 * 
 * @author an anthony attia <anthony.attia1@gmail.com>
 * @param <T>
 * @param <T>
 *            value object type
 *
 */
public class Transition<T> implements Serializable {

    /**
     * Id of a transition
     */

    private String idTransition;

    /**
     * List of composite actions.
     */
    private List<SimpleAction> actions;

    /**
     * Action storage.
     */
    private LifeCycleActionStorage<T> actionStorage;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * description.
     */
    private String description;

    /**
     * type.
     */
    private String type;

    /**
     * List of targeted states.
     */
    List<String> targetStates;

    /**
     * @return the id
     */
    public String getIdTransition() {
        return idTransition;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setIdTransition(String id) {
        this.idTransition = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the description
     */
    public String getDescriptionT() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setdescription(String description) {
        this.description = description;
    }

    /**
     * @return the actions
     */
    public List<SimpleAction> getActions() {
        return actions;
    }

    /**
     * @param listAction
     *            the actions to set
     */
    public void setActions(List<SimpleAction> listAction) {
        this.actions = listAction;
    }

    /**
     * @return the actionStorage
     */
    public LifeCycleActionStorage<T> getActionStorage() {
        return actionStorage;
    }

    /**
     * @param actionStorage
     *            the actionStorage to set
     */
    public void setActionStorage(LifeCycleActionStorage<T> actionStorage) {
        this.actionStorage = actionStorage;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the targetStates
     */
    public List<String> getTargetStates() {
        return targetStates;
    }

    /**
     * @param targetStates
     *            the targetStates to set
     */
    public void setTargetStates(List<String> targetStates) {
        this.targetStates = targetStates;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
