# CineRadar 🎬

**CineRadar** é um sistema recomendador de filmes por perfil cinéfilo.  
O projeto analisa preferências do usuário, filtra filmes incompatíveis e gera uma lista ranqueada com as melhores recomendações.

> **Slogan:** O seu filme de amanhã, hoje


---

## Integrantes

- Pedro Henrique Bispo
- Isaac Nascimento
- Katia Noblat

---

## Sobre o projeto

O CineRadar recomenda filmes com base em informações do perfil do usuário, como:

- gêneros preferidos;
- duração ideal;
- classificação etária máxima;
- idiomas aceitos;
- filmes já assistidos;
- notas dadas anteriormente.

A recomendação é feita em três etapas principais:

1. **Busca do catálogo** pela interface `CatalogoFilmesAPI`.
2. **Filtragem dos filmes** usando regras obrigatórias.
3. **Cálculo de score e ranqueamento** para retornar os melhores candidatos.

---

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Maven
- JUnit 5
- Mockito
- H2 Database
- Lombok

---

## Estrutura do projeto

```text
src/
├── main/java/com/cineRadar/testeQualidade/
│   ├── exception/
│   │   ├── DuracaoInvalidaException.java
│   │   ├── NotaInvalidaException.java
│   │   ├── PerfilIncompletoException.java
│   │   └── PesoInvalidoException.java
│   ├── model/
│   │   ├── Filme.java
│   │   ├── PerfilCinefilo.java
│   │   ├── Recomendacao.java
│   │   ├── Usuario.java
│   │   └── enums/
│   │       ├── ClassificacaoEtaria.java
│   │       ├── Genero.java
│   │       └── Idioma.java
│   ├── repository/
│   │   └── HistoricoUsuarioRepository.java
│   ├── service/
│   │   ├── CalculadoraScore.java
│   │   ├── CatalogoFilmesAPI.java
│   │   ├── CtalogoMock.java
│   │   ├── FiltroFilmes.java
│   │   ├── NotificadorPush.java
│   │   └── RecomendadorService.java
│   └── util/
│       └── GeradorAleatorio.java
└── test/java/com/cineRadar/testeQualidade/
    ├── model/
    │   ├── FilmeTest.java
    │   └── PerfilCinefiloTest.java
    └── service/
        ├── CalculadoraScoreTest.java
        ├── FiltroFilmesTest.java
        └── RecomendadorServiceTest.java
```

---

## Principais classes

| Classe/Interface | Função |
|---|---|
| `Usuario` | Representa a pessoa que recebe recomendações. |
| `PerfilCinefilo` | Guarda preferências, histórico e notas do usuário. |
| `Filme` | Representa um filme do catálogo. |
| `Recomendacao` | Resultado da recomendação, com filme, score e justificativa. |
| `FiltroFilmes` | Remove filmes incompatíveis com o perfil. |
| `CalculadoraScore` | Calcula a pontuação de compatibilidade. |
| `RecomendadorService` | Orquestra busca, filtro, score, ordenação, histórico e notificação. |
| `CatalogoFilmesAPI` | Interface mockável para o catálogo de filmes. |
| `HistoricoUsuarioRepository` | Interface mockável para persistência do histórico. |
| `NotificadorPush` | Interface mockável para envio de notificações. |
| `GeradorAleatorio` | Interface mockável para sorteios e desempates. |

---

## Regras de filtragem

A classe `FiltroFilmes` remove filmes que não podem ser recomendados ao usuário:

- filmes já assistidos;
- filmes acima da classificação etária máxima do perfil;
- filmes em idioma não aceito;
- filmes com gênero de peso `0.0`, indicando rejeição explícita.

---

## Fórmula de score

A classe `CalculadoraScore` calcula uma nota final de `0` a `100` considerando:

| Componente | Peso |
|---|---:|
| Compatibilidade de gênero | 50% |
| Aderência à duração preferida | 20% |
| Popularidade | 15% |
| Afinidade histórica | 15% |

As constantes estão declaradas no código para evitar números mágicos:

```java
public static final double PESO_GENERO = 50.0;
public static final double PESO_DURACAO = 20.0;
public static final double PESO_POPULARIDADE = 15.0;
public static final double PESO_AFINIDADE = 15.0;
```

---

## Como executar o projeto

### Pré-requisitos

Antes de rodar, instale:

- Java 21 ou superior;
- Maven, caso não use o wrapper do projeto;
- IDE de sua preferência, como IntelliJ IDEA, Eclipse ou VS Code.

