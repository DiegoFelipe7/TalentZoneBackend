package co.com.sofka.api.products;

import co.com.sofka.model.products.Products;
import co.com.sofka.usecase.products.editproduct.EditProductUseCase;
import co.com.sofka.usecase.products.getproduct.GetProductUseCase;
import co.com.sofka.usecase.products.getproductid.GetProductIdUseCase;
import co.com.sofka.usecase.products.removeproduct.RemoveProductUseCase;
import co.com.sofka.usecase.products.saveproduct.SaveProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductsHandler {
    private final GetProductUseCase getProduct;
    private final GetProductIdUseCase getProductId;
    private final SaveProductUseCase saveProduct;
    private final EditProductUseCase editProduct;

    private final RemoveProductUseCase removeProduct;

    public Mono<ServerResponse> listProduct(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getProduct.get(), Products.class);
    }
    public Mono<ServerResponse> getProductId(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getProductId.apply(id), Products.class);
    }

    public Mono<ServerResponse> saveProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Products.class)
                .flatMap(ele->ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(saveProduct.apply(ele) , Products.class));
    }
    public Mono<ServerResponse> editProduct(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return serverRequest.bodyToMono(Products.class)
                .flatMap(ele->ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(editProduct.apply(id,ele) , Products.class));
    }

    public Mono<ServerResponse> removeProduct(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(removeProduct.apply(id) , Products.class);
    }
}
