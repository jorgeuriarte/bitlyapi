import io.vertx.groovy.core.Vertx


vertx.deployVerticle("groovy:webserver.groovy")
vertx.deployVerticle("groovy:bitlyapi/bitlyVerticle.groovy")

println """

	DEMO DE ACCESO A BITLY:

	Para crear una URL, llama a:

		curl -X POST http://localhost:8080/shorturl/create/?url=http://gailen.es

	Para acceder a las estadísticas de una hash de biyly, llama así:

		curl http://localhost:8080/shorturl/2cDgIFK/stats

"""
