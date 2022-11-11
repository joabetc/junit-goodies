/*
   Copyright 2019 Jörg Pfründer

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package com.github.joergpfruender.junitgoodies;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ParameterizedTestHelper {

  private ParameterizedTestHelper() { }

  /**
   * Creates the cartesian product of two argument streams.
   * <p>
   *     Example:
   *     <pre>
   *         public static Stream&#60;Arguments&#62; additionSamples() {
   *            return Stream.of(
   *               Arguments.of(0, 2, 2),
   *               Arguments.of(1, 3, 4),
   *               Arguments.of(2, 7, 9));
   *         }
   *
   *         private static Stream&#60;String&#62; emptyStrings() {
   *            return Stream.of("   ", "");
   *         }
   *
   *         public static Stream&#60;Arguments&#62; combinedParams() {
   *            return ParameterizedTestHelper.cartesian(additionSamples(), emptyStrings());
   *         }
   *
   *         &#64;ParameterizedTest(name = "[{index}] parseInt({0} + {1} + \"{3}\") = {2}")
   *         &#64;MethodSource("combinedParams1")
   *         void exampleCombination1(Integer i1, Integer i2, Integer i3, String emptyString) {
   *            int addition = i1 + i2;
   *            String actual = addition + emptyString.trim();
   *            assertThat(Integer.parseInt(actual)).isEqualTo(i3.intValue());
   *         }
   *     </pre>
   *
   * @param streamA {@link Stream} of {@link Arguments}
   * @param streamB {@link Stream} of {@link Arguments}
   * @return The cartesian product where each tuple of arguments of streamA is multiplied by the tuple of arguments of streamB
   */
  public static <T, O> Stream<Arguments> cartesian(Stream<T> streamA, Stream<O> streamB) {
    List<T> argumentsA = streamA.collect(Collectors.toList());
    List<O> argumentsB = streamB.collect(Collectors.toList());

    return cartesian(argumentsA, argumentsB);
  }

  static <T, O> Stream<Arguments> cartesian(List<T> argumentsA, List<O> argumentsB) {
    List<Arguments> result = new ArrayList<>();
    for (Object o : argumentsA) {
      Object[] objects = asArray(o);
      for (Object o1 : argumentsB) {
        Object[] objects1 = asArray(o1);

        Object[] arguments = ArrayUtils.addAll(objects, objects1);
        result.add(Arguments.of(arguments));
      }
    }

    return result.stream();
  }

  private static Object[] asArray(Object o) {
    Object[] objects;
    if (o instanceof Arguments) {
      objects = ((Arguments) o).get();
    } else {
      objects = new Object[]{o};
    }

    return objects;
  }
}
