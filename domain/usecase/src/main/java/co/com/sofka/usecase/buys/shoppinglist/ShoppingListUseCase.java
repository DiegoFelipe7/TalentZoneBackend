package co.com.sofka.usecase.buys.shoppinglist;

import co.com.sofka.model.buys.Buys;
import co.com.sofka.model.buys.gateways.BuysRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class ShoppingListUseCase implements Supplier<Flux<Buys>> {
    private final BuysRepository buysRepository;
    @Override
    public Flux<Buys> get() {
        return buysRepository.findAll();
    }
}
