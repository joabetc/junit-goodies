# junit-goodies
Some small helpers for JUnit 5:

* `ParameterizedTestHelper` - builds cartesian product of parameters for parameterized tests

## Usage
Example:
```java
public class YourClassTest {

    public static Stream<Arguments> additionSamples() {
        return Stream.of(
        Arguments.of(0, 2, 2),
        Arguments.of(1, 3, 4),
        Arguments.of(2, 7, 9));
    }
    
    private static Stream<String> emptyStrings() {
        return Stream.of("   ", "");
    }

    public static Stream<Arguments> combinedParams() {
        return ParameterizedTestHelper.cartesian(additionSamples(), emptyStrings());
    }

    @ParameterizedTest(name = "[{index}] parseInt({0} + {1} + \"{3}\") = {2}")
    @MethodSource("combinedParams1")
    void exampleCombination1(Integer i1, Integer i2, Integer i3, String emptyString) {
         int addition = i1 + i2;
         String actual = addition + emptyString.trim();
         assertThat(Integer.parseInt(actual)).isEqualTo(i3);
    }
}
```

For more examples on how to use it please refer to the `ParameterizedTestHelperTest` class