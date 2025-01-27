
# Desafio WebPublico

Neste desafio foi gerado uma API em Java com Spring Boot. Pode estar cadastrando Pessoas Fisicas ou Juridicas. Dependendo do numero de Cadastro, vai ser indentificado se é um CPF ou CNPJ. 
Após o cadastro da Pessoa, pode estar acadastrando endereços. 
Foi desenvolvido um CRUD para Pessoas e outro para Endereçoes. 

A documentação das rotas pode ser visualizadas após executar a api, acessando no navegador web: http://localhost:8080/swagger-ui.html

## Tecnologias 🚀

- Java 21
- PostgreSQL
- Spring Boot
- Swagger
- Docker

## Como utilizar ❓

Para subir o banco de dados deve acessar a pasta do projeto e digitar o seguinte comando no terminal bash:
```bash
$ docker compose up -d 
```
Para subir a API deve acessar a pasta do projeto e digitar o seguinte comando no terminal bash:
```bash
$ docker build -t "nome-da-imagem" . 
$ docker run -p 8080:8080 "nome-da-imagem"
```
