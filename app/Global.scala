import play.api.{Logger, Application, GlobalSettings}
import services.ElasticsearchServer

object Global extends GlobalSettings {

  override def beforeStart(app: Application) {
    Logger.info("starting elasticserarch server")
    ElasticsearchServer.setUpRiver()
    Logger.info("set up river")
  }

  override def onStop(app: Application) {
    Logger.info("shutting down elasticserarch server")
    ElasticsearchServer.shutdown()
  }

}