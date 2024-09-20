# Ecommerce

> Projeto final da disciplina **Java: Programação Orientada a Objetos** da formação em Residência TIC no Serratec. O programa foi desenvolvido em linguagem **Java**, versão 17, através da IDE **Eclipse**.

## Descrição do Projeto

O projeto busca simular um programa de compra online no qual é possível consultar e alterar tabela de clientes, produtos e pedidos atraves do console. O sistema tem integração com banco de dados **PostgreSQL** onde busca informações pré definidas de: cadastro de clientes e produtos.

## Diagramas
[DDL - Linguagem de Definiçã de Dados](https://drawsql.app/teams/r-104/diagrams/ddl-poo-final) <br>
[UML - Linguagem de Modelagem Unificada](https://drive.google.com/file/d/1YGCalqiqX52iYX2sX-2_w-ppUHbyesNj/view)

## Funcionaliades do Projeto
_O sistema implementado permite:_

- **Cadastrar clientes e editar clientes**: Além dos dados já inseridos no banco de dados, é possível incluir novos cadastros de clientes. Este registro armazena: nome, CPF, data de nascimento e armazena um serial id. O sistema também permite alterar incluir, alterar, apagar, localizar cliente e listar clientes. <br>
- **Fazer e alterar pedidos**: O menu de pedidos armazena id do pedido, id do cliente, data de emissão e entrega, observação e valor da compra. A partir dela é possível incluir produtos a um pedido, alterar, listar, localizar ou apagar um pedido ou item de uma compra.<br>
- **Tratamento de erro**: O projeto contém alguns pré requisitos para salvar um pedido, como: não é possível criar um pedido com 0 produtos no carrinho e também não é possível registrar um pedido sem vínculo com algum cliente.<br>

## Tabelas Existentes
- Pessoa
- CLiente
- Produto
- Categoria
- Pedido
- PedidoItem

### Como executar
`1.` Clone o repositório em sua máquina. <br>
`2.` Após descompactar a pasta, importe o diretorio no Eclipse no caminho: Files > Open Projects from File System... > encontrar a pasta com downlad do arquivo .zip. <br>
`3.` Execute o código para realização de testes.<br>

### Observações
> Este projeto é um exercício acadêmico que tem objetivo de aprimorar as habilidades de programação dos alunos em **Java** como uma linguagem de programação orientada a objetos. Sugestões de melhorias são bem-vindas.

### Equipe de Desenvolvimento
_Este projeto foi desenvolvido pelo Grupo 3 da Turma 22 (Nova Friburgo/Teresópolis) do Serratec 2024.2 com os seguintes integrantes:_

* Andressa Assis
* Eduarda Pinho
* Eric Duarte
* Isabella Pinheiro
* Lucas Gomes
* Renata Rodrigues
* Samuel Teldison
