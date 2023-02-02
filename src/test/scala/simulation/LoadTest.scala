package simulation

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import simulation.Constants.{EXPERIENCE_SLUG, EXPERIENCE_SLUG_DESC, PUBLISHED_CITIES, PUBLISHED_CITIES_DESC}
import util.Constants._
import util.FileUtil.{loadGraphqlResource, loadGraphqlVariables}

import scala.concurrent.duration.{FiniteDuration, SECONDS}

class LoadTest extends Simulation {

  private val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(BASE_URL)
    .acceptHeader(JSON_CONTENT_TYPE)

  private val token: String = "Bearer " + JWT_TOKEN

  // Cities

  private val cityQuery = loadGraphqlResource("src/test/resources/graphql/query/publishedCitiesByAbbreviation.graphql")
  private val nyc_variable = loadGraphqlVariables("src/test/resources/graphql/query/parameters/publishedCitiesByAbbreviation_NYC.json")
  private val chi_variable = loadGraphqlVariables("src/test/resources/graphql/query/parameters/publishedCitiesByAbbreviation_CHI.json")
  private val sf_variable = loadGraphqlVariables("src/test/resources/graphql/query/parameters/publishedCitiesByAbbreviation_SF.json")
  private val la_variable = loadGraphqlVariables("src/test/resources/graphql/query/parameters/publishedCitiesByAbbreviation_LA.json")
  private val pok_variable = loadGraphqlVariables("src/test/resources/graphql/query/parameters/publishedCitiesByAbbreviation_POK.json")

  val nycScenario: ScenarioBuilder = scenario(PUBLISHED_CITIES + "NYC")
    .exec(
      http(PUBLISHED_CITIES_DESC + " (NYC)")
        .post(GRAPHQL_URL)
        .header(AUTHORIZATION_HEADER, token)
        .header(JSON_CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
        .body(StringBody("{\"query\":\"" + cityQuery + "\", \n\"variables\":" + nyc_variable + "}".stripMargin))
        .check(status.is(200))).exec { session =>
    println(session)
    session
  }

  val chiScenario: ScenarioBuilder = scenario(PUBLISHED_CITIES + "CHI")
    .exec(
      http(PUBLISHED_CITIES_DESC + " (CHI)")
        .post(GRAPHQL_URL)
        .header(AUTHORIZATION_HEADER, token)
        .header(JSON_CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
        .body(StringBody("{\"query\":\"" + cityQuery + "\", \n\"variables\":" + chi_variable + "}".stripMargin))
        .check(status.is(200))).exec { session =>
    println(session)
    session
  }

  val sfScenario: ScenarioBuilder = scenario(PUBLISHED_CITIES + "SF")
    .exec(
      http(PUBLISHED_CITIES_DESC + " (SF)")
        .post(GRAPHQL_URL)
        .header(AUTHORIZATION_HEADER, token)
        .header(JSON_CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
        .body(StringBody("{\"query\":\"" + cityQuery + "\", \n\"variables\":" + sf_variable + "}".stripMargin))
        .check(status.is(200))).exec { session =>
    println(session)
    session
  }

  val laScenario: ScenarioBuilder = scenario(PUBLISHED_CITIES + "LA")
    .exec(
      http(PUBLISHED_CITIES_DESC + " (LA)")
        .post(GRAPHQL_URL)
        .header(AUTHORIZATION_HEADER, token)
        .header(JSON_CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
        .body(StringBody("{\"query\":\"" + cityQuery + "\", \n\"variables\":" + la_variable + "}".stripMargin))
        .check(status.is(200))).exec { session =>
    println(session)
    session
  }

  val pokScenario: ScenarioBuilder = scenario(PUBLISHED_CITIES + "POK")
    .exec(
      http(PUBLISHED_CITIES_DESC + " (POK)")
        .post(GRAPHQL_URL)
        .header(AUTHORIZATION_HEADER, token)
        .header(JSON_CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
        .body(StringBody("{\"query\":\"" + cityQuery + "\", \n\"variables\":" + pok_variable + "}".stripMargin))
        .check(status.is(200))).exec { session =>
    println(session)
    session
  }

  // Experience

  private val experienceQuery = loadGraphqlResource("src/test/resources/graphql/query/experienceBySlug.graphql")
  private val laughFactoryVariable = loadGraphqlVariables("src/test/resources/graphql/query/parameters/experienceBySlugLaughFactory.json")

  val laughFactoryScenario: ScenarioBuilder = scenario(EXPERIENCE_SLUG + " Laugh Factory")
    .exec(
      http(EXPERIENCE_SLUG_DESC + " (Laugh Factory)")
        .post(GRAPHQL_URL)
        .header(AUTHORIZATION_HEADER, token)
        .header(JSON_CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
        .body(StringBody("{\"query\":\"" + experienceQuery + "\", \n\"variables\":" + laughFactoryVariable + "}".stripMargin))
        .check(status.is(200))).exec { session =>
    println(session)
    session
  }

  setUp(
    nycScenario.inject(
      constantUsersPerSec(2) during FiniteDuration(10, SECONDS)
    ),
    chiScenario.inject(
      constantUsersPerSec(2) during FiniteDuration(10, SECONDS)
    ),
    sfScenario.inject(
      constantUsersPerSec(2) during FiniteDuration(10, SECONDS)
    ),
    laScenario.inject(
      constantUsersPerSec(2) during FiniteDuration(10, SECONDS)
    ),
    pokScenario.inject(
      constantUsersPerSec(2) during FiniteDuration(10, SECONDS)
    ),
    laughFactoryScenario.inject(
      constantUsersPerSec(2) during FiniteDuration(10, SECONDS)
    )
  ).protocols(httpProtocol)

}
