# Vila DevIn House 2.0

## Startando a aplicação
### Suba o banco de dados
- No terminal, execute o comando `docker-compose up -d`, irá subir o banco de dados MySQL em um container para acessar com a aplicação.
- Execute o arquivo **VilaApplication.java** e suba o servidor de aplicação através do SpringBoot.
- Além do container MySQL, também irá subir o container responsável pelo RabbitMQ:
  - endereço: `http://localhost:15672/`
  - usuário: guest
  - senha: guest

### Variáveis de ambiente
- Você precisa configurar duas variáveis de ambiente referente ao serviço de e-mail:
  - MAIL_USER: endereço de e-mail usado como remetente, deve ser válido
  - MAIL_PASSWORD: senha do e-mail remetente, deve ser válido

## Documentação aplicação
- Após subir a aplicação, acesse a página referente a documentação da API: http://localhost:8080/swagger-ui/#/
- Ao subir a aplicação, o FlyWay irá executar as migrations criando um usuário tipo admin com os seguintes dados:
  - email: admin@admin.com
  - senha: admin