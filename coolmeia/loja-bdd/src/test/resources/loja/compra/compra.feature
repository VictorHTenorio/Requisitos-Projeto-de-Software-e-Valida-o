Feature: Realizar compra

  Scenario: Produto disponível
    Given Um "produto" "está" disponível
    When um cliente coloca o produto no carrinho
    Then o cliente finaliza a compra
    And a quantidade do produto é decrementada de acordo com a quantidade comprada

  Scenario: Produto indisponível
    Given Um "produto" "não está" disponível
    When um cliente coloca o produto no carrinho
    And o cliente finaliza a compra
    Then A loja informa que a venda não pode ser realizado