package bitlyapi

import io.vertx.groovy.ext.web.Router
import static groovy.json.JsonOutput.toJson
import static groovy.json.JsonOutput.prettyPrint

class BitlyAPIHttpWrapper {
	def vertx

	BitlyAPIHttpWrapper(vertx) {
		this.vertx = vertx
	}

	void configureRoutes(Router router) {
		router.post("/shorturl/create").handler { routingContext ->
			def longUrl = routingContext.request().getParam("url")
			if (longUrl)
				vertx.eventBus().send('bitly.createlink', [url:longUrl], { res ->
					def answer = res.result().body()
					routingContext.response().end("""
						Long:  ${answer.longUrl}
						Short: ${answer.shortUrl}
					""")
					})
			else
				routingContext.response().end("No data")
		}
		router.get("/shorturl/:hash/stats").handler { routingContext ->
			def hash = routingContext.request().getParam("hash")
			if (hash)
				vertx.eventBus().send('bitly.linkStats', [hash:hash], { res ->
					def answer = res.result().body()
					routingContext.response().end(prettyPrint(answer))
				})
			else
				routingContext.response().end()
		}
	}
}
