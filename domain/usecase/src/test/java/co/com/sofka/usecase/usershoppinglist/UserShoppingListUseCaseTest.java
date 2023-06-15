package co.com.sofka.usecase.usershoppinglist;

import co.com.sofka.model.buys.Buys;
import co.com.sofka.model.buys.TypeOfDocument;
import co.com.sofka.model.buys.gateways.BuysRepository;
import co.com.sofka.model.products.Products;
import co.com.sofka.usecase.buys.shoppinglist.ShoppingListUseCase;
import co.com.sofka.usecase.buys.usershoppinglist.UserShoppingListUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserShoppingListUseCaseTest {
    @Mock
    BuysRepository buysRepository;
    @InjectMocks
    UserShoppingListUseCase userShoppingListUseCase;
    @BeforeEach
    public void setUp(){
        buysRepository = mock(BuysRepository.class);
        userShoppingListUseCase = new UserShoppingListUseCase(buysRepository);
    }

    @Test
    void ListBuysUser() {

        List<Products> productsList = List.of(  new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200 , 30));

        Buys buys = new Buys("648a31c75d7bb5aa8ws4dc4", LocalDateTime.now(), TypeOfDocument.CC,"1058788349","Diego Muñoz",productsList);

        Mono<Buys> buysMono = Mono.just(buys);

        when(buysRepository.findAll()).thenReturn(Flux.just(buys));

        StepVerifier.create(userShoppingListUseCase.apply(buys.getIdentification()))
                .expectNextMatches(res-> res.getId().equals("648a31c75d7bb5aa8ws4dc4"))
                .expectComplete()
                .verify();

    }
}
