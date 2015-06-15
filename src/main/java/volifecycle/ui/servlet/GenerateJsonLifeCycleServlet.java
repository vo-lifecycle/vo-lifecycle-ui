package volifecycle.ui.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.volifecycle.lifecycle.LifeCycleManager;

import volifecycle.ui.bean.LifeCycleContainer;
import volifecycle.ui.bean.LifeCycleContainerJson;
import volifecycle.ui.vo.HandlerDataServlet;
import volifecycle.ui.vo.LifeCycle;

/**
 * 
 * @author an anthony attia <anthony.attia1@gmail.com>
 *
 */
public class GenerateJsonLifeCycleServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateJsonLifeCycleServlet() {

        super();

    }

    @Override
    public void init() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        HandlerDataServlet handler = new HandlerDataServlet();
        ObjectMapper mapper = new ObjectMapper();
        LifeCycle lifeCycle = new LifeCycle();
        LifeCycleContainerJson lifeCycleJson = new LifeCycleContainerJson();

        @SuppressWarnings("rawtypes")
        List<LifeCycleManager> lifecycleManagerList = new ArrayList<LifeCycleManager>();

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        // Load spring context
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        @SuppressWarnings("unchecked")
        LifeCycleContainer managerContainerList = ((LifeCycleContainer) context.getBean("lifecycleContainer"));
        lifecycleManagerList = managerContainerList.getManagerList();

        handler.init();
        lifeCycleJson = handler.getManagerListAttrs(lifecycleManagerList);

        if (req.getParameter("action") != null) {
            if (req.getParameter("action").equals("showList")) {
                out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lifeCycleJson));
            }
        }
        if (req.getParameter("idManagerLifeCycle") != null) {
            int idManagerLifeCycle = Integer.parseInt(req.getParameter("idManagerLifeCycle"));

            if (idManagerLifeCycle >= 0 && idManagerLifeCycle <= lifecycleManagerList.size()) {

                lifeCycle = handler.getLifeCycleInformations(lifecycleManagerList.get(idManagerLifeCycle));

                out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lifeCycle));
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }

}
