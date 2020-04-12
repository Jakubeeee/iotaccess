package com.jakubeeee.iotaccess.randomnumberws;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Service;

import java.util.stream.DoubleStream;

import static com.google.common.collect.ImmutableSet.toImmutableSet;

/**
 * Service bean for generation of random numbers.
 */
@Service
class RandomNumberService {

    RandomNumberWSContainer generateRandomNumberContainer(int quantity) {
        ImmutableSet<String> randomNumbers = DoubleStream
                .generate(this::generateRandomNumberBetween0and100)
                .limit(quantity)
                .mapToObj(String::valueOf)
                .collect(toImmutableSet());
        return RandomNumberWSContainer.of(randomNumbers);
    }

    private double generateRandomNumberBetween0and100() {
        return generateRandomNumber(0, 100);
    }

    @SuppressWarnings({"SameParameterValue"})
    private double generateRandomNumber(int lowerBound, int upperBound) {
        return (Math.random() + lowerBound) * (upperBound + 1);
    }

}
