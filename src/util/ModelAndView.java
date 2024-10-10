package util;

import java.util.HashMap;

public class ModelAndView {
    String url ;
    HashMap<String ,Object> data;
    
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @return the data
     */
    public HashMap<String, Object> getData() {
        return data;
    }
    /**
     * @param data the data to set
     */
    public void setData(HashMap<String, Object> data) {
        this.data = data;
    } 

    public void addObject(String variableName,Object value){
        if(data == null){
             data = new HashMap<String ,Object>();
        }
        this.data.put(variableName, value);
    }
    /**
     * 
     */
    public ModelAndView() {

    }
    
    
}
