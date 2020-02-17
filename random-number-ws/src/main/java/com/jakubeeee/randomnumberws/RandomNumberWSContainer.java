package com.jakubeeee.randomnumberws;

import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Internal container used for storing random numbers within this module.
 */
@RequiredArgsConstructor(staticName = "of")
@Value
class RandomNumberWSContainer {

    private final ImmutableSet<String> values;

}
