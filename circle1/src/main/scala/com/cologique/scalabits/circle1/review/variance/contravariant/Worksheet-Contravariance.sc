import com.cologique.scalabits.circle1.review.variance.contravariant._

val mike = new Manager("5241", "SEN", "Sales")
val prol1= new Employee("1138", "THX")
val prol2 = new Employee("3417", "LUH")


def presentManagementAward(award: Award[Manager, Manager]) = { println(award.awardType + ": a big bonus for "+ award.recipient.name) }
def presentEmployeeAward(award: Award[Employee, Employee]) = { println(award.awardType + ": a ziploc of mushroom cream soup for "+ award.recipient.name) }

presentEmployeeAward(new AttendanceAward(prol1))
presentManagementAward(new TeamLeadershipAward(mike))
presentManagementAward(new AttendanceAward(mike))

// Contravariance allows for putting stuff in "wider" but not getting it out which is not so useful except for functions
