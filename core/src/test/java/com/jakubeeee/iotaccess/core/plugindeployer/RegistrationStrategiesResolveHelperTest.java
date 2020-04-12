package com.jakubeeee.iotaccess.core.plugindeployer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.EnumSet;
import java.util.Set;

import static com.jakubeeee.iotaccess.core.plugindeployer.RegistrationStrategiesResolveHelper.resolveRegistrationStrategiesFromArgs;
import static com.jakubeeee.iotaccess.core.plugindeployer.RegistrationStrategy.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class RegistrationStrategiesResolveHelperTest {

    @ParameterizedTest
    @MethodSource("resolveRegistrationStrategiesFromArgsTest_data")
    void resolveRegistrationStrategiesFromArgsTest(ApplicationArguments args,
                                                   Set<RegistrationStrategy> expectedResult) {
        var registrationStrategies = resolveRegistrationStrategiesFromArgs(args);
        assertThat(registrationStrategies, is(equalTo(expectedResult)));
    }

    private static Object[][] resolveRegistrationStrategiesFromArgsTest_data() {
        return new Object[][]{
                {args("--regstrat=some_invalid_value"), EnumSet.noneOf(RegistrationStrategy.class)},
                {args(""), EnumSet.allOf(RegistrationStrategy.class)},
                {args("--regstrat=spi"), EnumSet.of(SPI)},
                {args("--regstrat=db"), EnumSet.of(DATABASE)},
                {args("--regstrat=fs"), EnumSet.of(FILESYSTEM)},
                {args("--regstrat=spi", "--regstrat=db"), EnumSet.of(SPI, DATABASE)},
                {args("--regstrat=spi", "--regstrat=fs"), EnumSet.of(SPI, FILESYSTEM)},
                {args("--regstrat=db", "--regstrat=fs"), EnumSet.of(DATABASE, FILESYSTEM)},
                {args("--regstrat=spi", "--regstrat=db", "--regstrat=fs"), EnumSet.of(SPI, DATABASE, FILESYSTEM)}
        };
    }

    private static ApplicationArguments args(String... stringArgs) {
        return new DefaultApplicationArguments(stringArgs);
    }

}
