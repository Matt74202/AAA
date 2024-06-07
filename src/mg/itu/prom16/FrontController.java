package mg.itu.prom16;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import annotation.Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import util.Mapping;
import util.ModelAndView;
import util.Utilitaire;

import annotation.Get;

public class FrontController extends HttpServlet {
    HashMap<String, Mapping> urlMappings;

    public void init() {
        try {
            String packageName = getInitParameter("package");
            Vector<String> controllers = Utilitaire.getListController(packageName, Controller.class);

            HashMap<String, Mapping> temp = new HashMap<String, Mapping>();

            for (String controller : controllers) {
                Class<?> clazz = Class.forName(controller);
                List<Method> classMethods = Utilitaire.getClassMethodsWithAnnotation(clazz, Get.class);
                for (Method method : classMethods) {
                    String annotationValue = method.getAnnotation(Get.class).value();
                    temp.put(annotationValue, new Mapping(controller, method.getName()));
                }
            }
            setUrlMappings(temp);
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        PrintWriter out = response.getWriter();
        try {
            String url = request.getRequestURI().substring(request.getContextPath().length());
            Mapping mapping = urlMappings.get(url);

            if (mapping != null) {
                Class<?> clazz = Class.forName(mapping.getClassName());
                Method method = clazz.getMethod(mapping.getMethodName());
                Object result = method.invoke(clazz.getConstructor().newInstance());
                if(result instanceof String){
                    out.println(result);
                }else if(result instanceof ModelAndView){
                    ModelAndView modelAndView = (ModelAndView)result;
                    HashMap<String , Object> data = modelAndView.getData(); 
                    for(String key : data.keySet()){
                        request.setAttribute(key, data.get(key));
                    }
                    request.getRequestDispatcher(modelAndView.getUrl()).forward(request, response);
                }else{
                    System.out.println("not recognized");
                }
            } else {
                out.println("Url not found");
            }

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | ServletException | IOException e) {
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