package co.com.sofka.model.buys;
import co.com.sofka.model.products.Products;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Buys {
    private String id;
    private LocalDateTime date = LocalDateTime.now();
    private TypeOfDocument idType;
    private String identification;
    private String clientName;
    private List<Products> products;
}
