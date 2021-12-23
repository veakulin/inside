package ru.akulin.controllers;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import ru.akulin.entities.HistoricalMessage;
import ru.akulin.exceptions.MessageControllerException;
import ru.akulin.input.Message;
import ru.akulin.output.Error;
import ru.akulin.output.History;
import ru.akulin.output.Response;
import ru.akulin.services.TokenService;

@RestController
@RequestMapping(path = "/message")
public class MessageController {

    @Autowired
    TokenService tokenService;

    @Autowired
    private DataSource ds;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response> login(@RequestBody Message message, @RequestHeader("Authorization") String authHeader) {

        Response response;
        String token = authHeader.replace("Bearer ", "");

        try {
            // В задании не сказано, как именно проверять токен,
            // так что считаем, что самого факта успешной расшифровки достаточно
            Claims claims = tokenService.decodeToken(token);

            try (Connection conn = ds.getConnection()) {

                String sender = claims.getSubject();

                if (message.getText().equalsIgnoreCase("history 10")) {
                    response = new History(getHistory(conn, sender));
                } else {
                    saveMessage(conn, sender, message.getText());
                    response = new Response(true);
                }
            }
        }
        catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException cause) {
            // Если токен не расшифровался - это нормально,
            // Сообщим клиенту, а падать незачем.
            response = new Error(cause.getMessage());
        }
        catch (Exception cause) {
            throw new MessageControllerException("Видимо, что-то случилось", cause);
        }

        return ResponseEntity.ok(response);
    }

    private List<HistoricalMessage> getHistory(Connection conn, String from) throws SQLException {

        String cmd = "select m.timestamp, u.login as sender, m.text " +
                     "from messages as m " +
                     "join users as u " +
                     "on m.sender_id = u.id " +
                     "where (u.login = ?) " +
                     "order by timestamp desc " +
                     "limit 10";

        List<HistoricalMessage> result = new LinkedList<>();

        try (PreparedStatement st = conn.prepareStatement(cmd)) {
            st.setString(1, from);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                result.add(new HistoricalMessage(rs.getLong("timestamp"),
                                                 rs.getString("sender"),
                                                 rs.getString("text")));
            }
        }
        return result;
    }

    private void saveMessage(Connection conn, String sender, String text) throws SQLException {

        // Вложенный select внутри insert - это, наверное, нехорошо,
        // но мне нужен id пользователя, а два запроса делать не хочется.
        String cmd = "insert into messages (timestamp, sender_id, text) " +
                     "values (?, (select id from users where login = ?), ?)";

        try (PreparedStatement st = conn.prepareStatement(cmd)) {
            st.setLong(1, System.currentTimeMillis());
            st.setString(2, sender);
            st.setString(3, text);
            st.executeUpdate();
        }
    }
}