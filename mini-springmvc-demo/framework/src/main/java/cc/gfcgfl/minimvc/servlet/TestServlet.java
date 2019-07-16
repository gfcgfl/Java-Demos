package cc.gfcgfl.minimvc.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: TestServlet
 * @Description:
 * @CreatedBy: fcguo
 * @CreatedAt: 6/18/19 9:36 PM
 **/
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("impl ok");
    }
}
