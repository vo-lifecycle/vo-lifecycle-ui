package org.volifecycle.ui.vo;

import java.io.Serializable;
import java.util.Collection;

/**
 * State representation.
 * 
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
public class State implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * sate id.
     */
    private String id;

    /**
     * state description.
     */
    private String description;

    /**
     * items number.
     */
    private Long items;

    /**
     * @return the items
     */
    public Long getItems() {
        return items;
    }

    /**
     * @param items
     *            the items to set
     */
    public void setItems(Long items) {
        this.items = items;
    }

    /**
     * state transition list.
     */
    private Collection<Transition> transitionMap;

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
    public void setId(String id) {
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
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the transitionList
     */
    public Collection<Transition> getTransitionMap() {
        return transitionMap;
    }

    /**
     * @param collection
     *            the transitionList to set
     */
    public void setTransitionMap(Collection<Transition> collection) {
        this.transitionMap = collection;
    }

}
