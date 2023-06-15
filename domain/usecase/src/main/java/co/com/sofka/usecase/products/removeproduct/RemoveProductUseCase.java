package co.com.sofka.usecase.products.removeproduct;

import co.com.sofka.model.ex.BusinessException;
import co.com.sofka.model.ex.Response;
import co.com.sofka.model.products.gateways.ProductsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.*;

@RequiredArgsConstructor
public class RemoveProductUseCase implements Function<String , Mono<Response>> {
    private final ProductsRepository productsRepository;


    @Override
    public Mono<Response> apply(String id) {
        return productsRepository.existsById(id).flatMap(ele->{
            if(ele){
                return productsRepository.deleteById(id).thenReturn(new Response("Success","Producto eliminado"));
            }
            return Mono.error(new BusinessException(BusinessException.Type.ERROR_EL_PRODUCTO_NO_EXISTES));
        });
    }

}
