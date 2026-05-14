# TrabalhoTeste — Sistema de Recomendação de Filmes

Projeto Java/Spring Boot focado em qualidade de software para recomendação de filmes com base no perfil do usuário.

## Objetivo

Gerar recomendações de filmes considerando:
- preferências por gênero;
- faixa de duração;
- classificação etária máxima;
- idiomas aceitos;
- histórico de filmes assistidos e notas.

## Tecnologias

- Java 21
- Spring Boot 4.0.6
- Maven
- JUnit 5
- Mockito

## Estrutura do projeto

```text
src/main/java/com/cineRadar/testeQualidade
├── model        # Entidades de domínio (Filme, Usuario, PerfilCinefilo, Recomendacao)
├── service      # Regras de recomendação e integrações (RecomendadorService, CalculadoraScore, FiltroFilmes)
├── repository   # Contratos de persistência (HistoricoUsuarioRepository)
├── exception    # Exceções de domínio
└── util         # Utilitários (ex.: gerador aleatório)

src/test/java/com/cineRadar/testeQualidade
├── model        # Testes de modelo
└── service      # Testes de serviços
```

## Como executar

### Pré-requisitos
- JDK 21 instalado e configurado no ambiente.
- Maven 3.9+.

### Rodar testes
Na raiz do projeto:

```bash
mvn test
```

### Rodar aplicação

```bash
mvn spring-boot:run
```

## Principais componentes

- **RecomendadorService**: orquestra catálogo, filtros, score, ordenação, registro de histórico e notificação.
- **FiltroFilmes**: remove filmes não elegíveis para o perfil.
- **CalculadoraScore**: calcula score e justificativa da recomendação.
- **CtalogoMock**: catálogo mockado de filmes para uso em testes/cenários locais.

## Observações

- O projeto contém testes unitários e de integração para os fluxos principais de recomendação.
- O diretório `src/main/resources` não é obrigatório para execução atual do projeto.
