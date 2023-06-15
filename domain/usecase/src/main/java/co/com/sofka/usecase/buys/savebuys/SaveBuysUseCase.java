package co.com.sofka.usecase.buys.savebuys;

import co.com.sofka.model.buys.Buys;
import co.com.sofka.model.buys.gateways.BuysRepository;
import co.com.sofka.model.ex.BusinessException;
import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class SaveBuysUseCase implements Function<Buys, Mono<Buys>> {
    private final ProductsRepository productsRepository;
    private final BuysRepository buysRepository;

    @Override
    public Mono<Buys> apply(Buys buys) {
        return Flux.fromIterable(buys.getProducts())
                .flatMap(this::validateProduct)
                .collectList()
                .flatMap(validatedProducts -> {
                    if (validatedProducts.size() != buys.getProducts().size()) {
                        return Mono.error(new BusinessException(BusinessException.Type.ERROR_EN_LA_CANTIDAD));
                    }
                    return eliminateQuantities(validatedProducts)
                            .then(saveBuys(buys));
                });
    }

    private Mono<Products> validateProduct(Products product) {
        return productsRepository.findById(product.getId())
                .switchIfEmpty(Mono.error(new BusinessException(BusinessException.Type.ERROR_EL_PRODUCTO_NO_EXISTES)))
                .flatMap(data -> {
                    if (data.getMin() >= product.getQuantity() || data.getInInventory() <= product.getQuantity()) {
                        return Mono.error(new BusinessException(BusinessException.Type.ERROR_EN_LA_CANTIDAD));
                    }
                    return Mono.just(product);
                });
    }
    private Mono<Buys> saveBuys(Buys buys) {
        return buysRepository.save(buys);
    }
    private Mono<Void> eliminateQuantities(List<Products> validatedProducts) {
        return Flux.fromIterable(validatedProducts)
                .flatMap(product -> productsRepository.findById(product.getId())
                        .flatMap(p -> {
                            p.setId(p.getId());
                            p.setInInventory(p.getInInventory() - product.getQuantity());
                            return productsRepository.save(p);
                        }))
                .then();
    }
}
