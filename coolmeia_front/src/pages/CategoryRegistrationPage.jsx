import React, { useState, useEffect } from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { X, AlertCircle, CheckCircle } from 'lucide-react';

const API_BASE_URL = 'http://127.0.0.1:8080/coolmeia';

const Alert = ({ type, message, onClose }) => {
  const bgColor = type === 'success' ? 'bg-green-100' : 'bg-red-100';
  const textColor = type === 'success' ? 'text-green-800' : 'text-red-800';
  const Icon = type === 'success' ? CheckCircle : AlertCircle;

  return (
    <div className={`rounded-md ${bgColor} p-4 mb-4`}>
      <div className="flex">
        <div className="flex-shrink-0">
          <Icon className={`h-5 w-5 ${textColor}`} aria-hidden="true" />
        </div>
        <div className="ml-3">
          <p className={`text-sm font-medium ${textColor}`}>{message}</p>
        </div>
        <div className="ml-auto pl-3">
          <button
            type="button"
            onClick={onClose}
            className={`inline-flex rounded-md p-1.5 ${textColor} hover:bg-green-200 focus:outline-none`}
          >
            <X className="h-5 w-5" aria-hidden="true" />
          </button>
        </div>
      </div>
    </div>
  );
};

const CategoryRegistrationPage = () => {
  const [categoryName, setCategoryName] = useState('');
  const [categories, setCategories] = useState([]);
  const [alert, setAlert] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const fetchCategories = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/categorias`);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      setCategories(data);
    } catch (error) {
      console.error('Erro ao carregar categorias:', error);
      setAlert({ type: 'error', message: 'Erro ao carregar categorias. Por favor, tente novamente.' });
    }
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      const response = await fetch(`${API_BASE_URL}/categorias`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nome: categoryName }),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const newCategory = await response.json();
      setCategories([...categories, newCategory]);
      setAlert({ type: 'success', message: 'Categoria cadastrada com sucesso!' });
      setCategoryName('');
    } catch (error) {
      console.error('Erro ao cadastrar categoria:', error);
      setAlert({ 
        type: 'error', 
        message: 'Erro ao cadastrar categoria. Verifique os dados e tente novamente.' 
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleDelete = async (categoryId) => {
    setIsLoading(true);
    try {
      // Extrai o ID numérico do objeto aninhado
      const idToDelete = categoryId.id;
      
      const response = await fetch(`${API_BASE_URL}/categorias/${idToDelete}`, {
        method: 'DELETE',
      });

      if (!response.ok) {
        const errorData = await response.text();
        throw new Error(errorData || `HTTP error! status: ${response.status}`);
      }

      setCategories(categories.filter((category) => category.id.id !== idToDelete));
      setAlert({ type: 'success', message: 'Categoria excluída com sucesso!' });
      
      // Atualiza a lista após deletar
      await fetchCategories();
    } catch (error) {
      console.error('Erro ao excluir categoria:', error);
      setAlert({ 
        type: 'error', 
        message: 'Erro ao excluir categoria. Tente novamente.' 
      });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />
      <main className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-black mb-8">Cadastro de Categorias</h1>

        {alert && (
          <Alert
            type={alert.type}
            message={alert.message}
            onClose={() => setAlert(null)}
          />
        )}

        <form onSubmit={handleSubmit} className="mb-8">
          <div className="grid grid-cols-1 gap-6">
            <div>
              <label htmlFor="categoryName" className="block text-sm font-medium text-black">
                Nome da Categoria
              </label>
              <input
                type="text"
                id="categoryName"
                name="categoryName"
                value={categoryName}
                onChange={(e) => setCategoryName(e.target.value)}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
                disabled={isLoading}
              />
            </div>
          </div>
          <button
            type="submit"
            className="mt-4 inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-amber-600 hover:bg-amber-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-amber-500 disabled:opacity-50"
            disabled={isLoading}
          >
            {isLoading ? 'Processando...' : 'Cadastrar Categoria'}
          </button>
        </form>

        <h2 className="text-2xl font-bold text-black mb-4">Categorias Cadastradas</h2>
        <ul className="space-y-4">
          {categories.map((category) => (
            <li key={category.id.id} className="flex items-center justify-between bg-amber-200 px-4 py-2 rounded-md">
              <span>{category.nome}</span>
              <button
                onClick={() => handleDelete(category.id)}
                className="text-red-600 hover:text-red-800 focus:outline-none disabled:opacity-50"
                disabled={isLoading}
              >
                <X className="h-5 w-5" />
              </button>
            </li>
          ))}
        </ul>
      </main>
      <Footer />
    </div>
  );
};

export default CategoryRegistrationPage;