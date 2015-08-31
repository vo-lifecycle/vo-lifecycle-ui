package org.volifecycle.ui.vo;


/**
 * 
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
public interface ItemsByState {

    /**
     * Get the number of all items by state.
     * 
     * @param state
     * @return
     */
    public Long getAllItemsByState(State state);
}
