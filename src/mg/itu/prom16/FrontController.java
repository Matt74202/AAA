package mg.itu.prom16;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import annotation.Controller;
import exception.InvalidControllerProviderException;
import exception.InvalidReturnTypeExcpetion;
import exception.UrlNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.Mapping;
import util.ModelAndView;
import util.Utilitaire;

public class FrontController extends HttpServlet {
    HashMap<String, Mapping> urlMappings;

    public void init() throws ServletException {
        try {
            String packageName = getInitParameter("package");
            if (packageName == null || packageName.isEmpty()) {
                throw new InvalidControllerProviderException("Invalid Controller Provider ");
            }
            Vector<String> controllers = Utilitaire.getListController(packageName, Controller.class);
            HashMap<String, Mapping> temp = Utilitaire.getMapping(controllers);
            setUrlMappings(temp);
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        PrintWriter out = response.getWriter();
        try {
            String url = request.getRequestURI().substring(request.getContextPath().length());
            Mapping mapping = urlMappings.get(url);
            out.println(mapping);
            if (mapping != null) {
                Class<?> clazz = Class.forName(mapping.getClassName());
                Method method = mapping.getMethod();
                Object result = mapping.invoke(request, clazz, method);
                if (result instanceof String) {
                    out.println(result);
                // } else if (result instanceof ModelAndView) {
                    ModelAndView modelAndView = (ModelAndView) result;
                    HashMap<String, Object> data = modelAndView.getData();
                    for (String key : data.keySet()) {
                        request.setAttribute(key, data.get(key));
                    }
                    request.getRequestDispatcher(modelAndView.getUrl()).forward(request, response);
                } else {
                    throw new InvalidReturnTypeExcpetion("Invalid return type");
                }
            } else {
                throw new UrlNotFoundException("Url not found ");
            }
        } catch (UrlNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the urlMappings
     */
    public HashMap<String, Mapping> getUrlMappings() {
        return urlMappings;
    }

    /**
     * @param urlMappings the urlMappings to set
     */
    public void setUrlMappings(HashMap<String, Mapping> urlMappings) {
        this.urlMappings = urlMappings;
    }

}