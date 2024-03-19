package co.edu.uniquindio.ingesis.seguridad.test.steps;

import co.edu.uniquindio.ingesis.seguridad.test.dtos.Error;
import co.edu.uniquindio.ingesis.seguridad.test.dtos.User;
import co.edu.uniquindio.ingesis.seguridad.test.utils.DataGenerator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterStepdefs {
    private static final String BASE_URL = "http://localhost:8080/users";
    private User.UserBuilder userBuilder;
    private User user;
    private Response response;


    @io.cucumber.java.en.Given("Soy un usuario no registrado")
    public void soyUnUsuarioNoRegistrado() {
        userBuilder = User.builder().username(DataGenerator.INSTANCE.username());
        // TODO En el caso de tener acceso a la BD se debe garantizar que el usuario a usar no este registrado
    }

    @And("proporciono los datos de registro")
    public void proporcionoLosDatosDeRegistro() {
        userBuilder.password(DataGenerator.INSTANCE.password());
    }


    @When("invoco el servicio de registro")
    public void invocoElServicioDeRegistro() {
        user = userBuilder.build();
        baseURI = BASE_URL;
        response = given().contentType(ContentType.JSON).body(user).when().post();
    }

    @Then("obtengo un status code {int}")
    public void obtengoUnStatusCode(int status) {
        response.then().statusCode(status);
    }

    @And("mis datos registrados")
    public void misDatosRegistrados() {
        var userRegister = response.then()
                .body("username",response->notNullValue())
                .body("password",response->notNullValue())
                .body("username",response->equalTo(user.username()))
                .and()
                .extract().body().as(User.class);
        assertNotNull(userRegister);
    }

    @And("proporciono los datos de registro omitiendo el {string}")
    public void proporcionoLosDatosDeRegistroOmitiendoEl(String campo) {
        if("usuario".equals(campo)){
            userBuilder.username(null);
        } else if ("clave".equals(campo)) {
            userBuilder.password(null);
        }
    }

    @And("un mensaje que indica que el {string}")
    public void unMensajeQueIndicaQueEl(String mensaje) {
        Error[] errores = response.then()
                .body("error",response->notNullValue())
                .body("error", hasItems(mensaje))
                .extract().body().as(Error[].class);
        assertTrue(errores.length>0);
    }
}
