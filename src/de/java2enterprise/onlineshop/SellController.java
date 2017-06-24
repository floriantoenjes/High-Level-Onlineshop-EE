package de.java2enterprise.onlineshop;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Item;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Logger;

@Named
@RequestScoped
public class SellController implements Serializable {
    private static final Long serialVersionUID = 1L;

    private final static int MAX_IMAGE_LENGTH = 400;

    private final static Logger log = Logger.getLogger(SigninController.class.toString());

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction ut;

    private Part part;

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    @Inject
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String persist(SigninController signinController) {
        try {
            ut.begin();
            EntityManager em = emf.createEntityManager();
            InputStream in = part.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int length = 0; (length = in.read(buffer)) > 0;) {
                baos.write(buffer, 0, length);
            }
            item.setFoto(scale(baos.toByteArray()));

            Customer customer = signinController.getCustomer();

            customer = em.find(Customer.class, customer.getId());

            item.setSeller(customer);
            em.persist(item);
            ut.commit();

            log.info("Offered item: " + item);

            FacesMessage m = new FacesMessage("Successfully saved item!",
                    "You offered the item " + item);
            FacesContext.getCurrentInstance().addMessage("sellForm", m);
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage fm;
            if (e.getCause() != null) {
                fm = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getCause().getMessage());
            } else {
                fm = new FacesMessage(e.getMessage());
            }
            FacesContext.getCurrentInstance().addMessage("sellForm", fm);
        }
        return "sell.xhtml";
    }

    public byte[] scale(byte[] foto) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(foto);
        BufferedImage originalBufferedImage = ImageIO.read(byteArrayInputStream);

        double originalWidth = (double) originalBufferedImage.getWidth();
        double originalHeight = (double) originalBufferedImage.getHeight();

        double relevantLength = originalWidth > originalHeight ? originalWidth : originalHeight;

        double scaleFactor = MAX_IMAGE_LENGTH / relevantLength;

        int width = (int) Math.round(originalWidth * scaleFactor);
        int height = (int) Math.round(originalHeight * scaleFactor);

        BufferedImage resizedBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resizedBufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        AffineTransform affineTransform = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
        g2d.drawRenderedImage(originalBufferedImage, affineTransform);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedBufferedImage, "PNG", baos);

        return baos.toByteArray();
    }
}
