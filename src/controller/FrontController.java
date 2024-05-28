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

    // String packagePath;
    // boolean isChecked;
    // Vector<String> controllers;
    HashMap<String,Mapping> map;

    // public void init(){
    //     try{
    //         controllers= new Utilitaire().getListController(this.getInitParameter("package"), AnnotationController.class);
    //         isChecked= true;
    //     }
    //     catch (Exception e){
    //         throw new RuntimeException();
    //     }
    // }
    public void init() throws ServletException {
        try {
            String package_name = this.getInitParameter("package_name");
            map = Util.getAllClassesSelonAnnotation(package_name,ControllerAnnotation.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        // PrintWriter out = response.getWriter();
        // // String url = request.getRequestURL().toString();
        // // out.println("Tonga eto amin'ny " + url);
        // if (!isChecked){
        //     init();
        // }
        // else{
        //     for (int i=0; i<controllers.size(); i++){
        //         out.println(controllers.get(i));
        //     }
        // }
        try{
            String stringUrl = request.getRequestURL().toString();
            Boolean ifUrlExist = false;
            PrintWriter out = response.getWriter();
            
            for (String cle : map.keySet()) {
                if(cle.equals(request.getRequestURI().toString())){
                    out.println("Votre url : "+stringUrl +" est associe a la methode : "+ map.get(cle).getMethodeName()+" dans la classe : "+(map.get(cle).getClassName()));
                    ifUrlExist = true;
                }
            }
                if (!ifUrlExist) {
                    out.println("Aucune methode n'est associe a l url : "+stringUrl );
                }
            }
        catch(Exception e){ }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException{
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        processRequest(request, response);
    }
}
