package org.volifecycle.ui.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.volifecycle.lifecycle.LifeCycleAction;
import org.volifecycle.lifecycle.LifeCycleAdapter;
import org.volifecycle.lifecycle.LifeCycleManager;
import org.volifecycle.lifecycle.LifeCycleState;
import org.volifecycle.lifecycle.LifeCycleTransition;
import org.volifecycle.lifecycle.impl.LifeCycleCompositeActionImpl;
import org.volifecycle.lifecycle.impl.LifeCycleManagerImpl;
import org.volifecycle.lifecycle.impl.LifeCycleStateImpl;
import org.volifecycle.lifecycle.impl.LifeCycleTransitionImpl;
import org.volifecycle.ui.bean.LifeCycleContainer;
import org.volifecycle.ui.bean.LifeCycleContainerJson;
import org.volifecycle.ui.handler.HandlerServlet;
import org.volifecycle.ui.vo.LifeCycle;

/**
 * 
 * @author attia anthony <anthony.attia1@gmail.com>
 *
 */
public class HandlerServletTest<T, A extends LifeCycleAdapter<T>> {

    private LifeCycleManagerImpl<T, A> manager;
    private Map<String, LifeCycleState<T>> statesById;
    private Map<String, LifeCycleTransition<T>> transitionsById;
    private LifeCycleStateImpl<T> state;
    private LifeCycleTransitionImpl<T> transition;
    private LifeCycleCompositeActionImpl<T> action;
    private List<LifeCycleAction<T>> listAction;
    private List<String> targetList;
    private LifeCycle lifeCycle;
    private HandlerServlet handler;
    private LifeCycleContainer containerManager;

    /**
     * Init datas
     */
    @Before
    public final void initData() {
        handler = new HandlerServlet();
        manager = new LifeCycleManagerImpl<T, A>();
        statesById = new HashMap<String, LifeCycleState<T>>();
        transitionsById = new HashMap<String, LifeCycleTransition<T>>();
        state = new LifeCycleStateImpl<T>();
        transition = new LifeCycleTransitionImpl<T>();
        action = new LifeCycleCompositeActionImpl<T>();
        listAction = new ArrayList<LifeCycleAction<T>>();
        targetList = new ArrayList<String>();
        lifeCycle = new LifeCycle();
        containerManager = new LifeCycleContainer();
        handler.init();

        // Init datas
        state.setDescription("state test");
        state.setId("id test");

        targetList.add("State 1");
        targetList.add("State 2");
        targetList.add("State 3");

        action.setId("id action");
        action.setDescription("description action");

        transition.setDescription("transition description");
        transition.setId("id transition");
        transition.setType("type transition");
        transition.setTargetStates(targetList);

        listAction.add(action);

        transition.setActions(listAction);

        transitionsById.put("", transition);

        state.setTransitionsById(transitionsById);
        statesById.put("s", state);

        manager.setStatesById(statesById);
    }

    /**
     * Test if function getManagerListAttrs return list of managers
     */
    @Test
    public void getManagerListAttrsTest() {

        HandlerServlet handler = new HandlerServlet();
        Map<String, Object> statesById = new HashMap<String, Object>();
        LifeCycleManagerImpl<T, A> cmanager = new LifeCycleManagerImpl<T, A>();

        cmanager.setDescription("description manager test");
        cmanager.setId("id manager test");
        statesById.put("state", "id");

        List<LifeCycleManager<?, ?>> lifecycleManagerList = new ArrayList<LifeCycleManager<?, ?>>();
        lifecycleManagerList.add(cmanager);

        LifeCycleContainerJson listManagerToJson = new LifeCycleContainerJson();

        listManagerToJson = handler.getManagerListAttrs(lifecycleManagerList);

        containerManager.setManagerList(lifecycleManagerList);

        assertEquals(1, listManagerToJson.getList().size());
        assertEquals(1, containerManager.getManagerList().size());

    }

    /**
     * Create a new manager and check if a lifecycle is correctly returned with
     * its manager datas
     */
    @Test
    public void getLifeCycleInformationsTest() {
        lifeCycle = handler.getLifeCycleInformations(manager);

        assertEquals(1, lifeCycle.getState().get(0).getTransitionMap().size());
        assertEquals("state test", lifeCycle.getState().get(0).getDescription());
        assertNotNull("null okay", lifeCycle.getState().get(0).getTransitionMap());

        assertEquals(1, lifeCycle.getState().size());
    }
}
