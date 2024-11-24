import React, { useState } from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';

const CustomerRegistrationPage = () => {
  const [customer, setCustomer] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    cpf: '',
    birthDate: '',
    cep: '',
    address: {
      street: '',
      neighborhood: '',
      city: '',
      state: '',
    },
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCustomer((prevCustomer) => ({
      ...prevCustomer,
      [name]: value,
    }));
  };

  const handleCepChange = async (e) => {
    const cep = e.target.value;
    setCustomer((prevCustomer) => ({
      ...prevCustomer,
      cep,
    }));

    if (cep.length === 8) {
      try {
        const response = await fetch(`https://viacep.com.br/ws/${cep}/json/`);
        const data = await response.json();

        if (!data.erro) {
          setCustomer((prevCustomer) => ({
            ...prevCustomer,
            address: {
              street: data.logradouro,
              neighborhood: data.bairro,
              city: data.localidade,
              state: data.uf,
            },
          }));
        } else {
          setCustomer((prevCustomer) => ({
            ...prevCustomer,
            address: {
              street: '',
              neighborhood: '',
              city: '',
              state: '',
            },
          }));
        }
      } catch (error) {
        console.error('Erro ao buscar o endereço:', error);
      }
    } else {
      setCustomer((prevCustomer) => ({
        ...prevCustomer,
        address: {
          street: '',
          neighborhood: '',
          city: '',
          state: '',
        },
      }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Aqui você pode enviar os dados do cliente para o backend ou fazer outras ações necessárias
    console.log('Dados do cliente:', customer);
  };

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />

      <main className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-black mb-8">Cadastro de Cliente</h1>

        <form onSubmit={handleSubmit}>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label htmlFor="firstName" className="block text-sm font-medium text-black">
                Nome
              </label>
              <input
                type="text"
                id="firstName"
                name="firstName"
                value={customer.firstName}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label htmlFor="lastName" className="block text-sm font-medium text-black">
                Sobrenome
              </label>
              <input
                type="text"
                id="lastName"
                name="lastName"
                value={customer.lastName}
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
              <label htmlFor="phone" className="block text-sm font-medium text-black">
                Telefone
              </label>
              <input
                type="tel"
                id="phone"
                name="phone"
                value={customer.phone}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
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
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label htmlFor="birthDate" className="block text-sm font-medium text-black">
                Data de Nascimento
              </label>
              <input
                type="date"
                id="birthDate"
                name="birthDate"
                value={customer.birthDate}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label htmlFor="cep" className="block text-sm font-medium text-black">
                CEP
              </label>
              <input
                type="text"
                id="cep"
                name="cep"
                value={customer.cep}
                onChange={handleCepChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
          </div>

          {customer.address.street && (
            <div className="mt-6">
              <h2 className="text-lg font-medium text-black">Endereço</h2>
              <div className="mt-4">
                <p>{customer.address.street}</p>
                <p>{customer.address.neighborhood}</p>
                <p>{customer.address.city} - {customer.address.state}</p>
              </div>
            </div>
          )}

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