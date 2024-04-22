package ecommerce.finalproject.service.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Category;
import ecommerce.finalproject.entity.Collection;
import ecommerce.finalproject.repository.CollectionRepo;
import ecommerce.finalproject.request.CollectionRequest;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionRepo collectionRepo;

    @Autowired
    CategoryService categoryService;

    @Override
    public Collection createCollection(CollectionRequest request) {
        Category category = categoryService.isExisted(request.getCategoryId());
        if (category != null) {
            Collection collection = new Collection();
            collection.setName(request.getName());
            collection.setParentCategory(category);
            return collectionRepo.save(collection);
        }

        return null;
    }

    @Override
    public Collection updateCollection(Long collectionId, CollectionRequest request) {
        Category category = categoryService.isExisted(request.getCategoryId());
        Collection collection = isExisted(collectionId);
        if (collection != null && category != null) {
            collection.setName(request.getName());
            collection.setParentCategory(category);
            return collectionRepo.save(collection);
        }
        return null;
    }

    @Override
    public Collection isExisted(Long collectionId) {
        return collectionRepo.findById(collectionId).get();
    }

}
