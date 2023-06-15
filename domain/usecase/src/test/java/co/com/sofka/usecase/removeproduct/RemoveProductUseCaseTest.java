package co.com.sofka.usecase.removeproduct;

import co.com.sofka.model.ex.Response;
import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import co.com.sofka.usecase.products.getproduct.GetProductUseCase;
import co.com.sofka.usecase.products.removeproduct.RemoveProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemoveProductUseCaseTest {
    @Mock
    ProductsRepository productsRepository;
    @InjectMocks
    RemoveProductUseCase removeProductUseCase;
    @BeforeEach
    public void setUp(){
        productsRepository = mock(ProductsRepository.class);
        removeProductUseCase = new RemoveProductUseCase(productsRepository);
    }

    @Test
     void removeProduct() {
        String productId = "648a31c75d74aa1f7705cb88";
        Products products = new Products(productId, "Arroz", 500, true, 8, 200);
        Response successResponse = new Response("Success", "Producto eliminado");

        when(productsRepository.existsById(productId)).thenReturn(Mono.just(true));
        when(productsRepository.deleteById(productId)).thenReturn(Mono.empty());

        StepVerifier.create(removeProductUseCase.apply(productId))
                .expectNextMatches(res-> res.getType().equals(successResponse.getType()))
                .expectComplete()
                .verify();
    }
}
