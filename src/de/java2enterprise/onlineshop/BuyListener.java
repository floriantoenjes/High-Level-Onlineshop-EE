package de.java2enterprise.onlineshop;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class BuyListener implements ActionListener {

    @Override
    public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
        FacesMessage fm = new FacesMessage("Sie versuchen einen Artikel zu kaufen!");
        FacesContext.getCurrentInstance().addMessage("buyForm", fm);
    }
}
