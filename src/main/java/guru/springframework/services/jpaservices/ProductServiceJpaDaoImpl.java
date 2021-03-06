package guru.springframework.services.jpaservices;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductFormToProduct;
import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import guru.springframework.services.SendTextMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by jt on 12/9/15.
 */
@Service
@Profile("jpadao")
public class ProductServiceJpaDaoImpl extends AbstractJpaDaoService implements ProductService {

    private ProductFormToProduct productFormToProduct;
    private SendTextMessageService sendTextMessageService;

    @Autowired
    public void setProductFormToProduct(ProductFormToProduct productFormToProduct) {
        this.productFormToProduct = productFormToProduct;
    }

    @Autowired
    public void setSendTextMessageService(SendTextMessageService sendTextMessageService) {
        this.sendTextMessageService = sendTextMessageService;

    }

    @Override
    public List<Product> listAll() {
        sendTextMessageService.sendTextMessage("Listing Products");
        EntityManager em = emf.createEntityManager();

        return em.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public Product getById(Integer id) {
        sendTextMessageService.sendTextMessage("Requested Product ID: " + id);

        EntityManager em = emf.createEntityManager();

        return em.find(Product.class, id);
    }

    @Override
    public Product saveOrUpdate(Product domainObject) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Product savedProduct = em.merge(domainObject);
        em.getTransaction().commit();

        return savedProduct;
    }

    @Override
    public Product saveOrUpdateProductForm(ProductForm productForm) {
        return saveOrUpdate(productFormToProduct.convert(productForm));
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.find(Product.class, id));
        em.getTransaction().commit();
    }
}
