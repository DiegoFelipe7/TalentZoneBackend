package co.com.sofka.mongo.products;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDBRepository extends ReactiveMongoRepository<ProductsDocument, String>, ReactiveQueryByExampleExecutor<ProductsDocument>, ReactiveCrudRepository<ProductsDocument,String> {
}
