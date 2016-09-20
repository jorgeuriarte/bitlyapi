# bitlyapi
Demo de acceso a bitly desde un verticle.
Consiste en un verticle "bitlyApi.groovy", que es llamado desde otro verticle "webserver".

Ejecútalo con gradlew y ya te dice cómo usarlo.


	./gradlew run
	Starting vert.x application...
	351231f9-18e3-4cd4-aeef-4c5bc37d6c8e-redeploy

	DEMO DE ACCESO A BITLY:

	Para crear una URL, llama a:

		curl -X POST http://localhost:8080/shorturl/create/?url=http://gailen.es

	Para acceder a las estadísticas de una hash de biyly, llama así:

		curl http://localhost:8080/shorturl/2cDgIFK/stats



