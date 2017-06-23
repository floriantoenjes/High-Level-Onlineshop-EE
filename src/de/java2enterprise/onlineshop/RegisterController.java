package de.java2enterprise.onlineshop;

import de.java2enterprise.onlineshop.model.Customer;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

@Named
@RequestScoped
public class RegisterController {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction ut;

    @Inject
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String persist() {
        try {
            ut.begin();
            emf.createEntityManager().persist(customer);
            ut.commit();
            return "confirm";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/reject.xhml";
    }
}
