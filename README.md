# Vila DevIn House 2.0

## Startando a aplicação
### Suba o banco de dados
- No terminal, execute o comando `docker-compose up -d`, irá subir o banco de dados MySQL em um container para acessar com a aplicação.
- Execute o arquivo **VilaApplication.java** e suba o servidor de aplicação através do SpringBoot.

## Documentação aplicação
- Após subir a aplicação, acesse a página referente a documentação da API: http://localhost:8080/swagger-ui/#/
- Ao subir a aplicação, o FlyWay irá executar as migrations criando um usuário tipo admin com os seguintes dados:
  - email: admin@admin.com
  - senha: admin