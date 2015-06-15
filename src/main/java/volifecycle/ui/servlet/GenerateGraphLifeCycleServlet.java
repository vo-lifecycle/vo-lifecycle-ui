package volifecycle.ui.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * @author anthony attia <anthony.attia1@gmail.com>
 */
public class GenerateGraphLifeCycleServlet extends HttpServlet {
    /**
     * defautl serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateGraphLifeCycleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Return lifecylcle graph view html.
     * 
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      res
     */

    @Override
    protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
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

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * @Override nse)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
