import com.cologique.scalabits.circle1.review.variance.covariant.{Employee,Manager, Award}

val mike = new Manager("5241", "SEN", "Sales")
val prol1= new Employee("1138", "THX")
val prol2 = new Employee("3417", "LUH")

val managerAward = new Award[Manager](mike)
val employeeAward1 = new Award[Employee] (prol1)
val employeeAward2 = new Award[Employee] (prol2)

val employeeAward: Award[Employee] = managerAward
val awards = List[Award[Employee]](managerAward)

/*
val a = Array(employeeAward1, employeeAward2, managerAward)

def processArray(array:Array[Award[Employee]]) = {
  //array(1) = employeeAward2
  array(0) = managerAward // It will work - putting an manager award into array of Apples!
}
processArray(a)
//*/

/*
// NOTE  sealed abstract class List[+A]
val a = List(employeeAward1, employeeAward2)
def processArray(array:List[Award[Employee]]) = {
  // IMMUTABLE SO IT IS SAFE
 }
processArray(a)
//*/

// Covariance allows for pulling stuff out "wider"
