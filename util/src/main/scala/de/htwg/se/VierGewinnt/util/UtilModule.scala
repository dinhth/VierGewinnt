package de.htwg.se.VierGewinnt.util

import com.google.inject.AbstractModule

class UtilModule extends AbstractModule {
  override def configure(): Unit =
    bind(classOf[ObserverRest])
}
