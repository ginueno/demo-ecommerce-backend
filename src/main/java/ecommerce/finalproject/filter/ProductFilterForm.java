package ecommerce.finalproject.filter;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFilterForm {
    private String name;
    private String size;
    private String color;
    private String category;
    private String collection;
    private String lpage;
    private String sort;
}
