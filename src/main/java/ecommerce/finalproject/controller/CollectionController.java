package ecommerce.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.finalproject.entity.Collection;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.request.CollectionRequest;
import ecommerce.finalproject.service.Product.CollectionService;
import ecommerce.finalproject.service.User.UserService2;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    UserService2 userService;

    @Autowired
    CollectionService collectionService;

    @PostMapping("/create")
    public ResponseEntity<Collection> createCollectionHandler(@RequestBody CollectionRequest request,
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findProfileByJwt(jwt);
        Collection collection = collectionService.createCollection(request);
        return new ResponseEntity<Collection>(collection, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{collectionId}")
    public ResponseEntity<Collection> updateCollectionHandler(@RequestParam Long collectionId,
            @RequestBody CollectionRequest request, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findProfileByJwt(jwt);
        Collection collection = collectionService.updateCollection(collectionId, request);
        return new ResponseEntity<Collection>(collection, HttpStatus.ACCEPTED);
    }

}
