package br.sp.fdanilo.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	@BeforeClass
	public static void setup () {
		RestAssured.baseURI = ("http://localhost:8001/tasks-backend");
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured
			.given()
			.when()
				.get("/todo")
			.then()
				.statusCode(200);
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured
			.given()
				.body("    {\r\n"
						+ "	\"task\": \"Teste API\",\r\n"
						+ "	\"dueDate\": \"2024-12-30\"\r\n"
						+ "}")
				.contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.statusCode(201);
	}
	
	@Test
	public void deveAdicionarTarefaInvalida() {
		RestAssured
			.given()
				.body("    {\r\n"
						+ "	\"task\": \"Teste API\",\r\n"
						+ "	\"dueDate\": \"2020-12-30\"\r\n"
						+ "}")
				.contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.statusCode(400)
				.body("message", CoreMatchers.is("Due date must not be in past"));
	}
}