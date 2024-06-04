package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Vector;

import annotation.AnnotationController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.Mapping;
import util.Utilitaire;

public class FrontController extends HttpServlet {
    HashMap<String,Mapping> map;
    public void init() throws ServletException {
        try {
            String package_name = this.getInitParameter("package_name");
            map = Utilitaire.getAllClassesSelonAnnotation(package_name,AnnotationController.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processedRequest(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processedRequest(req, res);
    }

    protected void processedRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println("Tongasoa ato am FrontController");
        String url= req.getRequestURI().toString();
        boolean urlexist=false;
        for (String cle:map.keySet()){
            if (cle.equals(url)) {
                //out.println("Votre url : "+url +" est associe a la methode : "+ map.get(cle).getMethodeName()+" dans la classe : "+(map.get(cle).getClassName()));
                Mapping mapping=map.get(url);
                try {
                    Class<?>c=Class.forName(mapping.getClassName());
                    Method m=c.getDeclaredMethod(mapping.getMethodeName());
                    Object instance=c.getDeclaredConstructor().newInstance();
                    Object result=m.invoke(instance);
                    out.println(result.toString());
                    urlexist=true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
               

            }
        }
        if (!urlexist) {
            out.println("Aucune methode n'est associe a l url : "+url);
        }
    }
}

