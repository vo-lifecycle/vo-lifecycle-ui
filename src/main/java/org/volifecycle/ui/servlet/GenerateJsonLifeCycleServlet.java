package org.volifecycle.ui.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.volifecycle.lifecycle.LifeCycleManager;
import org.volifecycle.ui.bean.LifeCycleContainer;
import org.volifecycle.ui.bean.LifeCycleContainerJson;
import org.volifecycle.ui.handler.HandlerServlet;
import org.volifecycle.ui.vo.LifeCycle;

/**
 * Generation of JSON representation of lifecycle.
 * 
 * @author an anthony attia <anthony.attia1@gmail.com>
 *
 */
public class GenerateJsonLifeCycleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenerateJsonLifeCycleServlet() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		generateJSON(req, resp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		generateJSON(req, resp);
	}

	/**
	 * Generation of JSON content.
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void generateJSON(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, JsonGenerationException, JsonMappingException {
		HandlerServlet handler = new HandlerServlet();
		ObjectMapper mapper = new ObjectMapper();
		LifeCycle lifeCycle = new LifeCycle();
		LifeCycleContainerJson lifeCycleManagerListToJson = new LifeCycleContainerJson();
		List<LifeCycleManager> lifecycleManagerList = new ArrayList<LifeCycleManager>();

		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();

		// Load spring context
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		LifeCycleContainer managerContainerList = ((LifeCycleContainer) context.getBean("lifecycleContainer"));
		lifecycleManagerList = managerContainerList.getManagerList();

		handler.init();

		// Get list of all managers from a manager container xml file
		lifeCycleManagerListToJson = handler.getManagerListAttrs(lifecycleManagerList);

		// display list of all manager in a json format on the browser
		if (req.getParameter("action") != null) {
			if (req.getParameter("action").equals("showList")) {
				out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lifeCycleManagerListToJson));
			}
		}

		// display lifecycle content in json format on the browser
		if (req.getParameter("idManagerLifeCycle") != null) {
			int idManagerLifeCycle = Integer.parseInt(req.getParameter("idManagerLifeCycle"));
			if (idManagerLifeCycle >= 0 && idManagerLifeCycle <= lifecycleManagerList.size()) {
				lifeCycle = handler.getLifeCycleInformations(lifecycleManagerList.get(idManagerLifeCycle));
				out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lifeCycle));
			}
		}
	}

}
