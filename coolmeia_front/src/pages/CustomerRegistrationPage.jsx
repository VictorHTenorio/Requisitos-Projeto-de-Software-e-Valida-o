import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';

const CustomerRegistrationPage = () => {
  const navigate = useNavigate();
  const [customer, setCustomer] = useState({
    cpf: '',
    nome: '',
    email: '',
    senha: '',
    nascimento: '',
  });
  const [errorMessage, setErrorMessage] = useState('');

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCustomer((prevCustomer) => ({
      ...prevCustomer,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/coolmeia/clientes', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          cpf: customer.cpf,
          nome: customer.nome,
          email: customer.email,
          senha: customer.senha,
          nascimento: new Date(customer.nascimento).getTime(),
        }),
      });

      if (response.ok) {
        console.log('Cliente cadastrado com sucesso!');
        navigate('/login'); // Redirecionar para a página de login
      } else {
        // Obtém a mensagem do servidor ou uma genérica
        const errorData = await response.json().catch(() => response.text());
        const errorMessage =
          (typeof errorData === 'string' && errorData) ||
          errorData.message ||
          'Erro desconhecido. Por favor, tente novamente.';
        setErrorMessage(errorMessage);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
      setErrorMessage('Ocorreu um erro na requisição. Por favor, tente novamente.');
    }
  };

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />

      <main className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-black mb-8">Cadastro de Cliente</h1>

        {errorMessage && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {errorMessage}
          </div>
        )}

        <form onSubmit={handleSubmit}>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label htmlFor="cpf" className="block text-sm font-medium text-black">
                CPF
              </label>
              <input
                type="text"
                id="cpf"
                name="cpf"
                value={customer.cpf}
                onChange={handleInputChange}
                placeholder="xxxxxxxxxxx"
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label htmlFor="nome" className="block text-sm font-medium text-black">
                Nome
              </label>
              <input
                type="text"
                id="nome"
                name="nome"
                value={customer.nome}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label htmlFor="email" className="block text-sm font-medium text-black">
                E-mail
              </label>
              <input
                type="email"
                id="email"
                name="email"
                value={customer.email}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label htmlFor="senha" className="block text-sm font-medium text-black">
                Senha
              </label>
              <input
                type="password"
                id="senha"
                name="senha"
                value={customer.senha}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label htmlFor="nascimento" className="block text-sm font-medium text-black">
                Data de Nascimento
              </label>
              <input
                type="date"
                id="nascimento"
                name="nascimento"
                value={customer.nascimento}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
          </div>

          <div className="mt-8">
            <button
              type="submit"
              className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-amber-600 hover:bg-amber-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-amber-500"
            >
              Cadastrar
            </button>
          </div>
        </form>
      </main>

      <Footer />
    </div>
  );
};

export default CustomerRegistrationPage;
