package com.cologique.scalabits.circle1.slick

import scala.slick.driver.ExtendedProfile
import scala.util.Try

/**
 * The database version of an entity class.
 *
 * Must be consistent with the table structure - hence not as flexible as a candidate
 * for THE domain model object.
 *
 * Issue: But having a separate domain-level entity seems redundant.
 */
case class Supplier(id: Int, name: String, street: String, city: String, state: String, zip: String)

class SlickSupplierPersister(val driver: ExtendedProfile) {

  import driver.simple._

  object SupplierTable extends Table[Supplier]("supplier") {
    def id = column[Int]("id", O.DBType("long"), O.NotNull, O.PrimaryKey)
    def name = column[String]("name", O.DBType("varchar(50)"), O.NotNull)
    def street = column[String]("street", O.DBType("varchar(50)"), O.NotNull)
    def city = column[String]("city", O.DBType("varchar(30)"), O.NotNull)
    def state = column[String]("state", O.DBType("varchar(20)"), O.NotNull)
    def zip = column[String]("zip", O.DBType("varchar(10)"), O.NotNull)

    // Required magic to be grokked.
    def * = id ~ name ~ street ~ city ~ state ~ zip <> (Supplier, Supplier.unapply _)

  }
  
  /**
   * The supplier data access object.
   *
   * For simplicity just using an object. Not worrying about multiple possible implementations.
   * Q: The pattern used in Slick multi-db example is to have the wrapper be the dao.
   * Is that better? One less object?
   * Q: How about combining SupplierTable and SupplierDao into a single object?
   * Note: Using an implicit session parameter in all DAO calls - makes it clear what they depend on.
   * But more verbose than using threadLocalSession.
   */
  object SupplierDao {

    def drop(implicit session: Session) = {
      Try(SupplierTable.ddl.drop)
    }

    def create(implicit session: Session) = {
      SupplierTable.ddl.create
    }

    def recreate(implicit session: Session) = {
      drop
      create
    }

    /**
     * Parameterized query example.
     */
    private val nameByIDQuery = for {
      id <- Parameters[Int]
      s <- SupplierTable if s.id === id
    } yield s.name

    /**
     * Get all suppliers.
     */
    def all(implicit session: Session) = {
      Query(SupplierTable).list
    }

    /**
     * Get the name of a supplier.
     */
    def nameByID(id: Int)(implicit session: Session) = {
      nameByIDQuery(id).firstOption
    }

    def add(supplier: Supplier)(implicit session: Session) = {
      SupplierTable.insert(supplier)
    }

    /**
     * Update the name of a supplier.
     */
    def updateName(id: Int, name: String)(implicit session: Session) = {
      // Cannot update via parameterized query (it is a feature request).
      val target = for { s <- SupplierTable if s.id === id } yield s.name
      target.update(name)
    }
  }
}

object SlickSupplierPersister {
  
  import scala.slick.driver.H2Driver

  // TODO. Instantiate the Slick driver dynamically?
  
  private val settings = SlickSettings.Settings
  val jdbcDriver = settings.jdbcDriver
  val slickDriver = jdbcDriver match {
    case "org.h2.driver" => H2Driver
    case _ => H2Driver
  }
  
  val supplierDao = new SlickSupplierPersister(slickDriver).SupplierDao
}
