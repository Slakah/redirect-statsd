package controllers

import com.timgroup.statsd.NonBlockingStatsDClient
import play.api._
import play.api.mvc._

object Application extends Controller {
  val statsd = new NonBlockingStatsDClient("bbc.search", "192.168.59.103", 8125);


  def redirect = Action { implicit req =>
    val url = req.queryString("url")(0)
    statsd.recordSetEvent("url", url)
    statsd.count("click_through", 1)
    statsd.recordGaugeValue("rank_position", req.queryString("cd")(0).toLong)
    Found(url)
  }

}