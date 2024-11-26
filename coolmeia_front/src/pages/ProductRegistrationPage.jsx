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

const ProductRegistrationPage = () => {
  const initialProductState = {
    nome: '',
    descricao: '',
    valor: '',
    quantidade: '',
    cores: [],
    categorias: []
  };

  const [product, setProduct] = useState(initialProductState);
  const [category, setCategory] = useState('');
  const [color, setColor] = useState({ nome: '', hex: '' });
  const [availableCategories, setAvailableCategories] = useState([]);
  const [products, setProducts] = useState([]);
  const [alert, setAlert] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const fetchProducts = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/produtos/todos`);
      if (response.ok) {
        const data = await response.json();
        console.log('Produtos carregados:', data);
        setProducts(data);
      } else {
        throw new Error('Erro ao carregar produtos');
      }
    } catch (error) {
      console.error('Erro ao carregar produtos:', error);
      setAlert({
        type: 'error',
        message: 'Erro ao carregar produtos. Por favor, recarregue a página.'
      });
    }
  };

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await fetch(`${API_BASE_URL}/categorias`);
        if (response.ok) {
          const categories = await response.json();
          setAvailableCategories(categories);
        } else {
          throw new Error('Erro ao carregar categorias');
        }
      } catch (error) {
        console.error('Erro ao carregar categorias:', error);
        setAlert({
          type: 'error',
          message: 'Erro ao carregar categorias. Por favor, recarregue a página.'
        });
      }
    };

    fetchCategories();
    fetchProducts();
  }, []);

  const handleDeleteProduct = async (productId) => {
    setIsLoading(true);
    try {
      const response = await fetch(`${API_BASE_URL}/produtos/${productId}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        setAlert({
          type: 'success',
          message: 'Produto excluído com sucesso!'
        });
        await fetchProducts();
      } else {
        throw new Error('Erro ao excluir produto');
      }
    } catch (error) {
      console.error('Erro ao excluir produto:', error);
      setAlert({
        type: 'error',
        message: 'Erro ao excluir produto. Tente novamente.'
      });
    } finally {
      setIsLoading(false);
    }
  };

  const resetForm = () => {
    setProduct(initialProductState);
    setColor({ nome: '', hex: '' });
    setCategory('');
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProduct((prevProduct) => ({
      ...prevProduct,
      [name]: value,
    }));
  };
// ... continuação do componente ProductRegistrationPage

const handleCategoryChange = (e) => {
  setCategory(e.target.value);
};

const handleCategoryAdd = () => {
  if (category) {
    const selectedCategory = availableCategories.find(
      (cat) => cat.id.id.toString() === category
    );

    if (selectedCategory && !product.categorias.some((cat) => cat.id === selectedCategory.id.id)) {
      setProduct((prevProduct) => ({
        ...prevProduct,
        categorias: [...prevProduct.categorias, { id: selectedCategory.id.id }]
      }));
    }
    setCategory('');
  }
};

const handleCategoryRemove = (categoryToRemove) => {
  setProduct((prevProduct) => ({
    ...prevProduct,
    categorias: prevProduct.categorias.filter((cat) => cat.id !== categoryToRemove.id),
  }));
};

const handleColorNameChange = (e) => {
  setColor((prevColor) => ({
    ...prevColor,
    nome: e.target.value,
  }));
};

const handleColorHexChange = (e) => {
  setColor((prevColor) => ({
    ...prevColor,
    hex: e.target.value,
  }));
};

const handleColorAdd = () => {
  if (color.nome.trim() !== '' && color.hex.trim() !== '') {
    setProduct((prevProduct) => ({
      ...prevProduct,
      cores: [...prevProduct.cores, color],
    }));
    setColor({ nome: '', hex: '' });
  }
};

const handleColorRemove = (colorToRemove) => {
  setProduct((prevProduct) => ({
    ...prevProduct,
    cores: prevProduct.cores.filter((c) => c.nome !== colorToRemove.nome && c.hex !== colorToRemove.hex),
  }));
};

