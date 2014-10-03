package services

import org.elasticsearch.action.search.SearchType
import org.elasticsearch.client.Requests
import org.elasticsearch.common.settings.ImmutableSettings._
import org.elasticsearch.env.Environment
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.node.NodeBuilder._
import play.api.Logger

import scala.util.parsing.json.{JSONArray, JSONObject}

trait ElasticsearchServer {
  this: ElasticsearchServerConfig =>

  lazy val settings = settingsBuilder
    .put("path.data", dataDir)
    .put("path.plugins", pluginDir)
    .put("index.number_of_replicas", 0)
    .put("index.number_of_shards", 3)

  lazy val node = {
    val ev = new Environment(settings.build())
    Logger.info(ev.pluginsFile().getPath)
    Logger.info(ev.logsFile().getPath)
    nodeBuilder.local(true).settings(settings.build).node
  }

  lazy val client = node.client

  def shutdown() {
    node.close()
  }

  def setUpRiver() = {
    val request = Requests.indexRequest("_river").`type`("products_river").id("_meta").source(settingsJson.toString())
    client.index(request).actionGet()
  }

  lazy val settingsJson = {
    JSONObject(Map(
      "type" -> "jdbc",
      "jdbc" -> JSONObject(Map(
        "url" -> "jdbc:postgresql://localhost:5432/eccube_db",
        "user" -> "eccube_db_user",
        "password" -> "eccube_db_password",
        "sql" ->
          """
            |SELECT
            |  dtb_products.product_id as _id
            |  ,dtb_products.product_id as product_id
            |  ,dtb_products.name as name
            |  ,dtb_products.maker_id
            |  ,dtb_products.status
            |  ,dtb_products.comment1
            |  ,dtb_products.comment2
            |  ,dtb_products.comment3
            |  ,dtb_products.comment4
            |  ,dtb_products.comment5
            |  ,dtb_products.comment6
            |  ,dtb_products.note
            |  ,dtb_products.main_list_comment
            |  ,dtb_products.main_list_image
            |  ,dtb_products.main_comment
            |  ,dtb_products.main_image
            |  ,dtb_products.main_large_image
            |  ,dtb_products.sub_title1
            |  ,dtb_products.sub_comment1
            |  ,dtb_products.sub_image1
            |  ,dtb_products.sub_large_image1
            |  ,dtb_products.sub_title2
            |  ,dtb_products.sub_comment2
            |  ,dtb_products.sub_image2
            |  ,dtb_products.sub_large_image2
            |  ,dtb_products.sub_title3
            |  ,dtb_products.sub_comment3
            |  ,dtb_products.sub_image3
            |  ,dtb_products.sub_large_image3
            |  ,dtb_products.sub_title4
            |  ,dtb_products.sub_comment4
            |  ,dtb_products.sub_image4
            |  ,dtb_products.sub_large_image4
            |  ,dtb_products.sub_title5
            |  ,dtb_products.sub_comment5
            |  ,dtb_products.sub_image5
            |  ,dtb_products.sub_large_image5
            |  ,dtb_products.sub_title6
            |  ,dtb_products.sub_comment6
            |  ,dtb_products.sub_image6
            |  ,dtb_products.sub_large_image6
            |  ,dtb_products.del_flg
            |  ,dtb_products.creator_id
            |  ,dtb_products.create_date
            |  ,dtb_products.update_date
            |  ,dtb_products.deliv_date_id
            |  ,T4.product_code_min
            |  ,T4.product_code_max
            |  ,T4.price01_min
            |  ,T4.price01_max
            |  ,T4.price02_min
            |  ,T4.price02_max
            |  ,T4.stock_min
            |  ,T4.stock_max
            |  ,T4.stock_unlimited_min
            |  ,T4.stock_unlimited_max
            |  ,T4.point_rate
            |  ,T4.deliv_fee
            |  ,T4.class_count
            |  ,dtb_maker.name AS maker_name
            |FROM dtb_products
            |  JOIN (
            |         SELECT product_id,
            |           MIN(product_code) AS product_code_min,
            |           MAX(product_code) AS product_code_max,
            |           MIN(price01) AS price01_min,
            |           MAX(price01) AS price01_max,
            |           MIN(price02) AS price02_min,
            |           MAX(price02) AS price02_max,
            |           MIN(stock) AS stock_min,
            |           MAX(stock) AS stock_max,
            |           MIN(stock_unlimited) AS stock_unlimited_min,
            |           MAX(stock_unlimited) AS stock_unlimited_max,
            |           MAX(point_rate) AS point_rate,
            |           MAX(deliv_fee) AS deliv_fee,
            |           COUNT(*) as class_count
            |         FROM dtb_products_class
            |         GROUP BY product_id
            |       ) AS T4
            |    ON dtb_products.product_id = T4.product_id
            |  LEFT JOIN dtb_maker
            |    ON dtb_products.maker_id = dtb_maker.maker_id
          """.stripMargin,
        "index" -> "products",
        "schedule" -> "*/30 * * * * ?", //TODO
        "index_settings" -> JSONObject(Map(
          "analysis" -> JSONObject(
            Map(
              "filter" -> JSONObject(Map(
                "katakana_stemmer" -> JSONObject(Map(
                  "type" -> "kuromoji_stemmer",
                  "minimum_length" -> "4"
                ))
              )),
              "tokenizer" -> JSONObject(Map(
                "ja_tokenizer" -> JSONObject(Map(
                  "type" -> "kuromoji_tokenizer",
                  "mode" -> "search"
                ))
              )),
              "analyzer" -> JSONObject(Map(
                "default" -> JSONObject(Map(
                  "type" -> "custom",
                  "tokenizer" -> "ja_tokenizer",
                  "char_filter" -> JSONArray(List("html_strip", "kuromoji_iteration_mark")),
                  "filter" -> JSONArray(List("lowercase", "cjk_width", "kuromoji_baseform", "katakana_stemmer", "kuromoji_part_of_speech"))
                ))
              ))
            )
          )
        ))
      ))
    ))
  }

  def search(query: String) = {
    val res = client.prepareSearch("products")
      .setQuery( QueryBuilders.fuzzyLikeThisQuery("name","comment3","main_comment","main_list_comment").likeText(query).analyzer("default"))
      .execute().actionGet()

    println(res)

    res
  }
}


object ElasticsearchServer extends ElasticsearchServer with ElasticsearchServerConfig