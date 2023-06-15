package co.com.sofka.usecase.products.editproduct;

import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class EditProductUseCase implements BiFunction<String , Products , Mono<Products>> {
    private final ProductsRepository productsRepository;
    @Override
    public Mono<Products> apply(String id, Products products) {
        products.setId(id);
        return productsRepository.save(products);
    }
}
