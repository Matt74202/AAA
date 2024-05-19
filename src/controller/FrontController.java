package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.Vector;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import util.Utilitaire;

public class FrontController extends HttpServlet {

    String packagePath;
    boolean isChecked;
    Vector<String> controllers;

    public void init(){
        try{
            controllers= new Utilitaire().getListController(this.getInitParameter("package"), AnnotationController.class);
            isChecked= true;
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        PrintWriter out = response.getWriter();
        // String url = request.getRequestURL().toString();
        // out.println("Tonga eto amin'ny " + url);
        if (!isChecked){
            init();
        }
        else{
            for (int i=0; i<controllers.size(); i++){
                out.println(controllers.get(i));
            }
        }
    }

    // @Override
    // protected void doGet(HttpServletRequest request, HttpServletResponse response)
    //         throws ServletException, IOException, ClassNotFoundException {
    //     processRequest(request, response);
    // }

    // @Override
    // protected void doPost(HttpServletRequest request, HttpServletResponse response)
    //         throws ServletException, IOException, ClassNotFoundException {
    //     processRequest(request, response);
    // }
}
