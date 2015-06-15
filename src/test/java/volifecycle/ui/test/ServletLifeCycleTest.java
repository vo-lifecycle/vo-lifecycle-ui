package volifecycle.ui.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import volifecycle.ui.vo.SimpleAction;
import volifecycle.ui.vo.LifeCycle;
import volifecycle.ui.vo.State;
import volifecycle.ui.vo.Transition;

/**
 * 
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
public class ServletLifeCycleTest {

    /**
     * test for an empty state graph
     */

    @Test
    public final void ValueEmptyGraph() {

        ObjectMapper mapper = new ObjectMapper();
        LifeCycle lCycle = new LifeCycle();

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lCycle);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(mapper);

    }

    /**
     * 
     */
    @Test
    public final void ValueUniqueGraph() {

        State state = new State();
        Transition transition = new Transition();
        SimpleAction check = new SimpleAction();
        LifeCycle lcycle = new LifeCycle();

        Map<String, Transition> transitionMap = new HashMap<String, Transition>();
        List<SimpleAction> checkList = new ArrayList<SimpleAction>();
        List<State> stateList = new ArrayList<State>();

        state.setDescription("ready to start");
        state.setId("Start");

        transition.setType("function");
        transition.setdescription("go to process state");

        check.setDescription("check_is_ok");
        check.setId("check_is_ok");

        checkList.add(check);

        // transition.setChecker(checkList);
        transitionMap.put(state.getId(), transition);

        state.setTransitionMap(transitionMap.values());

        stateList.add(state);
        lcycle.setState(stateList);

        assertEquals(1, lcycle.getState().size());
        assertEquals(1, state.getTransitionMap().size());
        // assertEquals(1, transition.getChecker().size());
    }

    /**
     * test for manual values generates
     */
    @Test
    public final void ValueGraphTest() {

        State state = new State();
        State state2 = new State();
        State state3 = new State();

        Transition transition = new Transition();
        Transition transition2 = new Transition();
        Map<String, Transition> transitionListState1 = new HashMap<String, Transition>();
        Map<String, Transition> transitionListState2 = new HashMap<String, Transition>();
        Map<String, Transition> transitionListState3 = new HashMap<String, Transition>();

        List<State> listState = new ArrayList<State>();

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

        assertEquals(2, state.getTransitionMap().size());
        // System.out.println(st.getTransitionMap().size());

        // Les autres états n'ont pas de transitions

        assertEquals(2, state2.getTransitionMap().size());
        // System.out.println(st1.getTransitionMap().size());

        assertEquals(1, state3.getTransitionMap().size());
        // System.out.println(st2.getTransitionMap().size());

    }

}

// public void Test

