package org.apache.mahout.clustering.syntheticcontrol.canopy;
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.mahout.matrix.DenseVector;
import org.apache.mahout.matrix.Vector;

public class InputMapper extends MapReduceBase implements
    Mapper<LongWritable, Text, Text, Text> {

  public void map(LongWritable key, Text values,
      OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
    String[] numbers = values.toString().split(" ");
    // sometimes there are multiple separator spaces
    List<Double> doubles = new ArrayList<Double>();
    for (int i = 0; i < numbers.length; i++) {
      String value = numbers[i];
      if (value.length() > 0)
        doubles.add(new Double(value));
    }
    Vector result = new DenseVector(doubles.size());
    int index = 0;
    for (Double d : doubles)
      result.set(index++, d);
    output.collect(null, new Text(result.asFormatString()));
  }

}