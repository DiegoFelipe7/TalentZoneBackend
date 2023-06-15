package co.com.sofka.api;

import co.com.sofka.api.products.ProductsHandler;
import co.com.sofka.api.products.ProductsRouterRest;
import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import co.com.sofka.usecase.products.getproduct.GetProductUseCase;
import co.com.sofka.usecase.products.getproductid.GetProductIdUseCase;
import co.com.sofka.usecase.products.saveproduct.SaveProductUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static reactor.core.publisher.Mono.when;

@ContextConfiguration(classes = {ProductsRouterRest.class, ProductsHandler.class})
@WebFluxTest
class RouterRestTest {
    @Autowired
    private WebTestClient webTestClient;


    private ApplicationContext context;

    @MockBean
    private GetProductUseCase getProductUseCase;
    private GetProductIdUseCase getProductIdUseCase;

    @Test
    void testListProduct() {

        Products products = new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200);

        when(getProductUseCase.get()).thenReturn(Flux.just(products));

        webTestClient.get()
                .uri("/api/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Products.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.get(0).getId()).isEqualTo("648a31c75d74aa1f7705cb88");
                            Assertions.assertThat(userResponse.get(0).getName()).isEqualTo("Arroz");
                        }
                );


    }
    @Test
    void testListProductId() {

        Products products = new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200);

        when(getProductIdUseCase.apply("648a31c75d74aa1f7705cb88")).thenReturn(Mono.just(products));

        webTestClient.get()
                .uri("/api/product/648a31c75d74aa1f7705cb88")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Products.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getId()).isEqualTo("648a31c75d74aa1f7705cb88");
                        }
                );


    }

//    @Test
//    void testListProduct() {
//       var data= webTestClient.get()
//                .uri("/api/products")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                .expectBodyList(Products.class)
//                .consumeWith(response -> {
//                    List<Products> productos=response.getResponseBody();
//                    productos.forEach(p->{
//                        System.out.println("test:"+p.getName());
//                    });
//
//                    Assertions.assertThat(productos.size()==10).isTrue();
//                });
//
//
//    }



}
