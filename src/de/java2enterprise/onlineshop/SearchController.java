package de.java2enterprise.onlineshop;

import de.java2enterprise.onlineshop.model.Item;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class SearchController implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction ut;

    private List<Item> items;

    public List<Item> getItems() {
        items = findAll();
        return items;
    }

    public List<Item> findAll() {
        try {
            TypedQuery<Item> query = emf.createEntityManager().createNamedQuery("Item.findAll", Item.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
