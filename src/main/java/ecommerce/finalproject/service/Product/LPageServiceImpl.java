package ecommerce.finalproject.service.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.entity.Category;
import ecommerce.finalproject.entity.LPage;
import ecommerce.finalproject.repository.LPageRepo;
import ecommerce.finalproject.request.LPageRequest;

@Service
public class LPageServiceImpl implements LPageService {

    @Autowired
    LPageRepo lPageRepo;

    @Autowired
    CategoryService categoryService;

    @Override
    public LPage createLPage(LPageRequest request) {
        Category category = categoryService.isExisted(request.getCategoryId());
        if (category != null) {
            LPage lPage = new LPage();
            lPage.setName(request.getName());
            lPage.setParentCategory(category);
            if(request.getBannerImg() != null) lPage.setBannerImg(request.getBannerImg());
            if(request.getBannerVideo() != null) lPage.setBannerVideo(request.getBannerVideo());
            return lPageRepo.save(lPage);
        }

        return null;
    }

    @Override
    public LPage updateLPage(Long lPageId, LPageRequest request) {
        Category category = categoryService.isExisted(request.getCategoryId());
        LPage lPage = isExisted(lPageId);
        if (lPage != null && category != null) {
            lPage.setName(request.getName());
            lPage.setParentCategory(category);
            return lPageRepo.save(lPage);
        }
        return null;
    }

    @Override
    public LPage isExisted(Long lPageId) {
        return lPageRepo.findById(lPageId).get();
    }

}
