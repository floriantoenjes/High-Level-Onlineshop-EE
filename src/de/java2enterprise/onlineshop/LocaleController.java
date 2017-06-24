package de.java2enterprise.onlineshop;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LocaleController implements Serializable {
    private static final long serialVersionUID = 1L;

    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String change(String lang) {
        this.lang = lang;
        return "index.xhtml";
    }
}
