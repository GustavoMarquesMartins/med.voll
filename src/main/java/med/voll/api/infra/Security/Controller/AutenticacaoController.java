package med.voll.api.infra.Security.Controller;

import jakarta.validation.Valid;
import med.voll.api.infra.Security.DTO.DadosEfetuarLogin;
import med.voll.api.infra.Security.DTO.ResponseToken;
import med.voll.api.infra.Security.Domain.Usuario;
import med.voll.api.infra.Security.Servico.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosEfetuarLogin dados) {
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = authenticationManager.authenticate(token);
        var tokenGerado = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok().body(new ResponseToken(tokenGerado));
    }
}
