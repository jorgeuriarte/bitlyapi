// @GrabResolver(name='swisstech.net', root="http://stackmagic.github.io/maven-repo/maven2")
// @Grab('net.swisstech.bitly:bitly-api-client:0.7.2')
import net.swisstech.bitly.BitlyClient
import net.swisstech.bitly.model.Response
import net.swisstech.bitly.model.v3.LinkClicksExpanded

import static groovy.json.JsonOutput.toJson
import static groovy.json.JsonOutput.prettyPrint
import groovy.json.JsonSlurper

@groovy.transform.Field
def token = "b771dcae4108c72f5027c183c1c6609be99123e7"

String createShortUrl(url) {
	BitlyClient client = new BitlyClient(token)
	client.shorten()
    	.setLongUrl(url) //
    	.call()?.data.url
}

def linkStats(hash) {
	BitlyClient client = new BitlyClient(token)
    def report = client.linkClicksExpanded() //
    		.setLink("http://bit.ly/${hash}")
    		.setUnits(-1)
			.call();
	toJson(new JsonSlurper().parseText(report.toString()))
}

vertx.eventBus().consumer("bitly.createlink") { message ->
	def targetUrl = message.body().url
	vertx.executeBlocking({ future ->
		future.complete(createShortUrl(targetUrl))
	}, { res ->
		message.reply([longUrl: targetUrl, shortUrl:res.result()])
	})
}

vertx.eventBus().consumer("bitly.linkStats") { message ->
	def shortUrl = "http://bit.ly/${message.body().hash}"
	vertx.executeBlocking({ future ->
		future.complete(linkStats())
	}, { res ->
		message.reply(res.result())
	})
}
