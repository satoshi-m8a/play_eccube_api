package services

import java.io.File

import play.api.Play


trait ElasticsearchServerConfig {

  lazy val ELASTICSEARCH_DATA_DIR = Play.current.configuration.getString("elasticsearch.data.dir").get

  lazy val ELASTICSEARCH_PLUGIN_DIR = Play.current.configuration.getString("elasticsearch.plugin.dir").get

  lazy val dataDir = {
    val dir = new File(ELASTICSEARCH_DATA_DIR)

    if (!dir.exists()) {
      dir.mkdirs()
    }
    dir.getAbsolutePath
  }

  lazy val pluginDir = {
    val dir = new File(ELASTICSEARCH_PLUGIN_DIR)

    if (!dir.exists()) {
      dir.mkdirs()
    }
    dir.getAbsolutePath
  }


}
