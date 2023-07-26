alter table medicos add status TINYINT;
alter table pacientes add status TINYINT;

update medicos set status =1;
update pacientes set status =1;