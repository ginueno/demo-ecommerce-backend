package ecommerce.finalproject.request;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateVariantForm {
    
    private Long product_id;

    private String size;

    private String color;

    private String colorCode;

    private int stock;

    private List<String> imgUrls = new ArrayList<>();
}
