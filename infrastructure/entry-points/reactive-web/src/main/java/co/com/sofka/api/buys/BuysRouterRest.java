package co.com.sofka.api.buys;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BuysRouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunctionBuys(BuysHandler handler) {
        return route(GET("/api/buys"), handler::shoppingList)
                .andRoute(POST("/api/buys"), handler::saveBuy)
                .and(route(GET("/api/buys/{identification}"), handler::shoppingListUser));
    }
}
