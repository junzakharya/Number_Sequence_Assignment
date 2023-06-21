package com.example.Yebelo_Assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberController {

    @Autowired
    NumberService numberService;

    @PostMapping("/fetch-next-number")
    public ResponseEntity<NumberResponse> fetchNextNumber(@RequestBody CategoryRequest categoryRequest) {
        NumberResponse numberResponse = numberService.fetchNextNumber(categoryRequest);
        return new ResponseEntity<>(numberResponse, HttpStatus.OK);
    }
}

