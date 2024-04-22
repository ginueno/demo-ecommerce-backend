package ecommerce.finalproject.service.Product;

import ecommerce.finalproject.entity.LPage;
import ecommerce.finalproject.request.LPageRequest;

public interface LPageService {
    public LPage createLPage(LPageRequest request);
    public LPage updateLPage(Long collectionId, LPageRequest request);
    public LPage isExisted(Long collectionId);
}
