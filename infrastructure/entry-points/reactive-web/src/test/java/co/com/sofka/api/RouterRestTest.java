package co.com.sofka.api;

import co.com.sofka.api.products.ProductsHandler;
import co.com.sofka.api.products.ProductsRouterRest;
import co.com.sofka.model.ex.Response;
import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import co.com.sofka.usecase.products.editproduct.EditProductUseCase;
import co.com.sofka.usecase.products.getproduct.GetProductUseCase;
import co.com.sofka.usecase.products.getproductid.GetProductIdUseCase;
import co.com.sofka.usecase.products.productpage.ProductPageUseCase;
import co.com.sofka.usecase.products.removeproduct.RemoveProductUseCase;
import co.com.sofka.usecase.products.saveproduct.SaveProductUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static reactor.core.publisher.Mono.when;


@ContextConfiguration(classes = {ProductsRouterRest.class, ProductsHandler.class})
@WebFluxTest
class RouterRestTest {
    @Autowired
    private ApplicationContext context;
    @MockBean
    private GetProductUseCase getProductUseCase;
    @MockBean
    private GetProductIdUseCase getProductIdUseCase;
    @MockBean
    private SaveProductUseCase saveProductUseCase;
    @MockBean
    private EditProductUseCase editProductUseCase;
    @MockBean
    private RemoveProductUseCase removeProductUseCase;

    @MockBean
    private ProductPageUseCase productPageUseCase;



    @Autowired
    private WebTestClient webTestClient;


    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void testListProductId() {
        Products product = new Products("648a31c75d74aa1f7705cb88", "Arroz", 500, true, 8, 200);
        Mono<Products> productMono = Mono.just(product);

        GetProductIdUseCase getProductIdUseCase1 = Mockito.mock(GetProductIdUseCase.class);
        Mockito.when(getProductIdUseCase.apply(anyString())).thenReturn(productMono);

        webTestClient.get()
                .uri("/api/product/{id}", "648a31c75d74aa1f7705cb88")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Products.class)
                .value(productResponse -> {
                    Assertions.assertThat(productResponse.getId()).isEqualTo("648a31c75d74aa1f7705cb88");
                });
    }



    @Test
    void testListProduct() {
        Products products = new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200);
        Products products2 = new Products("648a31c75d12acc1f7705cb88","Comida",500,true,8,200);

        Flux<Products> productsFlux = Flux.just(products,products2);
        GetProductIdUseCase getProductIdUseCase = Mockito.mock(GetProductIdUseCase.class);
        Mockito.when(getProductUseCase.get()).thenReturn(productsFlux);

        webTestClient.get()
                .uri("/api/products")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Products.class)
                .value(userResponse -> {
                    System.out.println(userResponse);
                            Assertions.assertThat(userResponse.get(0).getId()).isEqualTo("648a31c75d74aa1f7705cb88");
                        }
                );
    }

    @Test
    public void testCreateProduct() {

        Products products = new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200);
        Mono<Products> UserMono = Mono.just(products);
        SaveProductUseCase saveProductUseCase1 = Mockito.mock(SaveProductUseCase.class);

        Mockito.when(saveProductUseCase.apply(any())).thenReturn(UserMono);

        webTestClient.post()
                .uri("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(products), Products.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Products.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getId()).isEqualTo("648a31c75d74aa1f7705cb88");

                        }
                );
    }

    @Test
    public void testRemoveProduct() {
        Products products = new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200);

        Response response = new Response("Succes","exitoso");

        Mono<Products> UserMono = Mono.just(products);
        Mono<Response> responseMono = Mono.just(response);
        RemoveProductUseCase saveProductUseCase1 = Mockito.mock(RemoveProductUseCase.class);

        Mockito.when(removeProductUseCase.apply(any())).thenReturn(responseMono);

        webTestClient.delete()
                .uri("/api/product/remove/{id}" ,"648a31c75d74aa1f7705cb88" )
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Response.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getType()).isEqualTo("Succes");

                        }
                );
    }

//    @Test
//    void testListProductPagination() {
//        Products products = new Products("648a31c75d74aa1f7705cb88", "Arroz", 500, true, 8, 200);
//        Products products2 = new Products("648a31c75d12acc1f7705cb88", "Comida", 500, true, 8, 200);
//
//        Flux<Products> productsFlux = Flux.just(products, products2);
//        ProductPageUseCase productPageUseCase = Mockito.mock(ProductPageUseCase.class);
//        Mockito.when(productPageUseCase.apply(ArgumentMatchers.eq(10), ArgumentMatchers.eq(20))).thenReturn(productsFlux);
//
//        webTestClient.get()
//                .uri("/api/products/pagination?min=10&max=20")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(Products.class)
//                .value(productResponse -> {
//                    Assertions.assertThat(productResponse.get(0).getId()).isEqualTo("648a31c75d74aa1f7705cb88");
//                });
//    }


}
