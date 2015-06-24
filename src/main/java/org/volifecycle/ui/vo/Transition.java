package org.volifecycle.ui.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Transition representation class.
 * 
 * @author an anthony attia <anthony.attia1@gmail.com>
 *
 */
public class Transition implements Serializable {
    /**
     * Id of a transition
     */
    private String idTransition;

    /**
     * List the composites actions
     */
    public List<Action> actionsList;

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
    public void setIdTransition(final String id) {
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
    public void setType(final String type) {
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
    public void setdescription(final String description) {
        this.description = description;
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
    public void setDescription(final String description) {
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
    public void setTargetStates(final List<String> targetStates) {
        this.targetStates = targetStates;
    }

    /**
     * @return the compositeActions
     */
    public List<Action> getActionsList() {
        return actionsList;
    }

    /**
     * @param compositeActions
     *            the compositeActions to set
     */
    public void setActionsList(final List<Action> actionsList) {
        this.actionsList = actionsList;
    }

}
