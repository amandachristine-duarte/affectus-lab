# Affectus Lab — Aplicação Desktop (AV2)

**Curso:** Tecnologia em Análise e Desenvolvimento de Sistemas  
**Disciplina:** Prática de Linguagem de Programação I — Java POO  
**Professor:** Rafael N. R.  
**Integrantes:** Amanda Christine Duarte, Patrick Carlos Alves Ferreira e Joel Nunes Varela

## Sobre o projeto

O Affectus Lab é um protótipo acadêmico de educação emocional. A aplicação desktop permite autenticação, gestão de usuários, check-in emocional e registro de reflexões em diário. É um recurso educativo, não substitui acompanhamento profissional.

## Funcionalidades implementadas

- Login e navegação pela tela inicial.
- Gestão de usuários: cadastrar, listar, atualizar e excluir (CRUD), com tabela `JTable` e confirmação de exclusão.
- Check-in emocional: escolha entre oito experiências, leitura de descrições e experiências relacionadas, indicação de intensidade de 0 a 10, gravação e consulta do histórico por usuário.
- Diário emocional: escrita, gravação e consulta de reflexões por usuário.
- Demonstração de orientação a objetos na classe `TestePOO.java` (herança e polimorfismo).

**Funcionalidades planejadas, não implementadas nesta versão:** histórias interativas, práticas de regulação, catálogo de yoga e movimentação de moedas Affectus. A tabela `carteira_affectus` está prevista na estrutura do banco.

## Tecnologias e organização

- Java 21; Apache NetBeans 23; Maven.
- Java Swing para as interfaces.
- PostgreSQL 18 e JDBC (driver PostgreSQL declarado no `pom.xml`).
- Organização em pacotes `model`, `view`, `controller` e `model.dao`, com padrão MVC e camada DAO.

## Estrutura dos arquivos

```text
AffectusLabDesktop/
├── pom.xml
├── src/
│   └── main/java/br/edu/fmp/affectuslab/
│       ├── AffectusLabDesktop.java
│       ├── TestePOO.java
│       ├── controller/
│       ├── model/
│       │   └── dao/
│       └── view/
├── database/
│   ├── affectus_lab.sql
│   └── affectus_lab_dados.sql
└── README.md
```

A pasta `target/` é gerada pelo Maven e não precisa ser enviada.

## Preparar o banco de dados

1. Instale e inicie o PostgreSQL. Os exemplos abaixo usam servidor `localhost`, porta `5432` e usuário `postgres`.
2. Crie um **banco vazio** chamado `affectus_lab` (pelo pgAdmin ou pelo comando `createdb`).
3. Abra o terminal na pasta `database/` do projeto e execute, nesta ordem:

```powershell
psql -h localhost -p 5432 -U postgres -d affectus_lab -v ON_ERROR_STOP=1 -f affectus_lab.sql
psql -h localhost -p 5432 -U postgres -d affectus_lab -v ON_ERROR_STOP=1 -f affectus_lab_dados.sql
```

> Se o comando `psql` não for reconhecido no Windows, utilize o **SQL Shell (psql)** instalado com o PostgreSQL ou execute o `psql.exe` pelo diretório `bin` da instalação. Os comandos acima pressupõem que o terminal está na pasta `database`.

O primeiro arquivo cria a estrutura e as restrições do banco. O segundo carrega **oito experiências emocionais** e **16 relações** entre elas. **Não execute os scripts em um banco que já possua as mesmas tabelas ou dados**, pois podem ocorrer conflitos. Por serem arquivos Plain exportados pelo PostgreSQL, prefira executá-los com `psql` em vez de colar seu conteúdo no Query Tool.

Para verificar a importação no pgAdmin, execute:

```sql
SELECT id, nome FROM emocao ORDER BY id;
SELECT COUNT(*) AS total_correlacoes FROM emocao_correlacionada;
```

Resultados esperados: oito experiências e 16 correlações.

Os scripts de entrega **não incluem usuários, senhas nem registros pessoais**.

## Configurar a conexão

A classe `Conexao` usa o banco `affectus_lab` em `localhost:5432`, com usuário `postgres`, e lê a senha da variável de ambiente `AFFECTUS_DB_PASSWORD`.

No **PowerShell**, configure a variável sem exibir a senha digitada:

```powershell
$segredo = Read-Host "Senha do PostgreSQL" -AsSecureString
$env:AFFECTUS_DB_PASSWORD = [System.Net.NetworkCredential]::new("", $segredo).Password
```

Essa variável vale **somente para a sessão atual do PowerShell e os programas iniciados a partir dela**. Para que o NetBeans a utilize, inicie a IDE a partir dessa sessão ou configure a variável de ambiente no Windows **antes** de abrir o NetBeans. Se mudar a variável no Windows, reinicie a IDE. **Não publique senhas reais no GitHub.**

## Abrir e executar no NetBeans

1. Abra o NetBeans 23 e escolha **File → Open Project**.
2. Selecione a pasta `AffectusLabDesktop` que contém `pom.xml` e `src`.
3. Aguarde o Maven resolver as dependências (pode ser necessária conexão com a internet no primeiro uso).
4. Confirme que o PostgreSQL está em execução, que o banco foi preparado e que a variável de senha está disponível para a IDE.
5. Clique com o botão direito no projeto e selecione **Clean and Build**.
6. Execute **Run Project (F6)**. A classe principal `br.edu.fmp.affectuslab.AffectusLabDesktop` abre a tela de login.

## Primeiro acesso e demonstração

Os scripts SQL não incluem contas de usuário. Na primeira execução, utilize a opção de cadastro da tela de login para criar uma conta de demonstração e depois entre com o e-mail e a senha cadastrados.

Roteiro sugerido:

1. Fazer login e abrir a página inicial.
2. Abrir **Gestão de Usuários** e demonstrar cadastro, listagem na `JTable`, atualização e exclusão de uma conta fictícia.
3. Abrir **Check-in Emocional**, selecionar uma experiência, escolher intensidade, salvar e consultar o histórico.
4. Abrir **Diário Emocional**, registrar uma reflexão fictícia e consultar o histórico.
5. Executar `TestePOO.java` para demonstrar herança (`Usuario extends Pessoa`), sobrescrita de método e polimorfismo (`Pessoa referencia = usuario`).

## Modelo de dados

O banco contém as tabelas `usuario`, `emocao`, `emocao_correlacionada`, `registro_emocional`, `entrada_diario` e `carteira_affectus`. Os arquivos SQL incluem a estrutura relacional e restrições de integridade.

## Segurança e limitações

Este é um protótipo acadêmico para execução local, **não preparado para produção**. Não utilize informações pessoais ou emocionais reais. A autenticação e o armazenamento de senhas exigem melhorias de segurança antes de qualquer disponibilização pública.

## Entrega acadêmica

O projeto acompanha código-fonte Java, `pom.xml`, scripts SQL e este README. A apresentação aborda o problema e a solução, diagrama UML, organização MVC/DAO, banco de dados, CRUD, conceitos de POO e demonstração funcional.

**Affectus Lab — Consciência emocional, autonomia e possibilidades de escolha.**
