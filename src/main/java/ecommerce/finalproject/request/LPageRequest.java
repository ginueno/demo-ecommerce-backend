package ecommerce.finalproject.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LPageRequest {
    private String name;
    private Long categoryId;
    private String bannerImg;
    private String bannerVideo;
}
