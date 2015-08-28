package org.volifecycle.ui.handler;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.volifecycle.lifecycle.LifeCycleAction;
import org.volifecycle.lifecycle.LifeCycleManager;
import org.volifecycle.lifecycle.LifeCycleState;
import org.volifecycle.lifecycle.impl.LifeCycleCompositeActionImpl;
import org.volifecycle.lifecycle.impl.LifeCycleTransitionImpl;
import org.volifecycle.ui.bean.LifeCycleContainerJson;
import org.volifecycle.ui.bean.LifeCycleContainerLight;
import org.volifecycle.ui.vo.Action;
import org.volifecycle.ui.vo.ItemsByState;
import org.volifecycle.ui.vo.LifeCycle;
import org.volifecycle.ui.vo.SimpleAction;
import org.volifecycle.ui.vo.State;
import org.volifecycle.ui.vo.Transition;

/**
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
public class HandlerServlet implements Serializable {

    /**
     * default servialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * declare a new sate.
     */
    private State state;

    /**
     * declare a new sateList.
     */
    private List<State> stateList;

    /**
     * declare a new checkList.
     */
    private List<String> targetStates;

    /**
     * declare a new transitionList.
     */
    private Map<String, Transition> transitionList;

    /**
     * 
     */
    private LifeCycle lifeCycle;

    /**
     * declare a new transition.
     */
    private Transition transition;

    /**
     * declare list of managers.
     */

    private LifeCycleContainerJson listManager;

    /**
     * declare list of simpleAction.
     */
    private List<SimpleAction> simpleAction;

    /**
     * 
     * declare list of compositeAction.
     */
    private List<Action> listCompositeAction;

    /**
     * declare datas.
     */
    public final void init() {

        transitionList = new HashMap<String, Transition>();

    }

    List<SimpleAction> listofActionComposite;

    /**
     * Return the list of all life cycles managers
     * 
     * @param lifecycleManagerList
     * @return
     */
    public final LifeCycleContainerJson getManagerListAttrs(final List<LifeCycleManager<?, ?>> lifecycleManagerList) {
        Mapper mapper = new DozerBeanMapper();

        listManager = new LifeCycleContainerJson();

        LifeCycleContainerLight lifeCycleLight = null;
        List<LifeCycleContainerLight> containerManagerList = new ArrayList<LifeCycleContainerLight>();

        for (LifeCycleManager<?, ?> manager : lifecycleManagerList) {
            lifeCycleLight = mapper.map(manager, LifeCycleContainerLight.class);
            containerManagerList.add(lifeCycleLight);

        }

        listManager.setList(containerManagerList);

        return listManager;
    }

    /**
     * 
     * Take a manager of a lifecycle and return an object which contains all
     * datas about a vo lifecycle graph , including each actions for each
     * transitions and each transition for each actions.
     * 
     * @param manager
     *            .
     */
    public final LifeCycle getLifeCycleInformations(final LifeCycleManager<?, ?> manager, ItemsByState item) {

        Map<String, ?> statesById = manager.getStatesById();
        Set<String> keys = statesById.keySet();

        Iterator<String> it = keys.iterator();

        Iterator<String> itTransition = null;
        Map<String, ?> transitionsById = null;
        LifeCycleTransitionImpl<?> infosTransition = null;

        String key;
        stateList = new ArrayList<State>();
        lifeCycle = new LifeCycle();

        while (it.hasNext()) {
            state = new State();
            transitionList = new HashMap<String, Transition>();

            key = it.next();
            LifeCycleState<?> getStateList = (LifeCycleState<?>) statesById.get(key);
            transitionsById = getStateList.getTransitionsById();

            state.setId(key);
            state.setDescription(getStateList.getDescription());
            if (item != null)
                state.setItems(item.getAllItemsByState(state));

            lifeCycle.setStateListCycle(stateList);
            stateList.add(state);

            Set<String> keysTransition = (null == getStateList.getTransitionsById()) ? null : getStateList.getTransitionsById().keySet();
            if (isEmpty(keysTransition)) {
                continue;
            }

            itTransition = keysTransition.iterator();
            while (itTransition.hasNext()) {

                key = itTransition.next();
                if (getStateList.getTransitionsById() != null) {

                    transition = new Transition();
                    infosTransition = (LifeCycleTransitionImpl<?>) transitionsById.get(key);
                    targetStates = new ArrayList<String>();
                    simpleAction = new ArrayList<SimpleAction>();

                    if (null != infosTransition.getTargetStates()) {
                        for (String targetStatesL : infosTransition.getTargetStates()) {
                            if (targetStatesL != null) {
                                targetStates.add(targetStatesL);
                            }

                        }
                    }

                    listCompositeAction = new ArrayList<Action>();

                    listCompositeAction = getListAction(infosTransition);

                    transition.setType(infosTransition.getType());
                    transition.setdescription(infosTransition.getDescription());

                    transitionList.put(key, transition);

                    state.setTransitionMap(transitionList.values());
                    transition.setIdTransition(infosTransition.getId());
                    transition.setTargetStates(targetStates);
                    // transition.setActions(simpleAction);
                    transition.setActionsList(listCompositeAction);

                }
            }

            // if (listCompositeAction != null) {
            // for (Action ca : listCompositeAction) {
            // System.out.println(ca.getId());
            // System.out.println(ca.getDescription());
            //
            // }
            // }
            // System.out.println();
        }
        return lifeCycle;

    }

    /**
     * return the list of all actions of a transitions, whatever composite
     * action or simple.
     * 
     * @param transitionActions
     * @return
     */

    public List<Action> getListAction(final LifeCycleTransitionImpl<?> transition) {

        if (null != transition.getActions()) {
            for (LifeCycleAction<?> action : transition.getActions()) {
                Action cAction = new Action();
                if (action instanceof LifeCycleCompositeActionImpl<?>) {

                    LifeCycleCompositeActionImpl<?> composite = (LifeCycleCompositeActionImpl<?>) action;

                    cAction.setId(composite.getId());
                    cAction.setDescription(composite.getDescription());

                    listofActionComposite = new ArrayList<SimpleAction>();

                    if (null != composite.getActions()) {
                        for (LifeCycleAction<?> actionc : composite.getActions()) {
                            SimpleAction actionS = new SimpleAction();

                            // System.out.println(actionc.getId());

                            actionS.setId(actionc.getId());
                            actionS.setDescription(actionc.getDescription());
                            listofActionComposite.add(actionS);

                        }
                        cAction.setActions(listofActionComposite);

                    }
                    if (cAction != null)
                        listCompositeAction.add(cAction);

                } else {

                    cAction.setId(action.getId());
                    cAction.setDescription(action.getDescription());

                    if (action != null) {
                        listCompositeAction.add(cAction);
                    }
                }
            }
        }
        return listCompositeAction;
    }
}
