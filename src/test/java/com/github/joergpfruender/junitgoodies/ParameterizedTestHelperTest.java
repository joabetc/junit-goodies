package com.github.joergpfruender.junitgoodies;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
   Copyright 2019 Jörg Pfründer

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       <a href="http://www.apache.org/licenses/LICENSE-2.0">http://www.apache.org/licenses/LICENSE-2.0</a>

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 **/
class ParameterizedTestHelperTest {

  public static Stream<Arguments> additionSamples() {
    return Stream.of(Arguments.of(0, 2, 2), Arguments.of(1, 3, 4), Arguments.of(2, 7, 9));
  }

  private static Stream<String> emptyStrings1() {
    return Stream.of("   ", "");
  }

  public static Stream<Arguments> emptyStrings2() {
    return Stream.of(Arguments.of(""), Arguments.of("   "), Arguments.of("\n"));
  }

  public static Stream<Integer> simpleNumbers() {
    return Stream.of(2, 7, 9);
  }

  @ParameterizedTest
  @MethodSource("additionSamples")
  void testAddition(Integer i1, Integer i2, Integer i3) {
    int actual = i1 + i2;
    assertThat(actual).isEqualTo(i3.intValue());
  }

  @ParameterizedTest
  @MethodSource("emptyStrings1")
  void trim(String input) {
    assertThat(input.trim()).isEmpty();
  }

  public static Stream<Arguments> combinedParams1() {
    return ParameterizedTestHelper.cartesian(additionSamples(), emptyStrings1());
  }

  @ParameterizedTest
  @MethodSource("combinedParams1")
  void exampleCombination1(Integer i1, Integer i2, Integer i3, String emptyString) {
    int addition = i1 + i2;
    String actual = addition + emptyString.trim();
    assertThat(Integer.parseInt(actual)).isEqualTo(i3.intValue());
  }

  public static Stream<Arguments> combinedParams2() {
    return ParameterizedTestHelper.cartesian(emptyStrings2(), additionSamples());
  }

  @ParameterizedTest
  @MethodSource("combinedParams2")
  void exampleCombination2(String emptyString, Integer i1, Integer i2, Integer i3) {
    int addition = i1 + i2;
    String actual = addition + emptyString.trim();
    assertThat(Integer.parseInt(actual)).isEqualTo(i3.intValue());
  }

  public static Stream<Arguments> combinedParams3() {
    return ParameterizedTestHelper.cartesian(emptyStrings1(), simpleNumbers());
  }

  @ParameterizedTest
  @MethodSource("combinedParams3")
  void exampleCombination3(String emptyString, Integer simpleNumber) {
    String actual = simpleNumber + emptyString.trim();
    assertThat(Integer.parseInt(actual)).isEqualTo(simpleNumber.intValue());
  }
}