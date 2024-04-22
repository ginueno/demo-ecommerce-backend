package ecommerce.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.finalproject.entity.LPage;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.request.LPageRequest;
import ecommerce.finalproject.service.Product.LPageService;
import ecommerce.finalproject.service.User.UserService2;

@RestController
@RequestMapping("/api/lpages")
public class LPageController {
    @Autowired
    UserService2 userService;

    @Autowired
    LPageService lPageService;

    @PostMapping("/create")
    public ResponseEntity<LPage> createLPageHandler(@RequestBody LPageRequest request,
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findProfileByJwt(jwt);
        LPage lPage = lPageService.createLPage(request);
        return new ResponseEntity<LPage>(lPage, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{lpageId}")
    public ResponseEntity<LPage> updateLPageHandler(@RequestParam Long lpageId,
            @RequestBody LPageRequest request, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findProfileByJwt(jwt);
        LPage lPage = lPageService.updateLPage(lpageId, request);
        return new ResponseEntity<LPage>(lPage, HttpStatus.ACCEPTED);
    }

}
