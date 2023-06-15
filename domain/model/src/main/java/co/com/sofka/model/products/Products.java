package co.com.sofka.model.products;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Products {
   private String id;
   private String name;
   private Integer inInventory;
   private Boolean enabled;
   private Integer min;
   private Integer max;
   private Integer quantity;

   public Products(String id, String name, Integer inInventory, Boolean enabled, Integer min, Integer max) {
      this.id = id;
      this.name = name;
      this.inInventory = inInventory;
      this.enabled = enabled;
      this.min = min;
      this.max = max;
   }
}
