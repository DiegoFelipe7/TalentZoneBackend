package co.com.sofka.api;

import co.com.sofka.api.buys.BuysHandler;
import co.com.sofka.api.buys.BuysRouterRest;
import co.com.sofka.api.products.ProductsHandler;
import co.com.sofka.api.products.ProductsRouterRest;
import co.com.sofka.model.buys.Buys;
import co.com.sofka.model.buys.TypeOfDocument;
import co.com.sofka.model.buys.gateways.BuysRepository;
import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import co.com.sofka.usecase.buys.savebuys.SaveBuysUseCase;
import co.com.sofka.usecase.buys.shoppinglist.ShoppingListUseCase;
import co.com.sofka.usecase.buys.usershoppinglist.UserShoppingListUseCase;
import co.com.sofka.usecase.products.editproduct.EditProductUseCase;
import co.com.sofka.usecase.products.getproduct.GetProductUseCase;
import co.com.sofka.usecase.products.getproductid.GetProductIdUseCase;
import co.com.sofka.usecase.products.productpage.ProductPageUseCase;
import co.com.sofka.usecase.products.removeproduct.RemoveProductUseCase;
import co.com.sofka.usecase.products.saveproduct.SaveProductUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ContextConfiguration(classes = {BuysRouterRest.class, BuysHandler.class})
@WebFluxTest
public class RouterRestTestBuy {
    @Autowired
    private ApplicationContext context;
    @MockBean
    private ShoppingListUseCase shoppingListUseCase;
    @MockBean
    private SaveBuysUseCase saveBuysUseCase;
    @MockBean
    private UserShoppingListUseCase userShoppingListUseCase;

    @Autowired
    private WebTestClient webTestClient;


    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void testListBuys() {
        List<Products> productsList = List.of(  new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200 , 30));

        Buys buys = new Buys("648a31c75d7bb5aa8ws4dc4", LocalDateTime.now(), TypeOfDocument.CC,"1058788349","Diego Muñoz",productsList);
        Buys buys1 = new Buys("638431c75d7bb5aa8ws4dc4", LocalDateTime.now(), TypeOfDocument.CC,"1058788349","Diego Muñoz",productsList);

        Flux<Buys> buysFlux = Flux.just(buys,buys1);
        ShoppingListUseCase shoppingListUseCase1 = Mockito.mock(ShoppingListUseCase.class);
        Mockito.when(shoppingListUseCase.get()).thenReturn(buysFlux);

        webTestClient.get()
                .uri("/api/buys/history")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Buys.class)
                .value(userResponse -> {
                            System.out.println(userResponse);
                            Assertions.assertThat(userResponse.get(0).getId()).isEqualTo("648a31c75d7bb5aa8ws4dc4");
                        }
                );
    }

    @Test
    void testListProductUser() {
        List<Products> productsList = List.of(  new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200 , 30));

        Buys buys = new Buys("648a31c75d7bb5aa8ws4dc4", LocalDateTime.now(), TypeOfDocument.CC,"1058788349","Diego Muñoz",productsList);
        Buys buys1 = new Buys("638431c75d7bb5aa8ws4dc4", LocalDateTime.now(), TypeOfDocument.CC,"1058788349","Diego Muñoz",productsList);

        Flux<Buys> buysFlux = Flux.just(buys,buys1);
        UserShoppingListUseCase userShoppingListUseCase1 = Mockito.mock(UserShoppingListUseCase.class);
        Mockito.when(userShoppingListUseCase.apply(anyString())).thenReturn(buysFlux);

        webTestClient.get()
                .uri("/api/buys/history/{identification}" , "1058788349")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Buys.class)
                .value(userResponse -> {
                            System.out.println(userResponse);
                            Assertions.assertThat(userResponse.get(0).getId()).isEqualTo("648a31c75d7bb5aa8ws4dc4");
                        }
                );
    }

//    @Test
//    void saveProductUser() {
//        List<Products> productsList = List.of(  new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200 ,10));
//
//        Buys buys = new Buys("648a31c75d7bb5aa8ws4dc4", LocalDateTime.now(), TypeOfDocument.CC,"1058788349","Diego Muñoz",productsList);
//
//        Mono<Buys> buysMono = Mono.just(buys);
//        SaveBuysUseCase saveBuysUseCase1 = Mockito.mock(SaveBuysUseCase.class);
//        Mockito.when(saveBuysUseCase.saveBuys(any())).thenReturn(buysMono);
//
//        webTestClient.post()
//                .uri("/api/buys")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .body(Mono.just(buys), Buys.class)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(Buys.class)
//                .value(userResponse -> {
//                            Assertions.assertThat(userResponse.getId()).isEqualTo("648a31c75d7bb5aa8ws4dc4");
//
//                        }
//                );
//
//    }
}
