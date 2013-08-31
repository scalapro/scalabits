package com.cologique.scalabits.circle1.slick

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

class SlickSettings(config: Config) {
  
  val Organization = "cologique"
  val Module = Organization + "." + "circle1-slick"
  val JdbcUrlKey = Module + "." + "jdbc-url"
  val JdbcDriverKey = Module + "." + "jdbc-driver"
  
  def this() = {
    this(ConfigFactory.load())
  }

  config.checkValid(ConfigFactory.defaultReference(), Module)

  // Get configurations at startup to fail fast in case of issues.
  
  val jdbcUrl = config.getString(JdbcUrlKey)
  val jdbcDriver = config.getString(JdbcDriverKey)
}

object SlickSettings {
  val Settings = new SlickSettings
}
