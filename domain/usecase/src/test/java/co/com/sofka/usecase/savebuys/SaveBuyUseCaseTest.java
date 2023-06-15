package co.com.sofka.usecase.savebuys;

import co.com.sofka.model.buys.Buys;
import co.com.sofka.model.buys.TypeOfDocument;
import co.com.sofka.model.buys.gateways.BuysRepository;
import co.com.sofka.model.products.Products;
import co.com.sofka.model.products.gateways.ProductsRepository;
import co.com.sofka.usecase.buys.savebuys.SaveBuysUseCase;
import co.com.sofka.usecase.buys.shoppinglist.ShoppingListUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaveBuyUseCaseTest {
    @Mock
    BuysRepository buysRepository;
    ProductsRepository productsRepository;
    @InjectMocks
    SaveBuysUseCase saveBuysUseCase;
    @BeforeEach
    public void setUp(){
        buysRepository = mock(BuysRepository.class);
        productsRepository= mock(ProductsRepository.class);
        saveBuysUseCase = new SaveBuysUseCase(productsRepository,buysRepository);
    }
    @Test
    void SaveBuys() {
        List<Products> productsList = List.of(  new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200 , 30));
        Buys buys = new Buys("648a31c75d7bb5aa8ws4dc4", LocalDateTime.now(), TypeOfDocument.CC,"1058788349","Diego Muñoz",productsList);
        Mono<Buys> buysMono = Mono.just(buys);
        when(buysRepository.save(buys)).thenReturn(buysMono);

        StepVerifier.create(saveBuysUseCase.saveBuys(buys))
                .expectNextMatches(res->{
                    System.out.println(res);
                 return res.getId().equals("648a31c75d7bb5aa8ws4dc4");
                })
                .expectComplete()
                .verify();

    }

    @Test
    void validateProduct(){
        List<Products> productsList = List.of(  new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200 , 30));
        Buys buys = new Buys("648a31c75d7bb5aa8ws4dc4", LocalDateTime.now(), TypeOfDocument.CC,"1058788349","Diego Muñoz",productsList);
        Mono<Products> productsMono = Mono.just(productsList.get(0));

        when(productsRepository.findById(buys.getProducts().get(0).getId())).thenReturn(productsMono);

        StepVerifier.create(saveBuysUseCase.validateProduct(productsList.get(0)))
                .expectNextMatches(res-> res.getId().equals("648a31c75d74aa1f7705cb88"))
                .expectComplete()
                .verify();

    }

    @Test
    void eliminateQuantities(){
        List<Products> productsList = List.of(  new Products("648a31c75d74aa1f7705cb88","Arroz",500,true,8,200 , 5));
        Buys buys = new Buys("648a31c75d7bb5aa8ws4dc4", LocalDateTime.now(), TypeOfDocument.CC,"1058788349","Diego Muñoz",productsList);
        Mono<Products> productsMono = Mono.just(productsList.get(0));

        when(productsRepository.findById(buys.getProducts().get(0).getId())).thenReturn(productsMono);
        when(productsRepository.save(productsList.get(0))).thenReturn(productsMono);

        StepVerifier.create(saveBuysUseCase.eliminateQuantities(productsList))
                .expectComplete()
                .verify();

    }

    @Test
    void applyTest() {
        List<Products> productsList = List.of(
                new Products("1", "Product 1", 100, true, 6, 200,7)
        );
        Buys buys = new Buys("123", LocalDateTime.now(), TypeOfDocument.CC, "456", "John Doe", productsList);

        ProductsRepository productsRepository = mock(ProductsRepository.class);
        BuysRepository buysRepository = mock(BuysRepository.class);

        Products updatedProduct = new Products("1", "Product 1", 94, true, 5, 200, 6);
        when(productsRepository.findById("1")).thenReturn(Mono.just(updatedProduct));
        when(productsRepository.save(updatedProduct)).thenReturn(Mono.just(updatedProduct));

        when(buysRepository.save(buys)).thenReturn(Mono.just(buys));

        SaveBuysUseCase saveBuysUseCase = new SaveBuysUseCase(productsRepository, buysRepository);

        Mono<Buys> result = saveBuysUseCase.apply(buys);
        StepVerifier.create(result)
                .expectNextMatches(res -> {
                    System.out.println(res);
                    return res.getId().equals("123");
                })
                .verifyComplete();
    }


}
