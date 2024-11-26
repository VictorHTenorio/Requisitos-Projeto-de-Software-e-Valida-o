import React, { useState, useEffect } from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { X, AlertCircle, CheckCircle } from 'lucide-react';

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

const CouponRegistrationPage = () => {
  const [coupon, setCoupon] = useState({
    codigo: '',
    porcentagemDesconto: '',
    periodo: { inicio: '', fim: '' },
    categorias: [],
    produtos: []
  });
  const [category, setCategory] = useState('');
  const [product, setProduct] = useState('');
  const [availableCategories, setAvailableCategories] = useState([]);
  const [availableProducts, setAvailableProducts] = useState([]);
  const [alert, setAlert] = useState(null);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await fetch('http://localhost:8080/coolmeia/categorias');
        if (response.ok) {
          const categories = await response.json();
          console.log('Categorias carregadas:', categories);
          setAvailableCategories(categories);
        } else {
          throw new Error('Erro ao carregar categorias');
        }
      } catch (error) {
        console.error(error);
        setAlert({ type: 'error', message: 'Erro ao carregar categorias.' });
      }
    };

    const fetchProducts = async () => {
      try {
        const response = await fetch('http://localhost:8080/coolmeia/produtos/todos');
        if (response.ok) {
          const products = await response.json();
          console.log('Produtos carregados:', products);
          setAvailableProducts(products);
        } else {
          throw new Error('Erro ao carregar produtos');
        }
      } catch (error) {
        console.error(error);
        setAlert({ type: 'error', message: 'Erro ao carregar produtos.' });
      }
    };

    fetchCategories();
    fetchProducts();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCoupon((prevCoupon) => ({
      ...prevCoupon,
      [name]: value,
    }));
  };

  const handlePeriodChange = (e) => {
    const { name, value } = e.target;
    setCoupon((prevCoupon) => ({
      ...prevCoupon,
      periodo: { ...prevCoupon.periodo, [name]: value },
    }));
  };

  const handleCategoryAdd = () => {
    if (category) {
      const selectedCategory = availableCategories.find(
        (cat) => cat.id.id.toString() === category
      );
      if (selectedCategory && !coupon.categorias.some((cat) => cat.id === selectedCategory.id.id)) {
        setCoupon((prevCoupon) => ({
          ...prevCoupon,
          categorias: [...prevCoupon.categorias, { id: selectedCategory.id.id }]
        }));
      }
      setCategory('');
    }
  };

  const handleCategoryRemove = (categoryToRemove) => {
    setCoupon((prevCoupon) => ({
      ...prevCoupon,
      categorias: prevCoupon.categorias.filter((cat) => cat.id !== categoryToRemove.id),
    }));
  };

  const handleProductAdd = () => {
    if (product) {
      const selectedProduct = availableProducts.find(
        (prod) => prod.id.id.toString() === product
      );
      if (selectedProduct && !coupon.produtos.some((prod) => prod.id === selectedProduct.id.id)) {
        setCoupon((prevCoupon) => ({
          ...prevCoupon,
          produtos: [...prevCoupon.produtos, { id: selectedProduct.id.id }]
        }));
      }
      setProduct('');
    }
  };

  const handleProductRemove = (productToRemove) => {
    setCoupon((prevCoupon) => ({
      ...prevCoupon,
      produtos: prevCoupon.produtos.filter((prod) => prod.id !== productToRemove.id),
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Formatando as datas para o formato correto (YYYY-MM-DD)
    const formatDate = (dateString) => {
      const date = new Date(dateString);
      return date.toISOString().split('T')[0];
    };

    const payload = {
      codigo: coupon.codigo,
      porcentagemDesconto: parseInt(coupon.porcentagemDesconto),
      periodo: {
        inicio: formatDate(coupon.periodo.inicio),
        fim: formatDate(coupon.periodo.fim)
      },
      categorias: coupon.categorias.map(categoria => ({
        id: parseInt(categoria.id)
      })),
      produtos: coupon.produtos.map(produto => ({
        id: parseInt(produto.id)
      }))
    };

    try {
      console.log('Enviando payload:', payload);

      const response = await fetch('http://localhost:8080/coolmeia/cupons', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        setAlert({ type: 'success', message: 'Cupom cadastrado com sucesso!' });
        setCoupon({
          codigo: '',
          porcentagemDesconto: '',
          periodo: { inicio: '', fim: '' },
          categorias: [],
          produtos: [],
        });
      } else {
        const errorText = await response.text();
        console.error('Erro da API:', errorText);
        setAlert({ type: 'error', message: 'Erro ao cadastrar cupom. ' + errorText });
      }
    } catch (error) {
      console.error('Erro ao cadastrar cupom:', error);
      setAlert({ type: 'error', message: 'Erro ao cadastrar cupom. Verifique sua conexão.' });
    }
  };

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />
      <main className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-black mb-8">Cadastro de Cupom</h1>

        {alert && (
          <Alert
            type={alert.type}
            message={alert.message}
            onClose={() => setAlert(null)}
          />
        )}

        <form onSubmit={handleSubmit}>
          <div className="grid grid-cols-1 gap-6">
            <div>
              <label htmlFor="codigo" className="block text-sm font-medium text-black">
                Código
              </label>
              <input
                type="text"
                id="codigo"
                name="codigo"
                value={coupon.codigo}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label htmlFor="porcentagemDesconto" className="block text-sm font-medium text-black">
                Porcentagem de Desconto
              </label>
              <input
                type="number"
                id="porcentagemDesconto"
                name="porcentagemDesconto"
                value={coupon.porcentagemDesconto}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-black">Período</label>
              <div className="flex gap-4">
                <input
                  type="date"
                  name="inicio"
                  value={coupon.periodo.inicio}
                  onChange={handlePeriodChange}
                  className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                  required
                />
                <input
                  type="date"
                  name="fim"
                  value={coupon.periodo.fim}
                  onChange={handlePeriodChange}
                  className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                  required
                />
              </div>
            </div>
            <div>
              <label htmlFor="category" className="block text-sm font-medium text-black">
                Categorias
              </label>
              <div className="mt-1 flex">
                <select
                  id="category"
                  value={category}
                  onChange={(e) => setCategory(e.target.value)}
                  className="flex-grow rounded-none rounded-l-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                >
                  <option value="">Selecione uma categoria</option>
                  {availableCategories.map((cat) => (
                    <option key={cat.id.id} value={cat.id.id}>
                      {cat.nome}
                    </option>
                  ))}
                </select>
                <button
                  type="button"
                  onClick={handleCategoryAdd}
                  className="inline-flex items-center px-3 rounded-r-md border border-l-0 border-amber-400 bg-amber-50 text-black sm:text-sm"
                >
                  Adicionar
                </button>
              </div>
              <div className="mt-2 flex flex-wrap gap-2">
                {coupon.categorias.map((cat) => (
                  <div
                    key={cat.id}
                    className="inline-flex items-center px-2 py-1 rounded-md bg-amber-200 text-black text-sm"
                  >
                    {availableCategories.find((c) => c.id.id === cat.id)?.nome || `Categoria ${cat.id}`}
                    <button
                      type="button"
                      onClick={() => handleCategoryRemove(cat)}
                      className="ml-1 text-black hover:text-red-500"
                    >
                      <X className="h-4 w-4" />
                    </button>
                  </div>
                ))}
              </div>
            </div>
            <div>
              <label htmlFor="product" className="block text-sm font-medium text-black">
                Produtos
              </label>
              <div className="mt-1 flex">
                <select
                  id="product"
                  value={product}
                  onChange={(e) => setProduct(e.target.value)}
                  className="flex-grow rounded-none rounded-l-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                >
                  <option value="">Selecione um produto</option>
                  {availableProducts.map((prod) => (
                    <option key={prod.id.id} value={prod.id.id}>
                      {prod.nome}
                    </option>
                  ))}
                </select>
                <button
                  type="button"
                  onClick={handleProductAdd}
                  className="inline-flex items-center px-3 rounded-r-md border border-l-0 border-amber-400 bg-amber-50 text-black sm:text-sm"
                >
                  Adicionar
                </button>
              </div>
              <div className="mt-2 flex flex-wrap gap-2">
                {coupon.produtos.map((prod) => (
                  <div
                    key={prod.id}
                    className="inline-flex items-center px-2 py-1 rounded-md bg-amber-200 text-black text-sm"
                  >
                    {availableProducts.find((p) => p.id.id === prod.id)?.nome || `Produto ${prod.id}`}
                    <button
                      type="button"
                      onClick={() => handleProductRemove(prod)}
                      className="ml-1 text-black hover:text-red-500"
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

export default CouponRegistrationPage;
