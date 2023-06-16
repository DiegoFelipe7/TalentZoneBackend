package co.com.sofka.api.products;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductsRouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunctionProducts(ProductsHandler handler) {
        return route(GET("/api/products"), handler::listProduct)
                .andRoute(GET("/api/product/{id}") , handler::getProductId)
                .andRoute(GET("/api/products/pagination") , handler::productsPage)
                .andRoute(POST("/api/product"), handler::saveProduct)
                .andRoute(PUT("/api/product/{id}"), handler::editProduct)
                .and(route(DELETE("/api/product/remove/{id}"), handler::removeProduct));
    }
}
