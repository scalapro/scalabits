package com.cologique.scalabits.circle1.slick

import org.slf4j.LoggerFactory
import org.scalatest.BeforeAndAfter
import org.scalatest.WordSpec

class TypesafeConfigTest extends WordSpec with BeforeAndAfter {

  val logger = LoggerFactory.getLogger(this.getClass)
  
  "configuration" should {
    "include jdbc parameters" in {
      val settings = SlickSettings.Settings
      logger.info(settings.JdbcUrlKey + " = " + settings.jdbcUrl)
      logger.info(settings.JdbcDriverKey + " = " + settings.jdbcDriver)
    }
  }
}