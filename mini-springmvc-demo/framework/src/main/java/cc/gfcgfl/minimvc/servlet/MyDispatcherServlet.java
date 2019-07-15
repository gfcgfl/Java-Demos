package cc.gfcgfl.minimvc.servlet;

import cc.gfcgfl.minimvc.handler.MyMappingHandler;
import cc.gfcgfl.minimvc.handler.MyMappingHandlerManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MyDispatcherServlet extends HttpServlet{


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();

//        System.out.println("into dispatcher========and uri is " + requestURI);
        for (MyMappingHandler mappingHandler : MyMappingHandlerManager.mappingHandlerList) {
            if(requestURI.equals(mappingHandler.getUri())){
                try {
//                    System.out.println("uri matches++++++++++++++");
                    mappingHandler.handle(req, resp);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}