package ecommerce.finalproject.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemToCartRequest {
    
    private Long variantId;
    private int quantity;
    private int price;
    private int discountedPrice;
}
