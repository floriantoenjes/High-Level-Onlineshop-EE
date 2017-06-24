package de.java2enterprise.onlineshop;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ImageServlet", urlPatterns = "/image")
public class ImageServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            Query query = emf.createEntityManager().createQuery("SELECT i.foto FROM Item i WHERE i.id = :id");
            query.setParameter("id", Long.parseLong(id));
            byte[] foto = (byte[]) query.getSingleResult();
            response.reset();
            response.getOutputStream().write(foto);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
    }
}
