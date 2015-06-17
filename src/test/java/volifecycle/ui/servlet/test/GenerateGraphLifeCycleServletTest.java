package volifecycle.ui.servlet.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.jetty.testing.HttpTester;
import org.eclipse.jetty.testing.ServletTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import volifecycle.ui.servlet.GenerateGraphLifeCycleServlet;

@RunWith(MockitoJUnitRunner.class)
public class GenerateGraphLifeCycleServletTest {

    private ServletTester servletTester;
    private HttpTester request;
    private HttpTester response;

    @Before
    public void setUp() throws Exception {
        servletTester = new ServletTester();
        servletTester.addServlet(GenerateGraphLifeCycleServlet.class, "/GenerateGraphLifeCycleServlet");
        servletTester.start();

        request = new HttpTester();
        request.setMethod("GET");
        request.setURI("/GenerateGraphLifeCycleServlet");

        request.setVersion("HTTP/1.0");
        System.out.println(servletTester.getContext().getContextPath() + request.getURI());
        response = new HttpTester();
        response.parse(servletTester.getResponses(request.generate()));
    }

    @Test
    public void testGenerateGraphLifeCycleServletViews() {

        System.err.println(response.getHeader("Host"));
        System.err.println(response.getContent());
        assertEquals(200, response.getStatus());
    }
}
