package co.com.sofka.usecase.editproduct;

import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import co.com.sofka.usecase.products.editproduct.EditProductUseCase;
import co.com.sofka.usecase.products.saveproduct.SaveProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EditProductUseCaseTest {
    @Mock
    ProductsRepository productsRepository;
    @InjectMocks
    EditProductUseCase editProductUseCase;
    @InjectMocks
    SaveProductUseCase saveProductUseCase;
    @BeforeEach
    public void setUp(){
        productsRepository = mock(ProductsRepository.class);
        editProductUseCase = new EditProductUseCase(productsRepository);
        saveProductUseCase = new SaveProductUseCase(productsRepository);
    }

    @Test
    void editProduct(){
        Products products = new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200);
        Products products1 = new Products("648a31c75d74aa1f7705cb88","Postres",500,true,8,200);
        Mono<Products> productsMono = Mono.just(products);

        StepVerifier.create(saveProductUseCase.apply(products));
        when(productsRepository.save(Mockito.any(Products.class))).thenReturn(Mono.just(products1));

        StepVerifier.create(editProductUseCase.apply(products.getId(), products1))
                .expectNextMatches(res-> res.getName().equals("Postres"))
                .expectComplete()
                .verify();

    }
}
