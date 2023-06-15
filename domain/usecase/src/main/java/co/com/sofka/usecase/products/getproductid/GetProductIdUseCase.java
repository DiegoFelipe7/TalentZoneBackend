package co.com.sofka.usecase.products.getproductid;

import co.com.sofka.model.ex.BusinessException;
import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetProductIdUseCase implements Function<String , Mono<Products>> {
    private final ProductsRepository productsRepository;
    @Override
    public Mono<Products> apply(String id) {
        return productsRepository.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessException.Type.ERROR_EL_PRODUCTO_NO_EXISTES)));
    }
}
