package ecommerce.finalproject.service.Product;

import ecommerce.finalproject.entity.Collection;
import ecommerce.finalproject.request.CollectionRequest;

public interface CollectionService {
    public Collection createCollection(CollectionRequest request);
    public Collection updateCollection(Long collectionId, CollectionRequest request);
    public Collection isExisted(Long collectionId);
}
