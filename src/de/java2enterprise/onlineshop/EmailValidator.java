package de.java2enterprise.onlineshop;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Locale;
import java.util.regex.Pattern;

@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String value = (String) o;

        if (!Pattern.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", value)) {
            String label = ((HtmlInputText) uiComponent).getLabel();
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            FacesMessage fm = new FacesMessage(label + ": has to be valid !");
            throw new ValidatorException(fm);
        }
    }
}
