/**
 * Copied from Tag Annotation Example at http://www.artima.com/docs-scalatest-2.0.M5b/index.html#org.scalatest.Tag
 */
package com.cologique.scalabits.circle1.scalaTest.tags;

import java.lang.annotation.*;
import org.scalatest.TagAnnotation;

@TagAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LifeTest {}