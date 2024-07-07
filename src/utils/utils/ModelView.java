package utils;

import java.util.HashMap;

public class ModelView {
    String url;
    HashMap<String, Object> data;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    
    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public void addObject(String variable, Object valeur) {
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        this.data.put(variable, valeur);
    }
}