### Clonar o repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd TrabalhoTeste-main
```

### Rodar a aplicação

No Windows:

```bash
mvnw.cmd spring-boot:run
```

Com Maven instalado:

```bash
mvn spring-boot:run
```

---

## Como rodar os testes

No Windows:

```bash
mvnw.cmd test
```

Com Maven instalado:

```bash
mvn test
```

Os testes estão organizados em:

- testes de modelo;
- testes de cálculo de score;
- testes de filtragem;
- testes do serviço de recomendação com Mockito.

---

## Testes implementados

O projeto possui testes para os principais requisitos:

### PerfilCinefilo

- criação de perfil com pesos válidos;
- exceção para peso fora de `0.0` a `1.0`;
- exceção para duração mínima maior que a máxima;
- exceção para nota fora de `1` a `5`;
- registro de filme assistido no histórico.

### Filme

- criação com atributos preenchidos;
- comparação entre filmes pelo mesmo ID.

### CalculadoraScore

- cálculo por gênero;
- cálculo por duração;
- influência da popularidade;
- bônus por afinidade histórica;
- garantia de score entre `0` e `100`.

### FiltroFilmes

- remoção de filme já assistido;
- remoção por classificação etária;
- remoção por idioma não aceito;
- remoção por gênero rejeitado;
- retorno de lista vazia para catálogo vazio.

### RecomendadorService

- retorno limitado por `topN`;
- ordenação por score;
- desempate por popularidade;
- tratamento de catálogo vazio;
- tratamento de falha da API de catálogo;
- registro de recomendações no histórico;
- envio ou não envio de notificação conforme configuração do usuário;
- modo **Surpreenda-me** com sorteio controlado.

---

## Uso de Mockito

O Mockito foi usado para simular dependências externas e tornar os testes previsíveis.

### Dependências mockadas

| Dependência | Motivo |
|---|---|
| `CatalogoFilmesAPI` | Evita depender de API externa ou internet durante os testes. |
| `HistoricoUsuarioRepository` | Evita gravar dados reais durante os testes. |
| `NotificadorPush` | Evita envio real de notificações. |
| `GeradorAleatorio` | Controla sorteios e desempates para evitar testes instáveis. |

### Dependências não mockadas

| Classe | Motivo |
|---|---|
| `CalculadoraScore` | É lógica pura; mockar esconderia erros na fórmula. |
| `FiltroFilmes` | É lógica pura; deve ser testada de verdade. |
| `Filme`, `Usuario`, `PerfilCinefilo`, `Recomendacao` | São objetos de domínio. |
| Enums | São valores fixos; não faz sentido mockar. |

### Recursos usados

O projeto utiliza recursos como:

- `@ExtendWith(MockitoExtension.class)`;
- `@Mock`;
- `when(...).thenReturn(...)`;
- `when(...).thenThrow(...)`;
- `verify(...)`;
- `never()`;
- `times(...)`;
- `ArgumentCaptor`;
- `spy(...)`.

---

## Exemplo de fluxo de recomendação

1. O usuário informa seu perfil cinéfilo.
2. O `RecomendadorService` busca os filmes pelo `CatalogoFilmesAPI`.
3. O `FiltroFilmes` remove filmes incompatíveis.
4. A `CalculadoraScore` calcula a compatibilidade dos filmes restantes.
5. O serviço ordena por score, popularidade e desempate aleatório.
6. O sistema registra a recomendação no histórico.
7. Se as notificações estiverem habilitadas, o usuário recebe uma notificação.

---

## Diagramas

O repositório contém arquivos de apoio para a apresentação:

- diagrama de classes;
- diagrama de sequência;
- PDF com os requisitos do projeto.

Arquivos presentes no projeto:

```text
mermaid-diagram-2026-04-30-114819.png
mermaid-ai-diagram-2026-04-30-143641.pdf
PROJETO_TESTE_SOFTWARE_2026.pdf
```

---

## Cobertura de testes

O requisito do projeto pede cobertura mínima de 80% nos pacotes `model` e `service`.

Para gerar relatório de cobertura, recomenda-se configurar o plugin **JaCoCo** no `pom.xml` e rodar:

```bash
mvn test jacoco:report
```

Depois, o relatório costuma ficar em:

```text
target/site/jacoco/index.html
```

> Adicionar aqui o print da cobertura antes da entrega final.

---

## Bugs encontrados e corrigidos

| Bug | Como foi encontrado | Correção |
|---|---|---|
| Peso de gênero inválido era aceito | Teste de validação do `PerfilCinefilo` | Adicionada exceção `PesoInvalidoException`. |
| Duração mínima maior que máxima podia criar perfil inconsistente | Teste de duração inválida | Adicionada exceção `DuracaoInvalidaException`. |
| Notas fora de `1` a `5` podiam ser cadastradas | Teste de nota inválida | Adicionada exceção `NotaInvalidaException`. |
| Falha no catálogo poderia derrubar a recomendação | Teste com `thenThrow` no mock da API | Serviço passou a retornar lista vazia em caso de falha. |
| Falha no notificador poderia interromper a recomendação | Teste com falha simulada no `NotificadorPush` | Notificação passou a ser tratada sem quebrar o fluxo principal. |

---

## Status atual

- [x] Modelos principais implementados
- [x] Enums implementados
- [x] Exceções específicas implementadas
- [x] Filtro de filmes implementado
- [x] Cálculo de score implementado
- [x] Serviço de recomendação implementado
- [x] Testes unitários com JUnit 5
- [x] Testes com Mockito
- [x] Uso de `ArgumentCaptor`
- [x] Uso de `spy`
- [ ] Print da cobertura de testes no README


---

## Apresentação

Para o pitch, o produto será apresentado como **CineRadar**, uma solução que ajuda pessoas a escolherem filmes com menos indecisão e mais transparência.

Pontos principais da apresentação:

- problema da escolha excessiva em streamings;
- solução personalizada por perfil;
- diferencial em relação às recomendações genéricas;
- explicação do score;
- demonstração de uso;
- demonstração técnica com JUnit e Mockito;
- aprendizados do projeto.

---

## Licença

Projeto acadêmico desenvolvido para fins educacionais.
