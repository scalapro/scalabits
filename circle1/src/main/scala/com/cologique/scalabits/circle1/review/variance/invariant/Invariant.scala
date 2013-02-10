// Taken from http://skipoleschris.blogspot.com/2011/06/invariance-covariance-and.html
//
package com.cologique.scalabits.circle1.review.variance.invariant

class Employee(val number: String, val name: String)
class Manager(number: String, name: String, val department: String) extends Employee(number, name)

class Award[T](val recipient: T)
