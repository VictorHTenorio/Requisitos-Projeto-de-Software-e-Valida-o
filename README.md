
![image](https://github.com/user-attachments/assets/dd52b1b6-0c8e-4950-913f-99aac1967066)



# Sobre o projeto
<p align="justify">
O presente projeto é fruto da disciplina de Requisitos, Projeto de Software e Validação, do 5° período do curso de Ciências da Computação da CESAR School. <br>Nossa ideia foi criar uma plataforma de e-commerce do segmento de moda, a qual nomeamos Coolmeia. Para o desenvolvimento do projeto, usamos arquitetura limpa, modularização em Maven e testes em Cucumber.
</p>

## Requisitos   

### Apresentação da AV2
<li><a href="https://github.com/VictorHTenorio/Requisitos-Projeto-de-Software-e-Valida-o/blob/main/Coolmeia%202.pdf" target="_blank">Slides apresentados em aula</a></li>

### Descrição do domínio
<p align="justify">
No cenário de e-commerce, a jornada de compra do usuário envolve diversas etapas até a finalização do pedido. Inicialmente, o usuário escolhe os produtos de interesse e os adiciona ao carrinho de compras. Ele pode continuar navegando e incluir múltiplos itens no carrinho. Ao revisar os produtos selecionados, o usuário tem a opção de inserir um cupom de desconto para reduzir o valor final da compra. Nesse momento, o frete é calculado e diferentes formas de entrega são oferecidas. O próximo passo é a escolha do endereço de entrega e a forma de pagamento. Com o pagamento confirmado, o pedido é processado. Após a entrega, o usuário tem a oportunidade de deixar uma avaliação do produto, contribuindo para a experiência de outros compradores.
  <ul>
  <li><a href="https://docs.google.com/document/d/1vmuGp5wZvKqbisBxY-w7H0DZ6IsJgloKSFrfmB0Sgt0/edit#heading=h.f3fdtgnd6a8a" target="_blank">Descrição do domínio</a></li>
  </ul>
  </p>
  <br>


  
### Funcionalidades
<p align="justify">
  
- **Finalizar compra**: ao concretizar-se a compra, a quantidade de produtos no estoque é atualizada, sendo decrementado os itens vendidos.
- **Aplicar cupom no carrinho**: aplica-se desconto nos produtos elegíveis para o cupom.
- **Notificar baixo estoque de produto na lista de desejos**: informa o usuário, via e-mail, quando algum dos itens da sua lista de desejo está com pouca quantidade no estoque.
- **Destacar produtos novos**: adiciona os produtos recém criados na página de destade do site.
</p>

## Funcionalidades via interface
As funcionalidades foram desenvolvidas via React, com backend em SpringBoot

## Padrões de projeto
Neste projeto foram adotadas os padrões de projeto Singleton e Observer

## Protótipo de Baixa Fidelidade
<p align="justify">
Protótipo de baixa fidelidade realizado no figma pode ser acessado com o link a seguir.
<br>
<ul>
  <li><a href="https://www.figma.com/design/vAj3TpkjGlNOnrVvGaheVV/lofi?node-id=0-1&t=ifviIYi3P2zFXAhF-1" target="_blank">Protótipo de baixa</a></li>
</ul>
</p>




## Mapa de Histórias
<p align="justify">
Mapas de histórias que representa a interação do usuário com o nosso sistema desenvolvido em Avion pode ser acessado no link a seguir.
<br>
<ul>
  <li><a href="https://raw.githubusercontent.com/VictorHTenorio/Requisitos-Projeto-de-Software-e-Valida-o/refs/heads/main/mapa_de_historias.png" target="_blank">Mapa de histórias</a></li>
</ul>
</p>
<br>

## Mapa de Contexto
<p align="justify">
Mapas de contexto do nosso programa desenvolvidos com o uso do Context Mapper podem ser acessados com os links abaixo.
<br>
<ul>
  <li><a href="https://raw.githubusercontent.com/VictorHTenorio/Requisitos-Projeto-de-Software-e-Valida-o/refs/heads/main/context_BC_Administracao_puml.png" target="_blank">Context Map (Administração)</a></li>
  <li><a href="https://raw.githubusercontent.com/VictorHTenorio/Requisitos-Projeto-de-Software-e-Valida-o/refs/heads/main/context_BC_Controle_puml.png" target="_blank">Context Map (Controle)</a></li>
  <li><a href="https://raw.githubusercontent.com/VictorHTenorio/Requisitos-Projeto-de-Software-e-Valida-o/refs/heads/main/context_BC_Loja_puml.png" target="_blank">Context Map (Loja)</a></li>
</ul>
</p>
<br>


## Nossos desenvolvedores
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/Arrudadiego">
        <img src="https://avatars.githubusercontent.com/u/116604134?v=4" width="100px;" alt="Foto Diego"/><br>
        <sub>
          <b>Diego Arruda</b>
        </sub>
      </a>
      <br>
      <sub>dca2@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/flavio-muniz">
        <img src="https://avatars.githubusercontent.com/u/116359369?v=4" width="100px;" alt="Foto Flavio"/><br>
        <sub>
          <b>Flavio Muniz</b>
        </sub>
      </a>
      <br>
      <sub>frssm@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/hhenrique7510">
        <img src="https://avatars.githubusercontent.com/u/112914701?v=4" width="100px;" alt="Foto Henrique"/><br>
        <sub>
          <b>Henrique Roma</b>
        </sub>
      </a>
      <br>
      <sub>hrm@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/joaohlafeta">
        <img src="https://avatars.githubusercontent.com/u/105346791?v=4" width="100px;" alt="Foto Joao"/><br>
        <sub>
          <b>João Lafetá</b>
        </sub>
      </a>
      <br>
      <sub>jhml@cesar.school</sub>
    </td>
  </tr>
</table>
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/Cenafowzin">
        <img src="https://avatars.githubusercontent.com/u/83378430?v=4" width="100px;" alt="Foto Rodrigo"/><br>
        <sub>
          <b>Rodrigo Dubeux</b>
        </sub>
      </a>      
      <br>
      <sub>rdmo@cesar.school</sub>
    </td>
    <td align="center">
      <a href="https://github.com/VictorHTenorio">
        <img src="https://avatars.githubusercontent.com/u/101901740?v=4" width="100px;" alt="Foto Victor"/><br>
        <sub>
          <b>Victor Hora</b>
        </sub>
      </a>
      <br>
      <sub>vht@cesar.school</sub>
    </td>
  <td align="center">
      <a href="https://github.com/Yara-R">
        <img src="https://avatars.githubusercontent.com/u/103130662?v=4" width="100px;" alt="Foto Yara"/><br>
        <sub>
          <b>Yara Rodrigues</b>
        </sub>
      </a>
      <br>
      <sub>yri@cesar.school</sub>
    </td>
  </tr>
</table>
<br>
