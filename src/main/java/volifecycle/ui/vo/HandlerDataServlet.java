package volifecycle.ui.vo;

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
import org.volifecycle.lifecycle.LifeCycleAdapter;
import org.volifecycle.lifecycle.LifeCycleManager;
import org.volifecycle.lifecycle.LifeCycleState;
import org.volifecycle.lifecycle.impl.LifeCycleSimpleActionImpl;
import org.volifecycle.lifecycle.impl.LifeCycleTransitionImpl;

import volifecycle.ui.bean.LifeCycleContainerJson;
import volifecycle.ui.bean.LifeCycleContainerLight;

/**
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
public class HandlerDataServlet implements Serializable {

    /**
     * default servialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * init a new sate.
     */
    private State state;

    /**
     * init a new sateList.
     */
    private List<State> stateList;

    /**
     * init a new checkList.
     */
    private List<String> targetStates;

    /**
     * init a new transitionList
     */
    private Map<String, Transition> transitionList;

    /**
     * 
     */
    private LifeCycle lifeCycle;

    /**
     * init a new transition.
     */
    private Transition<?> transition;

    /**
     * init list of managers
     */

    private LifeCycleContainerJson listManager;

    private List<LifeCycleAction<?>> listAction;

    private List<SimpleAction> simpleAction;

    private Mapper mapper = new DozerBeanMapper();

    /**
     * init datas.
     */
    public final void init() {

        stateList = new ArrayList<State>();
        transitionList = new HashMap<String, Transition>();
        lifeCycle = new LifeCycle();
        listManager = new LifeCycleContainerJson();

    }

    /**
     * Return the list of all life cycles managers
     * 
     * @param lifecycleManagerList
     * @return
     */
    public final LifeCycleContainerJson getManagerListAttrs(List<LifeCycleManager> lifecycleManagerList) {

        LifeCycleContainerLight lifeCycleLight = null;
        List<LifeCycleContainerLight> containerManagerList = new ArrayList<LifeCycleContainerLight>();

        for (LifeCycleManager<?, LifeCycleAdapter<?>> manager : lifecycleManagerList) {
            lifeCycleLight = mapper.map(manager, LifeCycleContainerLight.class);
            containerManagerList.add(lifeCycleLight);

        }

        listManager.setList(containerManagerList);
        return listManager;
    }

    /**
     * Return an object which contains all data about vo lifecycle graph,
     * including each actions for each transitions and each transition for each
     * actions
     * 
     * @param bean
     *            .
     */
    public final LifeCycle getLifeCycleInformations(final LifeCycleManager<?, LifeCycleAdapter<?>> bean) {

        Map<String, ?> statesById = bean.getStatesById();
        Set<String> keys = statesById.keySet();

        Iterator<String> it = keys.iterator();

        Iterator<String> itTransition = null;
        Map<String, ?> transitionsById = null;
        LifeCycleTransitionImpl<?> infosTransition = null;
        List<?> checker = null;

        String key;

        while (it.hasNext()) {
            state = new State();
            transitionList = new HashMap<String, Transition>();

            key = it.next();
            LifeCycleState<?> getStateList = (LifeCycleState<?>) statesById.get(key);
            transitionsById = getStateList.getTransitionsById();

            state.setId(key);
            state.setDescription(getStateList.getDescription());

            Set<String> keysTransition = getStateList.getTransitionsById().keySet();
            itTransition = keysTransition.iterator();

            lifeCycle.setStateListCycle(stateList);

            stateList.add(state);

            while (itTransition.hasNext()) {

                key = itTransition.next();
                if (getStateList.getTransitionsById() != null) {

                    transition = new Transition();
                    infosTransition = (LifeCycleTransitionImpl<?>) transitionsById.get(key);
                    targetStates = new ArrayList<String>();
                    listAction = new ArrayList<LifeCycleAction<?>>();
                    simpleAction = new ArrayList<SimpleAction>();
                    for (String targetStatesL : infosTransition.getTargetStates()) {
                        if (targetStatesL != null)
                            targetStates.add(targetStatesL);

                    }
                    // for (LifeCycleAction<?> action :
                    // infosTransition.getActions()) {
                    // if (action != null)
                    // listAction.add(action);
                    // }
                    for (Object action : infosTransition.getActions()) {
                        SimpleAction act = new SimpleAction();
                        LifeCycleAction<?> cycleAction = (LifeCycleAction<?>) action;

                        act.setId(cycleAction.getId());
                        act.setDescription(cycleAction.getDescription());
                        if (action != null)
                            simpleAction.add(act);
                    }

                    transition.setType(infosTransition.getType());
                    transition.setdescription(infosTransition.getDescription());

                    transitionList.put(key, transition);

                    state.setTransitionMap(transitionList.values());
                    transition.setIdTransition(infosTransition.getId());
                    transition.setTargetStates(targetStates);
                    transition.setActions(simpleAction);

                }
            }

        }
        return lifeCycle;

    }
}
