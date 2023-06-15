package co.com.sofka.usecase.products.saveproduct;

import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class SaveProductUseCase implements Function<Products, Mono<Products> > {
    private final ProductsRepository productsRepository;
    @Override
    public Mono<Products> apply(Products products) {
        return productsRepository.save(products);
    }
}
