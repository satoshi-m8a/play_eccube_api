import play.api.{Play, Logger, Application, GlobalSettings}
import services.ElasticsearchServer

import play.api.Play.current

object Global extends GlobalSettings {

  override def beforeStart(app: Application) {

    if (!Play.isTest) {
      Logger.info("starting elasticserarch server")
      ElasticsearchServer.setUpRiver()
      Logger.info("set up river")
    }

  }

  override def onStop(app: Application) {
    if (!Play.isTest) {
      Logger.info("shutting down elasticserarch server")
      ElasticsearchServer.shutdown()
    }
  }

}