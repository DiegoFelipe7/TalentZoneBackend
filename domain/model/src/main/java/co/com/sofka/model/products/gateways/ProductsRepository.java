package co.com.sofka.model.products.gateways;

import co.com.sofka.model.products.Products;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductsRepository {

    Mono<Products> save(Products products);

    Flux<Products> findAll();
    Mono<Products> findById(String id);
    Mono<Void> deleteById(String id);

    Mono<Boolean> existsById(String id);

}
