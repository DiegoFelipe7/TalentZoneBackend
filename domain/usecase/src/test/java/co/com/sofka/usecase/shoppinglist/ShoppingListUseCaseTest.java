package co.com.sofka.usecase.shoppinglist;

import co.com.sofka.model.buys.Buys;
import co.com.sofka.model.buys.TypeOfDocument;
import co.com.sofka.model.buys.gateways.BuysRepository;
import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import co.com.sofka.usecase.buys.shoppinglist.ShoppingListUseCase;
import co.com.sofka.usecase.products.getproduct.GetProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShoppingListUseCaseTest {
    @Mock
    BuysRepository buysRepository;
    @InjectMocks
    ShoppingListUseCase shoppingListUseCase;
    @BeforeEach
    public void setUp(){
        buysRepository = mock(BuysRepository.class);
        shoppingListUseCase = new ShoppingListUseCase(buysRepository);
    }

    @Test
    void ListBuys() {

        List<Products> productsList = List.of(  new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200 , 30));

        Buys buys = new Buys("648a31c75d7bb5aa8ws4dc4",LocalDateTime.now(),TypeOfDocument.CC,"1058788349","Diego Muñoz",productsList);

        Mono<Buys> buysMono = Mono.just(buys);

        when(buysRepository.findAll()).thenReturn(Flux.just(buys));

        StepVerifier.create(shoppingListUseCase.get())
                .expectNextMatches(res-> res.getId().equals("648a31c75d7bb5aa8ws4dc4"))
                .expectComplete()
                .verify();

    }
}
