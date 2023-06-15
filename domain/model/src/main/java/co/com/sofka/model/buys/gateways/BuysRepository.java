package co.com.sofka.model.buys.gateways;
import co.com.sofka.model.buys.Buys;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BuysRepository {
    Mono<Buys> save(Buys buys);
    Flux<Buys> findAll();


}
