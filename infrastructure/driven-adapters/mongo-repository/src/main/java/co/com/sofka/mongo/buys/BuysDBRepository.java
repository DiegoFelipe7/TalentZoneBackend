package co.com.sofka.mongo.buys;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuysDBRepository extends ReactiveMongoRepository<BuysDocument, String>, ReactiveQueryByExampleExecutor<BuysDocument>, ReactiveCrudRepository<BuysDocument,String> {
}
