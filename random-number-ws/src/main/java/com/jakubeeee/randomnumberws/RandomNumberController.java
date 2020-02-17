package com.jakubeeee.randomnumberws;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.jakubeeee.randomnumberws.RandomNumberRestAPIConstants.GET_MULTIPLE_RANDOM_NUMBERS_ENDPOINT;
import static com.jakubeeee.randomnumberws.RandomNumberRestAPIConstants.GET_RANDOM_NUMBER_ENDPOINT;

/**
 * Controller publishing the random number REST API.
 */
@RequiredArgsConstructor
@RestController
public class RandomNumberController {

    private final RandomNumberService randomNumberService;

    @GetMapping(GET_RANDOM_NUMBER_ENDPOINT)
    public RandomNumberWSContainer getRandomNumber() {
        return randomNumberService.generateRandomNumberContainer(1);
    }

    @GetMapping(GET_MULTIPLE_RANDOM_NUMBERS_ENDPOINT)
    public RandomNumberWSContainer getMultipleRandomNumbers(@PathVariable("quantity") int quantity) {
        return randomNumberService.generateRandomNumberContainer(quantity);
    }

}
