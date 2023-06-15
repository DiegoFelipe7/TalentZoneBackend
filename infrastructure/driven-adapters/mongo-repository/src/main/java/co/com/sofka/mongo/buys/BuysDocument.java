package co.com.sofka.mongo.buys;

import co.com.sofka.model.buys.TypeOfDocument;
import co.com.sofka.model.products.Products;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(value = "buys")
@ToString
public class BuysDocument {
    @Id
    private String id;
    private LocalDateTime date;
    private TypeOfDocument idType;
    private String identification;
    private String clientName;
    private List<Products> products;
}
