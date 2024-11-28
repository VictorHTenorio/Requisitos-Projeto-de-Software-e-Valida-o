import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import Header from '../components/Header';
import Footer from '../components/Footer';

const LoginPage = () => {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [cpf, setCpf] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ cpf, senha: password }),
      });

      if (response.ok) {
        const message = await response.text();
        console.log(message);
        setErrorMessage('');
        login(cpf); // Salva o CPF no contexto e localStorage
        navigate('/');
      } else if (response.status === 404) {
        setErrorMessage('CPF não encontrado. Por favor, verifique o CPF digitado.');
      } else {
        const errorData = await response.text().catch(() => 'Erro desconhecido.');
        setErrorMessage(errorData);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
      setErrorMessage('Erro de conexão. Por favor, tente novamente.');
    }
  };

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />

      <main className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-black mb-8">Login</h1>

        {errorMessage && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {errorMessage}
          </div>
        )}

        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="cpf" className="block text-sm font-medium text-black">
              CPF (apenas números)
            </label>
            <input
              type="text"
              id="cpf"
              value={cpf}
              onChange={(e) => setCpf(e.target.value)}
              placeholder="xxxxxxxxxxx"
              className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
              required
            />
          </div>
          <div className="mb-4">
            <label htmlFor="password" className="block text-sm font-medium text-black">
              Senha
            </label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
              required
            />
          </div>
          <button
            type="submit"
            className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-amber-600 hover:bg-amber-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-amber-500"
          >
            Entrar
          </button>
        </form>
        <div>
          <br />
          Ou cadastre-se gratuitamente:{' '}
          <Link to="/cadastro" className="font-medium text-amber-600 hover:text-amber-500">
            Fazer cadastro
          </Link>
        </div>
      </main>

      <Footer />
    </div>
  );
};

export default LoginPage;
