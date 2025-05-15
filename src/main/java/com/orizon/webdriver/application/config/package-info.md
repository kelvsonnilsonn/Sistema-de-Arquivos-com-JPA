# Pacote application.config

## Sobre

O pacote config, por padrão, representa as configurações que o spring, ou outras dependências, utilizaram. 

No momento, ele só possui a classe *Beans* responsável por *armazenar os Beans* que serão utilizados pelo Spring através da anotação **@Autowired**.
A classe Beans é marcada com a anotação **@Configuration** para que o Spring a reconheça como a configuração padrão.
