package mg.itu.prom16;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.data.InitParameter;
import exception.DuplicateUrlException;
import exception.InvalidControllerPackageException;
import manager.handler.ExceptionHandler;
import manager.handler.RequestHandler;
import util.Mapping;


@MultipartConfig
public class FrontController extends HttpServlet {

    private static Map<String, Mapping> urlMappings;
    private static Exception exception = null;
    private static InitParameter initParameter;
    private static String customErrorPage;

    private transient RequestHandler requestHandler;

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            requestHandler.handleRequest(this, request, response);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (ServletException e) {
            ExceptionHandler.handleException(
                    new ServletException("A servlet error has occurred while executing doGet method", e.getCause()), req,
                    resp);
        } catch (IOException e) {
            ExceptionHandler.handleException(
                    new IOException("An IO error has occurred while executing doGet method", e.getCause()), req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (ServletException e) {
            ExceptionHandler.handleException(
                    new ServletException("A servlet error has occurred while executing doPost method", e.getCause()), req,
                    resp);
        } catch (IOException e) {
            ExceptionHandler.handleException(
                    new IOException("An IO error has occurred while executing doPost method", e.getCause()), req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            requestHandler = new RequestHandler();
            requestHandler.init(this);
        } catch (InvalidControllerPackageException | DuplicateUrlException e) {
            setException(e);
        } catch (Exception e) {
            setException(new Exception("An error has occurred during initialization + " + e.getMessage(), e.getCause()));
        }
    }

    public Map<String, Mapping> getUrlMapping() {
        return urlMappings;
    }

    public static void setUrlMapping(Map<String, Mapping> urlMapping) {
        urlMappings = urlMapping;
    }


    public Exception getException() {
        return exception;
    }

    public static void setException(Exception newException) {
        exception = newException;
    }


    public InitParameter getInitParameter() {
        return initParameter;
    }


    public static void setInitParameter(InitParameter newInitParameter) {
        initParameter = newInitParameter;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }

    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }


    public static String getCustomErrorPage() {
        return customErrorPage;
    }

    public static void setCustomErrorPage(String customErrorPage) {
        FrontController.customErrorPage = customErrorPage;
    }
}