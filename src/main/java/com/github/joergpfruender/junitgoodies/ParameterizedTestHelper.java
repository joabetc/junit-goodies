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

public class ParameterizedTestHelper {

  private ParameterizedTestHelper() { }

  /**
   * creates the cartesian product of two argument streams
   * <p>
   * look at ParameterizedTestHelperTest for usage examples
   *
   * @param a stream of JUnit5 arguments
   * @param b stream of JUnit5 arguments
   * @return the cartesian product where each tuple of arguments of stream a is multiplied by the tuple of arguments of stream b
   */
  public static <T, O> Stream<Arguments> cartesian(Stream<T> a, Stream<O> b) {
    List<T> argumentsA = a.collect(Collectors.toList());
    List<O> argumentsB = b.collect(Collectors.toList());

    List<Arguments> result = cartesian(argumentsA, argumentsB);

    return result.stream();
  }

  static <T, O> List<Arguments> cartesian(List<T> argumentsA, List<O> argumentsB) {
    List<Arguments> result = new ArrayList<>();
    for (Object o : argumentsA) {
      Object[] objects = asArray(o);
      for (Object o1 : argumentsB) {
        Object[] objects1 = asArray(o1);

        Object[] arguments = ArrayUtils.addAll(objects, objects1);
        result.add(Arguments.of(arguments));
      }
    }
    return result;
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
