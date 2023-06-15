package co.com.sofka.api;

import co.com.sofka.api.products.ProductsHandler;
import co.com.sofka.api.products.ProductsRouterRest;
import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import co.com.sofka.usecase.products.getproduct.GetProductUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@ContextConfiguration(classes = {ProductsRouterRest.class, ProductsHandler.class})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testListProduct() {
        webTestClient.get() //peticion GET
                .uri("/api/products")//endpoint
                .accept(MediaType.APPLICATION_JSON)//Consumir un mediatype
                .exchange() //enviar el request al endpoint y consumir el response
                .expectStatus().isOk() //ok=200
                .expectHeader().contentType(MediaType.APPLICATION_JSON)//cabecera
                .expectBodyList(Products.class)//la respuesta retorna un list de productos
                .consumeWith(response -> {
                    List<Products> productos=response.getResponseBody();
                    productos.forEach(p->{
                        System.out.println("test:"+p.getName());
                    });

                    Assertions.assertThat(productos.size()==10).isTrue();
                });

    }


}
