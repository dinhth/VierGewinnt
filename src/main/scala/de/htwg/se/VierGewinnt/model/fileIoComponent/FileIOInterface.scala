package de.htwg.se.VierGewinnt.model.fileIoComponent

import de.htwg.se.VierGewinnt.model.playgroundComponent.PlaygroundInterface

trait FileIOInterface {
  def load: PlaygroundInterface

  def save(playground: PlaygroundInterface): Unit
}
