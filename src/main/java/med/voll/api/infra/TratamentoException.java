package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratamentoException {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity error400(MethodArgumentNotValidException error) {
        var listaError = error.getFieldErrors();
        return ResponseEntity.badRequest().body(listaError.stream().map(TratamentoExceptionResponseDados::new).toList());
    }

    private record TratamentoExceptionResponseDados(String campos, String mensagem) {
        public TratamentoExceptionResponseDados(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
