package co.com.sofka.mongo.buys;

import co.com.sofka.model.buys.Buys;
import co.com.sofka.model.buys.gateways.BuysRepository;
import co.com.sofka.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BuysRepositoryAdapter extends AdapterOperations<Buys,BuysDocument, String, BuysDBRepository>
implements BuysRepository
{

    public BuysRepositoryAdapter(BuysDBRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Buys.class));
    }
}
