package co.com.sofka.usecase.productpage;

import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import co.com.sofka.usecase.products.getproduct.GetProductUseCase;
import co.com.sofka.usecase.products.productpage.ProductPageUseCase;
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
public class ProductPageUseCaseTest {
    @Mock
    ProductsRepository productsRepository;
    @InjectMocks
    ProductPageUseCase productPageUseCase;
    @BeforeEach
    public void setUp(){
        productsRepository = mock(ProductsRepository.class);
        productPageUseCase = new ProductPageUseCase(productsRepository);
    }

    @Test
    void ListProducts() {
        Products products = new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200);
        Mono<Products> productsMono = Mono.just(products);

        when(productsRepository.findAll()).thenReturn(Flux.just(products));

        StepVerifier.create(productPageUseCase.apply(1,3))
                .expectNextMatches(res-> res.getId().equals("648a31c75d74aa1f7705cb88"))
                .expectComplete()
                .verify();

    }
}
