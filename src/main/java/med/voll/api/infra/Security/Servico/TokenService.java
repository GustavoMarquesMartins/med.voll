package med.voll.api.infra.Security.Servico;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.infra.Security.Domain.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${secret.senha}")
    private String secretSenha;

    public String gerarToken(Usuario usuario) {

        try {
            var algoritimo = Algorithm.HMAC256(secretSenha);
            return JWT.create().withIssuer("API Voll.med").withSubject(usuario.getLogin()).withExpiresAt(dataExpiracao()).withClaim("id", usuario.getId()).sign(algoritimo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("erro ao gerar token jwt ", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secretSenha);
            return JWT.require(algoritmo)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
