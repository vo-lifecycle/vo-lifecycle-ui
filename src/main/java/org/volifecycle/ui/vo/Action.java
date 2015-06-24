package org.volifecycle.ui.vo;

import java.util.List;

public class Action {

    /**
     * Id which is used for forced the result of this action
     */
    private String id;

    /**
     * Description
     */
    private String description;

    /**
     * target state
     */
    private String targetState;

    /**
     * List actions wich are executed by "and" predicate operator.
     */
    private List<SimpleAction> actions;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final String id) {
        this.id = id;
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
     * @return the targetState
     */
    public String getTargetState() {
        return targetState;
    }

    /**
     * @param targetState
     *            the targetState to set
     */
    public void setTargetState(final String targetState) {
        this.targetState = targetState;
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
    public void setActions(final List<SimpleAction> listAction) {
        this.actions = listAction;
    }

}
