Feature: Adicionar Produto a Lista de Novidades

  Scenario: Novo produto criado
    Given Um produto novo foi criado
    Then O produto é adicionado na lista de novos produtos

  Scenario: Produto antigo na lista
    Given Um produto antigo esta na novos produtos
    When A verificação é realizada
    Then O produto é removido da lista de novos produtos