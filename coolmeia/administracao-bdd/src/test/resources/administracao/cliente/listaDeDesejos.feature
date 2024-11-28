Feature: Notificar Produtos Na Lista de Desejos

  Scenario: Produto com poucas quantidades
    Given Um cliente com um produto na lista de desejos
    When A quantidade do produto fica "menor que" o determinado como pouco
    Then o cliente "recebe" uma notificação pelo email

  Scenario: Produto com muita quantidades
    Given Um cliente com um produto na lista de desejos
    When A quantidade do produto fica "maior que" o determinado como pouco
    Then o cliente "não recebe" uma notificação pelo email