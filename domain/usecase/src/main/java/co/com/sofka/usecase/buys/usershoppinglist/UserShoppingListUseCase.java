package co.com.sofka.usecase.buys.usershoppinglist;

import co.com.sofka.model.buys.Buys;
import co.com.sofka.model.buys.gateways.BuysRepository;
import co.com.sofka.model.ex.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class UserShoppingListUseCase implements Function<String , Flux<Buys>> {
    private final BuysRepository buysRepository;
    @Override
    public Flux<Buys> apply(String id) {
        return buysRepository.findAll()
                .filter(ele->ele.getIdentification().contains(id))
                .switchIfEmpty(Mono.error(new BusinessException(BusinessException.Type.NO_HAY_COMPRAS_POR_EL_USUARIO)));
    }
}
