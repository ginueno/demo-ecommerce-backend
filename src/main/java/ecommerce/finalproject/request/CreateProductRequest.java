package ecommerce.finalproject.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductRequest {
    private String name;
    private String imgUrl;
    private int price;
    private int discountedPice;
    private String subTitle;
    private List<String> descriptions = new ArrayList<>();
    private List<String> descImgUrls = new ArrayList<>();
    private List<String> collections = new ArrayList<>();
    private List<String> lpages = new ArrayList<>();
}
