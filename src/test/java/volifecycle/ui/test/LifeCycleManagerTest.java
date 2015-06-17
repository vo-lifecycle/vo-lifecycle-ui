package volifecycle.ui.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import volifecycle.ui.vo.State;
import volifecycle.ui.vo.Transition;

/**
 * class test for a default manager with defaults values
 * 
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
public class LifeCycleManagerTest {

    private State state;
    private State state2;
    private State state3;

    private Transition transition;
    private Transition transition2;

    private Map<String, Transition> transitionListState1;
    private Map<String, Transition> transitionListState2;
    private Map<String, Transition> transitionListState3;

    private List<State> listState;

    @Before
    public final void initData() {

        state = new State();
        state2 = new State();
        state3 = new State();

        transition = new Transition();
        transition2 = new Transition();

        transitionListState1 = new HashMap<String, Transition>();
        transitionListState2 = new HashMap<String, Transition>();
        transitionListState3 = new HashMap<String, Transition>();
        listState = new ArrayList<State>();

        setData();
    }

    /**
     * 
     */
    @Test
    public final void lifeCycleDataTest() {

        assertNotNull("null okay", state.getTransitionMap());
        assertEquals(2, state.getTransitionMap().size());

        assertNotNull("null okay", state2.getTransitionMap());
        assertEquals(2, state2.getTransitionMap().size());

        assertNotNull("null okay", state3.getTransitionMap());
        assertEquals(1, state3.getTransitionMap().size());

    }

    public void setData() {
        state.setDescription("ready to start");
        state.setId("Start");

        state2.setDescription("in process");
        state2.setId("Process");

        state3.setDescription("final state");
        state3.setId("Final");

        listState.add(state);
        listState.add(state2);
        listState.add(state3);

        // set a new transition 1
        transition.setType("function");
        transition.setdescription("go to process state");

        transitionListState1.put("Final", transition);

        // add this transition to the transition list
        transitionListState1.put("Process", transition2);

        // add this transition list to the state final
        state.setTransitionMap(transitionListState1.values());

        // set a new transition 2
        transition2.setType("function");
        transition2.setdescription("go to process state");

        // add this transition 1 and transition 2 to the transition list
        transitionListState2.put("Final", transition);
        transitionListState2.put("Process", transition2);

        state2.setTransitionMap(transitionListState2.values());

        transitionListState3.put("Start", transition);

        state3.setTransitionMap(transitionListState3.values());

    }

}

// public void Test

