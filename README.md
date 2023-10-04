# FunciCad - Sistema de Cadastro de Funcionários

## Descrição do Projeto

O FunciCad é um sistema de cadastro de funcionários projetado para facilitar a gestão de informações sobre os colaboradores de uma organização. Ele oferece funcionalidades de cadastro, edição, exclusão e listagem de funcionários, bem como a associação de funcionários a departamentos específicos.

## História de Usuário

### Como administrador da empresa, eu quero poder cadastrar novos funcionários no sistema para manter um registro organizado de nossa equipe.

**Critérios de Aceitação**:

- Deve ser possível inserir informações pessoais dos funcionários, incluindo nome, RG, data de nascimento, cargo, salário, data de admissão e status.
- Deve ser possível associar cada funcionário a um departamento específico.
- Após o cadastro, os detalhes do funcionário devem ser visíveis na lista de funcionários.

## Diagrama de classes

![Diagrama de classes](src/main/resources/FuncioCad.png)

### Query para rodar

```
-- Primeiro, exclua a tabela funcionario se ela já existir
create database trabalhopoo2;

use trabalhopoo2;

-- Em seguida, crie a tabela funcionario com o campo rg como INT
CREATE TABLE funcionario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    rg INT NOT NULL,
    cargo VARCHAR(255) NOT NULL,
    salario DOUBLE NOT NULL,
    dataNascimento DATE NOT NULL,
    dataAdmissao DATE NOT NULL,
    departamento_id INT NOT NULL,
    FOREIGN KEY (departamento_id) REFERENCES departamento(id)
);


-- Depaartamento
CREATE TABLE departamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL
);



INSERT INTO departamento (nome, status)
VALUES
    ('Departamento de Desenvolvimento', 'Ativo'),
    ('Departamento de Marketing', 'Inativo'),
    ('Departamento de Recursos Humanos', 'Ativo');


-- Insira um novo registro com status como string
INSERT INTO departamento (nome, status)
VALUES ('Novo Departamento', 'Inativo');


-- Inserir 4 registros na tabela funcionario com campo rg como INT
INSERT INTO funcionario (nome, rg, cargo, salario, dataNascimento, dataAdmissao, departamento_id)
VALUES 
  ('João Silva', 123456789, 'Gerente', 5000.00, '1990-01-15', '2023-09-25', 1),
  ('Maria Santos', 987654321, 'Analista', 3500.00, '1995-05-20', '2023-09-25', 2),
  ('Carlos Oliveira', 111223344, 'Programador', 4000.00, '1988-07-10', '2023-09-25', 1),
  ('Ana Rodrigues', 555666777, 'Designer', 3200.00, '1992-03-28', '2023-09-25', 1);

```