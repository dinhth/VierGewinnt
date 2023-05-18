package de.htwg.se.VierGewinnt.persist.fileio

import com.google.inject.AbstractModule
import de.htwg.se.VierGewinnt.persist.fileio.fileIoJsonImpl.FileIO as FileIoXml
import de.htwg.se.VierGewinnt.persist.fileio.fileIoJsonImpl.FileIO as FileIoJson

class PersistenceModule extends AbstractModule{
  override def configure(): Unit =
    bind(classOf[FileIOInterface]).to(classOf[FileIoJson])
    bind(classOf[FileIOInterface]).to(classOf[FileIoXml])
}
