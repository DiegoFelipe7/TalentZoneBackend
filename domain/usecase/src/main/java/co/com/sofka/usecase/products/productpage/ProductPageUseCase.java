package co.com.sofka.usecase.products.productpage;

import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class ProductPageUseCase implements BiFunction<Integer , Integer,Flux<Products>> {
    private final ProductsRepository productsRepository;
    @Override
    public Flux<Products> apply(Integer page, Integer pageSize) {
        return productsRepository.findAll()
                .skip((long) (page - 1) *pageSize)
                .take(pageSize);
    }
}
