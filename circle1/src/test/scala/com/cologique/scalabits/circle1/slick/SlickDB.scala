package com.cologique.scalabits.circle1.slick

import scala.slick.session.Database

object SlickDB {
  private val settings = SlickSettings.Settings
  val db = Database.forURL(settings.jdbcUrl, driver = settings.jdbcDriver)
  
  implicit def session = db.createSession
}