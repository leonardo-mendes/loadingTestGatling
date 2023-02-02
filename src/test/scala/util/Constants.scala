package util

object Constants {

  val BASE_URL = PropertiesReader.getProperty("BASE_URL")
  val GRAPHQL_URL = "/graphql"
  val JSON_CONTENT_TYPE_HEADER = "Content-Type"
  val JSON_CONTENT_TYPE = "application/json"
  val AUTHORIZATION_HEADER = "Authorization"
  val JWT_TOKEN = PropertiesReader.getProperty("TOKEN")
  val SERVICE_JSON_BASE_PATH = "build/reports/resource"

}
