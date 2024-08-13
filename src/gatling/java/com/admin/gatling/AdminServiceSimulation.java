package com.admin.gatling;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class AdminServiceSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8089") // Change to your application's base URL
            .acceptHeader("application/json");

    ScenarioBuilder scn = scenario("Admin Service Performance Test")
            .exec(http("Get Employees using Rest Template")
                    .get("/client/rest")
                    .check(status().is(200)))
            .pause(1)
            .exec(http("Create Employee using Rest Template")
                    .post("/client/rest/employees")
                    .body(StringBody("{\"name\": \"Amar\", \"position\": \"Developer\", \"salary\": 50000}")).asJson()
                    .check(status().is(201)))
            .pause(1)
            .exec(http("Get Employee by ID using Rest Template")
                    .get("/client/rest/employees/1")
                    .check(status().is(200)))
            .exec(http("Get Employees using Feign Client")
                    .get("/client/feign")
                    .check(status().is(200)))
            .pause(1)
            .exec(http("Create Employee using Feign Client")
                    .post("/client/feign/employees")
                    .body(StringBody("{\"name\": \"Amar\", \"position\": \"Developer\", \"salary\": 50000}")).asJson()
                    .check(status().is(201)))
            .pause(1)
            .exec(http("Get Employee by ID using Feign Client")
                    .get("/client/feign/employees/1")
                    .check(status().is(200)));

    {
        setUp(
                scn.injectOpen(atOnceUsers(3)) // Adjust the number of users and injection profile as needed
        ).protocols(httpProtocol);
    }
}
