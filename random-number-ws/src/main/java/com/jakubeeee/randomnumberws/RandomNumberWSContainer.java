package com.jakubeeee.randomnumberws;

import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor(staticName = "of")
@Value
class RandomNumberWSContainer {

    private final ImmutableSet<String> values;

}
