package ecommerce.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.finalproject.entity.Category;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.request.CreateCategoryRequest;
import ecommerce.finalproject.service.Product.CategoryService;
import ecommerce.finalproject.service.User.UserService2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    UserService2 userService;

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<Category> createCategoryHandler(@RequestBody CreateCategoryRequest request,
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findProfileByJwt(jwt);
        Category category = categoryService.createCategory(request);
        return new ResponseEntity<Category>(category, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategoryHandler(@PathVariable Long id,
            @RequestBody CreateCategoryRequest request, @RequestHeader("Authorization") String jwt)
            throws UserException {
        User user = userService.findProfileByJwt(jwt);
        Category category = categoryService.updateCategory(id, request);
        return new ResponseEntity<Category>(category, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryHandler(@PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findProfileByJwt(jwt);
        Category category = categoryService.isExisted(id);
        return new ResponseEntity<Category>(category, HttpStatus.ACCEPTED);
    }

}
