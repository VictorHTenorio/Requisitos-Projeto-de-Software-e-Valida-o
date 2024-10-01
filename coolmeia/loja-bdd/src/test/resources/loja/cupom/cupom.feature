Feature: Aplicar cupom de desconto no carrinho

  Scenario: Aplicar um cupom válido
    Given Um carrinho de compras com produtos
    And Um cupom de desconto válido
    When O usuário aplica o cupom ao carrinho
    Then O valor total do carrinho deve ser reduzido pelo valor do desconto

  Scenario: Aplicar um cupom expirado
    Given Um carrinho de compras com produtos
    And Um cupom de desconto expirado
    When O usuário aplica o cupom ao carrinho
    Then O valor do cupom não deve ser aplicado
    And Uma mensagem de erro deve ser exibida
