package co.com.sofka.usecase.getproductid;

import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import co.com.sofka.usecase.products.getproduct.GetProductUseCase;
import co.com.sofka.usecase.products.getproductid.GetProductIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.management.monitor.GaugeMonitor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetProductIdUseCaseTest {
    @Mock
    ProductsRepository productsRepository;
    @InjectMocks
    GetProductIdUseCase getProductIdUseCase;
    @BeforeEach
    public void setUp(){
        productsRepository = mock(ProductsRepository.class);
        getProductIdUseCase = new GetProductIdUseCase(productsRepository);
    }

    @Test
    void getProductId(){
        Products products = new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200);
        Mono<Products> productsMono = Mono.just(products);

        when(productsRepository.findById(products.getId())).thenReturn(productsMono);

        StepVerifier.create(getProductIdUseCase.apply(products.getId()))
                .expectNextCount(1)
                .expectComplete()
                .verify();

    }
}
