package ru.akulin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ru.akulin.exceptions.LoginControllerException;
import ru.akulin.input.Credentials;
import ru.akulin.output.Error;
import ru.akulin.output.Response;
import ru.akulin.output.Token;
import ru.akulin.services.TokenService;

@RestController
@RequestMapping(path = "/login")
public class LoginController {

    @Autowired
    TokenService tokenService;

    @Autowired
    private DataSource ds;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response> login(@RequestBody Credentials credentials) {

        Response response;

        try (Connection conn = ds.getConnection()) {

            String cmd = "select login from users where (login = ?) and (password = ?)";

            try (PreparedStatement st = conn.prepareStatement(cmd)) {
                st.setString(1, credentials.getLogin());
                st.setString(2, credentials.getPassword());
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    String token = tokenService.generateToken(rs.getString("login"));
                    response = new Token(token);
                } else {
                    response = new Error("Неверное имя пользователя или пароль");
                }
            }
        }
        catch (Exception cause) {
            throw new LoginControllerException("Видимо, что-то случилось", cause);
        }

        return ResponseEntity.ok(response);
    }
}