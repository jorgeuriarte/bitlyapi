import io.vertx.groovy.ext.web.Router
import io.vertx.groovy.ext.web.handler.BodyHandler

def router = Router.router(vertx)
router.route().handler(BodyHandler.create())

// Añadir esta llamada en el web servrer principal de la app vertx
new bitlyapi.BitlyAPIHttpWrapper(vertx).configureRoutes(router)

vertx.createHttpServer().requestHandler(router.&accept).listen(8080)
