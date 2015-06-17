package volifecycle.ui.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.volifecycle.lifecycle.LifeCycleAction;
import org.volifecycle.lifecycle.LifeCycleManager;
import org.volifecycle.lifecycle.LifeCycleState;
import org.volifecycle.lifecycle.LifeCycleTransition;
import org.volifecycle.lifecycle.impl.LifeCycleCompositeActionImpl;
import org.volifecycle.lifecycle.impl.LifeCycleManagerImpl;
import org.volifecycle.lifecycle.impl.LifeCycleStateImpl;
import org.volifecycle.lifecycle.impl.LifeCycleTransitionImpl;

import volifecycle.ui.bean.LifeCycleContainerJson;
import volifecycle.ui.handler.HandlerServlet;
import volifecycle.ui.vo.LifeCycle;
import volifecycle.ui.vo.State;
import volifecycle.ui.vo.Transition;

/**
 * 
 * @author attia anthony <anthony.attia1@gmail.com>
 *
 */
public class HandlerServletTest {

    private LifeCycleManagerImpl manager;
    private Map<String, LifeCycleState<?>> statesById;
    private Map<String, LifeCycleTransition<?>> transitionsById;
    private LifeCycleStateImpl state;
    private LifeCycleTransitionImpl transition;
    private LifeCycleCompositeActionImpl action;
    private List<LifeCycleAction<?>> ListAction;
    private List<String> targetList;
    private LifeCycle lifeCycle;
    private HandlerServlet handler;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Before
    public final void initData() {

        handler = new HandlerServlet();

        manager = new LifeCycleManagerImpl();
        statesById = new HashMap<String, LifeCycleState<?>>();
        transitionsById = new HashMap<String, LifeCycleTransition<?>>();
        state = new LifeCycleStateImpl();
        transition = new LifeCycleTransitionImpl();
        action = new LifeCycleCompositeActionImpl();
        ListAction = new ArrayList<LifeCycleAction<?>>();
        targetList = new ArrayList<String>();
        lifeCycle = new LifeCycle();

        handler.init();
        setData();

    }

    /**
     * Test if function getManagerListAttrs return list of managers
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void getManagerListAttrsTest() {

        HandlerServlet handler = new HandlerServlet();
        Map<String, Object> statesById = new HashMap<String, Object>();
        LifeCycleManagerImpl cmanager = new LifeCycleManagerImpl();

        cmanager.setDescription("description manager test");
        cmanager.setId("id manager test");
        statesById.put("state", "id");

        // System.out.println(cmanager.getDescription() + cmanager.getId());

        List<LifeCycleManager> lifecycleManagerList = new ArrayList<LifeCycleManager>();
        lifecycleManagerList.add(cmanager);

        LifeCycleContainerJson listManagerToJson = new LifeCycleContainerJson();

        listManagerToJson = handler.getManagerListAttrs(lifecycleManagerList);

        assertEquals(1, listManagerToJson.getList().size());

    }

    /**
     * Create a new manager and check if a lifecycle is correctly returned with
     * its manager datas
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void getLifeCycleInformationsTest() {

        lifeCycle = handler.getLifeCycleInformations(manager);

        assertEquals(1, lifeCycle.getState().get(0).getTransitionMap().size());
        assertEquals("state test", lifeCycle.getState().get(0).getDescription());

        assertNotNull("null okay", lifeCycle.getState().get(0).getTransitionMap());

        for (State statel : lifeCycle.getState()) {
            for (Transition trans : statel.getTransitionMap()) {
                assertEquals(1, trans.getActions().size());
                assertEquals("transition description", trans.getDescription());
                assertEquals("id transition", trans.getIdTransition());

                if (null != trans.getActions()) {
                    for (Object obj : transition.getActions()) {

                        LifeCycleAction<?> cycleAction = (LifeCycleAction<?>) obj;

                        assertEquals("id action", cycleAction.getId());
                    }
                }
            }

        }
        assertEquals(1, lifeCycle.getState().size());

    }

    public void setData() {

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

        ListAction.add(action);

        transition.setActions(ListAction);

        transitionsById.put("", transition);

        state.setTransitionsById(transitionsById);
        statesById.put("s", state);

        // manager.s
        // Map<String, ?> map = new HashMap<String, ?>();

        manager.setStatesById(statesById);
    }

}
