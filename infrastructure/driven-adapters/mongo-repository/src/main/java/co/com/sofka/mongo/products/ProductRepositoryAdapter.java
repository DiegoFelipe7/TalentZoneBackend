package co.com.sofka.mongo.products;

import co.com.sofka.model.ex.BusinessException;
import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import co.com.sofka.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductRepositoryAdapter extends AdapterOperations<Products, ProductsDocument, String, ProductDBRepository>
 implements ProductsRepository
{
    public ProductRepositoryAdapter(ProductDBRepository repository, ObjectMapper mapper){
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Products.class));
   }


    @Override
    public Mono<Boolean> existsById(String id) {
        return repository.existsById(id);
    }
}
