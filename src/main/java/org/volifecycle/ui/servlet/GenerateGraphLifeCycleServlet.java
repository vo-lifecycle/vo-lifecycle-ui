package org.volifecycle.ui.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * Generation of lifecycle graphical representation servlet.
 * 
 * @author anthony attia <anthony.attia1@gmail.com>
 */
public class GenerateGraphLifeCycleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenerateGraphLifeCycleServlet() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		generateGraph(request, response);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		generateGraph(request, response);
	}

	/**
	 * Generation of graph.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void generateGraph(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		// response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		if (request.getParameter("resources") != null && request.getParameter("ext") != null) {

			String resource = request.getParameter("resources");
			String extension = request.getParameter("ext");

			InputStream resourceContent = this.getClass().getResourceAsStream(extension + "/" + resource + "." + extension);

			IOUtils.copy(resourceContent, out);

		} else {
			InputStream is = this.getClass().getResourceAsStream("html/graph.html");
			IOUtils.copy(is, out);
		}
	}
}
