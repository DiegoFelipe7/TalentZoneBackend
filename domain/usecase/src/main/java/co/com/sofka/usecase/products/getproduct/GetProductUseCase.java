package co.com.sofka.usecase.products.getproduct;

import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;
@RequiredArgsConstructor
public class GetProductUseCase implements Supplier<Flux<Products>> {
    private final ProductsRepository productsRepository;
    @Override
    public Flux<Products> get() {
        return productsRepository.findAll();
    }
}
