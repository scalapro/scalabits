// Taken from http://skipoleschris.blogspot.com/2011/06/invariance-covariance-and.html
//
package com.cologique.scalabits.circle1.review.variance.contravariant

class Employee(val number: String, val name: String)
class Manager(number: String, name: String, val department: String) extends Employee(number, name)

abstract class Award[+Recipient, AwardType](val recipient: Recipient) {val awardType:String}
class AttendanceAward[Recipient](recipient: Recipient) extends Award[Recipient, Employee](recipient) {val awardType = "Attendence Award"}
class TeamLeadershipAward[Recipient](recipient: Recipient) extends Award[Recipient, Manager](recipient) {val awardType = "Team Leadership Award"}

