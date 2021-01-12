/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hadoops.controller;

import java.io.IOException;
import java.util.Iterator;
 
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
 
public class ReduceClass extends Reducer{
 
    protected void reduce(Text key, Iterable values,
            Context context)
            throws IOException, InterruptedException {
     
        int sum = 0;
        Iterator valuesIt = values.iterator();
         
        //For each key value pair, get the value and adds to the sum
        //to get the total occurances of a word
        while(valuesIt.hasNext()){
            sum += (Integer) valuesIt.next();
        }
         
        //Writes the word and total occurances as key-value pair to the context
        context.write(key, new IntWritable(sum));
    }   
}