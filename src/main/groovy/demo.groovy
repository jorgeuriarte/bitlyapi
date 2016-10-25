// @GrabResolver(name='swisstech.net', root="http://stackmagic.github.io/maven-repo/maven2")
// @Grab('net.swisstech.bitly:bitly-api-client:0.7.2')
import net.swisstech.bitly.BitlyClient
import net.swisstech.bitly.model.Response
import net.swisstech.bitly.model.v3.LinkClicksExpanded

import org.scribe.oauth.OAuthService
import org.scribe.builder.ServiceBuilder
import org.scribe.builder.api.BitlyApi

import static groovy.json.JsonOutput.toJson
import static groovy.json.JsonOutput.prettyPrint
import groovy.json.JsonSlurper

/**
  Cuidado, esto es sólo para acceder a los tokens adecuados
**/
def printAuthorizationURL() {
	// 0- Registramos la app en bitly para obtener client_id y client_secret
	// 1-LLAMAMOS A ESTO PARA OBTENER LA URL
	// 2-LA ABRIMOS EN EL NAVEGADOR Y NOS QUEDAMOS EL 'code'
	// 3-Llamamos a un CURL como este:
	/**
		curl -k -X POST https://api-ssl.bitly.com/oauth/access_token -d "client_id=83eef4388f2738366e4d6f680eb0ff81660e6bcc&client_secret=bbe14a869beb3a300fd481f58823c87e51ca0df0&code=36b694ce4ce115f6bd3406f84d669a1593395826&redirect_uri=http://staging.kasitoko.com:7090/bitlyredirect/"


	**/

	OAuthService service = new ServiceBuilder() //
					.provider(BitlyApi.class) //
					.apiKey("83eef4388f2738366e4d6f680eb0ff81660e6bcc") //
					.apiSecret("bbe14a869beb3a300fd481f58823c87e51ca0df0") //
					.callback("http://staging.kasitoko.com:7090/bitlyredirect/") //
					.debug() //
					.build();
 	String authorizationUrl = service.getAuthorizationUrl(null);
 	println authorizationUrl
}

def demo() {
	
 	
    def token = "b771dcae4108c72f5027c183c1c6609be99123e7"
    println "TOKEN: ${token}"
	BitlyClient client = new BitlyClient(token)
	def respShort = client.shorten()
    	.setLongUrl("https://github.com/stackmagic/bitly-api-client") //
    	.call();
    println respShort
    println "-------"
    println client.linkClicksExpanded()
    	.setLink('http://bit.ly/2cjg3JI')
    	.setUnit("minute")
    	.setUnits(10)//
    	.call()
    println "-------"
    def report = client.userClicksExpanded() //
			.call();
	println prettyPrint(toJson(new JsonSlurper().parseText(report.toString())))
}

demo()
