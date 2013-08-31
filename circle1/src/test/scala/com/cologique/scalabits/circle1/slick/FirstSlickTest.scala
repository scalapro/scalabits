package com.cologique.scalabits.circle1.slick

import org.scalatest.BeforeAndAfter
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers.convertToAnyRefShouldWrapper
import org.scalatest.matchers.ShouldMatchers.not
import org.slf4j.LoggerFactory

import com.cologique.scalabits.circle1.slick.SlickDB.db
import com.cologique.scalabits.circle1.slick.SlickDB.session

class FirstSlickTest extends WordSpec with BeforeAndAfter {
  
  val logger = LoggerFactory.getLogger(this.getClass)
  val supplierDao = SlickSupplierPersister.supplierDao

  before {
    logger.info("before")
    db withSession supplierDao.recreate
  }

  "supplier" should {
    "be inserted and updated correctly" in {
      val supplier1 = Supplier(1, "Acme", "5300 College Ave", "Oakland", "CA", "94618")
      db withTransaction {
        supplierDao.add(supplier1)
        
        val suppliers = supplierDao.all
        suppliers foreach { s => println(s) }
        
        val name = supplierDao.nameByID(supplier1.id)
        name should not be ('empty)
        expect(supplier1.name) { name.get }
        println(name.get)
        
        val newName = "Acme Enterprises"
        
        (supplierDao.updateName)(supplier1.id, newName)
        val updatedName = supplierDao.nameByID(supplier1.id)
        
        expect(newName) { updatedName.get }
        println(updatedName.get)
      }
    }
  }
}

