package co.com.sofka.api.buys;

import co.com.sofka.model.buys.Buys;
import co.com.sofka.usecase.buys.savebuys.SaveBuysUseCase;
import co.com.sofka.usecase.buys.shoppinglist.ShoppingListUseCase;
import co.com.sofka.usecase.buys.usershoppinglist.UserShoppingListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BuysHandler {
    private final SaveBuysUseCase saveBuys;
    private final ShoppingListUseCase shoppingList;
    private final UserShoppingListUseCase userShoppingList;

    public Mono<ServerResponse> saveBuy(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Buys.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(saveBuys.apply(ele), Buys.class));
    }

    public Mono<ServerResponse> shoppingList(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(shoppingList.get() , Buys.class);
    }

    public Mono<ServerResponse> shoppingListUser(ServerRequest serverRequest) {
       String identification = serverRequest.pathVariable("identification");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userShoppingList.apply(identification) , Buys.class);
    }
}