const handleSubmit = async (e) => {
  e.preventDefault();
  setIsLoading(true);

  const payload = {
    nome: product.nome,
    descricao: product.descricao,
    quantidade: parseInt(product.quantidade),
    valor: parseFloat(product.valor),
    cores: product.cores,
    categorias: product.categorias.map(categoria => ({ id: parseInt(categoria.id) }))
  };

  try {
    const response = await fetch(`${API_BASE_URL}/produtos`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify(payload)
    });

    if (response.ok) {
      const produtoSalvo = await response.json();
      console.log('Produto salvo:', produtoSalvo);
      setAlert({
        type: 'success',
        message: 'Produto cadastrado com sucesso!'
      });
      resetForm();
      await fetchProducts();
    } else {
      const error = await response.text();
      console.error('Erro da API:', error);
      setAlert({
        type: 'error',
        message: 'Erro ao cadastrar produto. Por favor, tente novamente.'
      });
    }
  } catch (error) {
    console.error('Erro de rede:', error);
    setAlert({
      type: 'error',
      message: 'Erro de conexão. Por favor, verifique sua internet e tente novamente.'
    });
  } finally {
    setIsLoading(false);
  }
};

return (
  <div className="min-h-screen bg-amber-50">
    <Header />
    <main className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold text-black mb-8">Cadastro de Produto</h1>

      {alert && (
        <Alert
          type={alert.type}
          message={alert.message}
          onClose={() => setAlert(null)}
        />
      )}

      <form onSubmit={handleSubmit} className="bg-white shadow-sm rounded-lg p-6 mb-8">
        <div className="grid grid-cols-1 gap-6">
          <div>
            <label htmlFor="nome" className="block text-sm font-medium text-black">
              Nome
            </label>
            <input
              type="text"
              id="nome"
              name="nome"
              value={product.nome}
              onChange={handleInputChange}
              className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
              required
              disabled={isLoading}
            />
          </div>
          <div>
            <label htmlFor="descricao" className="block text-sm font-medium text-black">
              Descrição
            </label>
            <textarea
              id="descricao"
              name="descricao"
              value={product.descricao}
              onChange={handleInputChange}
              className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
              required
              disabled={isLoading}
            />
          </div>
          <div>
            <label htmlFor="valor" className="block text-sm font-medium text-black">
              Preço
            </label>
            <input
              type="number"
              id="valor"
              name="valor"
              step="0.01"
              value={product.valor}
              onChange={handleInputChange}
              className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
              required
              disabled={isLoading}
            />
          </div>
          <div>
            <label htmlFor="quantidade" className="block text-sm font-medium text-black">
              Quantidade em Estoque
            </label>
            <input
              type="number"
              id="quantidade"
              name="quantidade"
              value={product.quantidade}
              onChange={handleInputChange}
              className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
              required
              disabled={isLoading}
            />
          </div>
          <div>
            <label htmlFor="category" className="block text-sm font-medium text-black">
              Categoria
            </label>
            <div className="mt-1 flex">
              <select
                id="category"
                name="category"
                value={category}
                onChange={handleCategoryChange}
                className="flex-grow rounded-none rounded-l-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                disabled={isLoading}
              >
                <option value="">Selecione uma categoria</option>
                {availableCategories.map(cat => (
                  <option key={cat.id.id} value={cat.id.id}>
                    {cat.nome}
                  </option>
                ))}
              </select>
              <button
                type="button"
                onClick={handleCategoryAdd}
                className="inline-flex items-center px-3 rounded-r-md border border-l-0 border-amber-400 bg-amber-50 text-black sm:text-sm"
                disabled={isLoading}
              >
                Adicionar
              </button>
            </div>
            <div className="mt-2 flex flex-wrap gap-2">
              {product.categorias.map((category) => (
                <div
                  key={category.id}
                  className="inline-flex items-center px-2 py-1 rounded-md bg-amber-200 text-black text-sm"
                >
                  {availableCategories.find((cat) => cat.id.id === category.id)?.nome || `Categoria ${category.id}`}
                  <button
                    type="button"
                    onClick={() => handleCategoryRemove(category)}
                    className="ml-1 text-black hover:text-red-500"
                    disabled={isLoading}
                  >
                    <X className="h-4 w-4" />
                  </button>
                </div>
              ))}
            </div>
          </div>
          <div>
            <label htmlFor="colorName" className="block text-sm font-medium text-black">
              Cor
            </label>
            <div className="mt-1 flex">
              <input
                type="text"
                id="colorName"
                name="colorName"
                value={color.nome}
                onChange={handleColorNameChange}
                className="flex-grow rounded-none rounded-l-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                placeholder="Nome da cor"
                disabled={isLoading}
              />
              <input
                type="color"
                id="colorHex"
                name="colorHex"
                value={color.hex}
                onChange={handleColorHexChange}
                className="w-10 h-10 rounded-none border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500"
                disabled={isLoading}
              />
              <button
                type="button"
                onClick={handleColorAdd}
                className="inline-flex items-center px-3 rounded-r-md border border-l-0 border-amber-400 bg-amber-50 text-black sm:text-sm"
                disabled={isLoading}
              >
                Adicionar
              </button>
            </div>
            <div className="mt-2 flex flex-wrap gap-2">
              {product.cores.map((cor, index) => (
                <div
                  key={index}
                  className="inline-flex items-center px-2 py-1 rounded-md bg-amber-200 text-black text-sm"
                >
                  <div
                    className="w-4 h-4 rounded-full mr-2"
                    style={{ backgroundColor: cor.hex }}
                  />
                  {cor.nome}
                  <button
                    type="button"
                    onClick={() => handleColorRemove(cor)}
                    className="ml-1 text-black hover:text-red-500"
                    disabled={isLoading}
                  >
                    <X className="h-4 w-4" />
                  </button>
                </div>
              ))}
            </div>
          </div>
        </div>

        <div className="mt-8">
          <button
            type="submit"
            disabled={isLoading}
            className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-amber-600 hover:bg-amber-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-amber-500 disabled:opacity-50"
          >
            {isLoading ? 'Salvando...' : 'Cadastrar'}
          </button>
        </div>
      </form>

      {/* Lista de Produtos */}
      <div className="mt-8">
        <h2 className="text-2xl font-bold text-black mb-4">Produtos Cadastrados</h2>
        <div className="bg-white shadow rounded-lg overflow-hidden">
          <table className="min-w-full divide-y divide-amber-200">
            <thead className="bg-amber-50">
              <tr>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-amber-800 uppercase tracking-wider">
                  Nome
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-amber-800 uppercase tracking-wider">
                  Preço
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-amber-800 uppercase tracking-wider">
                  Estoque
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-amber-800 uppercase tracking-wider">
                  Categorias
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-amber-800 uppercase tracking-wider">
                  Cores
                </th>
                <th scope="col" className="px-6 py-3 text-right text-xs font-medium text-amber-800 uppercase tracking-wider">
                  Ações
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-amber-200">
              {products.map((product) => (
                <tr key={product.id}>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {product.nome}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    R$ {product.valor.toFixed(2)}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {product.quantidade}
                  </td>
                  <td className="px-6 py-4 text-sm text-gray-900">
                    <div className="flex flex-wrap gap-1">
                      {product.categorias?.map((categoria) => {
                        // Aqui a categoria tem apenas { id: 17 }
                        const categoryName = availableCategories.find(
                          cat => cat.id.id === categoria.id
                        )?.nome;
                        
                        return (
                          <span
                            key={categoria.id}
                            className="inline-flex items-center px-2 py-0.5 rounded bg-amber-100 text-amber-800 text-xs"
                          >
                            {categoryName || 'Categoria'}
                          </span>
                        );
                      })}
                    </div>
                  </td>
                  <td className="px-6 py-4 text-sm text-gray-900">
                    <div className="flex flex-wrap gap-1">
                      {product.cores?.map((cor, index) => (
                        <div
                          key={index}
                          className="inline-flex items-center px-2 py-0.5 rounded text-xs"
                          style={{ backgroundColor: cor.hex }}
                        >
                          {cor.nome}
                        </div>
                      ))}
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                    <button
                      onClick={() => handleDeleteProduct(product.id)}
                      disabled={isLoading}
                      className="text-red-600 hover:text-red-900 disabled:opacity-50"
                    >
                      <X className="h-5 w-5" />
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </main>
    <Footer />
  </div>
);
};

export default ProductRegistrationPage;