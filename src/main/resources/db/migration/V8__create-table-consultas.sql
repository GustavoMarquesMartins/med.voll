CREATE TABLE consultas (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           medico_id BIGINT NOT NULL,
                           paciente_id BIGINT NOT NULL,
                           data DATETIME NOT NULL,

                           PRIMARY KEY (id),
                           CONSTRAINT fk_consultas_medico_id FOREIGN KEY (medico_id) REFERENCES medicos(id),
                           CONSTRAINT fk_consultas_paciente_id FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);
