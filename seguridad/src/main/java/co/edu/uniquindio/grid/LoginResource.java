package co.edu.uniquindio.grid;

import co.edu.uniquindio.grid.dto.Login;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Path("/login")
public class LoginResource {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String login(Login login) {

        return Jwt.issuer("https://grid.uniquindio.edu.co")
                .subject(login.username())
                .groups("user")
                .expiresAt(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant())
                .sign();

//        return Jwt.issuer("grid.uniquindio.edu.co")
//                .subject(login.username())
//                .expiresAt(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant())
//                .sign(KeyUtils.generateSecretKey(SignatureAlgorithm.HS512));

//        return Jwt.issuer("grid.uniquindio.edu.co")
//                .subject(login.username())
//                .expiresAt(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant())
//                .innerSign(KeyUtils.generateSecretKey(SignatureAlgorithm.HS512)).encrypt(KeyUtils.generateSecretKey(KeyEncryptionAlgorithm.A256KW));
    }
}
