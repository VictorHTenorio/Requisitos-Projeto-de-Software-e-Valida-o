import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { ArrowLeft, ArrowRight } from 'lucide-react';
import axios from 'axios';
import ProductDetailsPopup from '../components/ProductDetailsPopup';

const NewProductsPage = () => {
  const navigate = useNavigate();
  const [productsData, setProductsData] = useState([]); // Todos os produtos
  const [categories, setCategories] = useState([]); // Categorias
  const [currentPage, setCurrentPage] = useState(1);
  const [loading, setLoading] = useState(true);
  const [animating, setAnimating] = useState(false); // Controla animações
  const PRODUCTS_PER_PAGE = 4;

  useEffect(() => {
    const fetchNewProducts = async () => {
      try {
        setLoading(true);
        const response = await axios.get('http://127.0.0.1:8080/coolmeia/produtos/novidades');
        const productIds = response.data.produtos.map((product) => product.id);

        const productRequests = productIds.map((id) =>
          axios.get(`http://127.0.0.1:8080/coolmeia/produtos/${id}`)
        );
        const productResponses = await Promise.all(productRequests);
        const products = productResponses.map((res) => res.data);

        setProductsData(products);
      } catch (error) {
        console.error('Erro ao carregar novos produtos:', error);
      } finally {
        setLoading(false);
      }
    };

    const fetchCategories = async () => {
      try {
        const response = await axios.get('http://127.0.0.1:8080/coolmeia/categorias');
        const categoriesData = response.data.map((category) => ({
          id: category.id.id,
          title: category.nome,
          description: `Explore produtos da categoria ${category.nome}.`,
          image: 'https://via.placeholder.com/400x200',
        }));
        setCategories(categoriesData);
      } catch (error) {
        console.error('Erro ao carregar categorias:', error);
      }
    };

    fetchNewProducts();
    fetchCategories();
  }, []);

  const handleCategoryClick = (category) => {
    // Navegar para a página de produtos com o filtro de categoria
    navigate(`/produtos?category=${category.id}`);
  };

  const handlePageChange = (page) => {
    if (page !== currentPage) {
      setAnimating(true); // Inicia animação
      setTimeout(() => {
        setCurrentPage(page);
        setAnimating(false); // Finaliza animação após troca
      }, 300); // Tempo deve coincidir com a duração da animação CSS
    }
  };

  const paginatedProducts = () => {
    const start = (currentPage - 1) * PRODUCTS_PER_PAGE;
    const end = start + PRODUCTS_PER_PAGE;
    return productsData.slice(start, end);
  };

  const totalPages = Math.ceil(productsData.length / PRODUCTS_PER_PAGE);

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />
      <main className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-black mb-8 text-center">Novos produtos</h1>

        {loading ? (
          <p className="text-center text-gray-600">Carregando...</p>
        ) : (
          <div>
            {/* Produtos com paginação */}
            <div className="relative mb-12">
              <button
                className="absolute left-0 top-1/2 transform -translate-y-1/2 bg-white rounded-full p-2 shadow-lg hover:bg-gray-200"
                onClick={() => handlePageChange(currentPage - 1)}
                disabled={currentPage === 1}
              >
                <ArrowLeft className="h-5 w-5 text-gray-700" />
              </button>
              <button
                className="absolute right-0 top-1/2 transform -translate-y-1/2 bg-white rounded-full p-2 shadow-lg hover:bg-gray-200"
                onClick={() => handlePageChange(currentPage + 1)}
                disabled={currentPage === totalPages}
              >
                <ArrowRight className="h-5 w-5 text-gray-700" />
              </button>

              <div
                className={`grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 transition-opacity duration-300 ${
                  animating ? 'opacity-0' : 'opacity-100'
                }`}
              >
                {paginatedProducts().map((product) => (
                  <div
                    key={product.id.id}
                    className="bg-white rounded-lg shadow-md overflow-hidden border-2 border-amber-400 hover:border-black transition-colors duration-300"
                  >
                    <img
                      src={product.image || 'https://via.placeholder.com/200'}
                      alt={product.nome}
                      className="w-full h-48 object-cover"
                    />
                    <div className="p-4">
                      <h3 className="font-medium text-black">{product.nome}</h3>
                      <p className="text-lg font-bold text-amber-600 mt-1">
                        R$ {product.valor.toFixed(2)}
                      </p>
                      <ProductDetailsPopup productId={product.id.id} />
                    </div>
                  </div>
                ))}
              </div>

              <div className="flex justify-center mt-4 space-x-2">
                {Array.from({ length: totalPages }).map((_, index) => (
                  <div
                    key={index}
                    className={`w-3 h-3 rounded-full cursor-pointer ${
                      currentPage === index + 1 ? 'bg-amber-500' : 'bg-gray-300'
                    }`}
                    onClick={() => handlePageChange(index + 1)}
                  />
                ))}
              </div>
            </div>

            {/* Categorias */}
            <div className="space-y-6 mt-12">
              {categories.map((category) => (
                <div
                  key={category.id}
                  className="bg-white rounded-lg shadow-md overflow-hidden border-l-4 border-amber-400 hover:border-l-black transition-colors duration-300 cursor-pointer"
                  onClick={() => handleCategoryClick(category)}
                >
                  <div className="p-4">
                    <div className="flex flex-col md:flex-row gap-6">
                      <img
                        src={category.image}
                        alt={category.title}
                        className="w-full md:w-1/3 h-48 object-cover rounded-lg"
                      />
                      <div className="flex-1 flex justify-between items-center">
                        <div>
                          <h2 className="text-xl font-bold text-black mb-2">{category.title}</h2>
                          <p className="text-gray-600">{category.description}</p>
                        </div>
                        <ArrowRight className="h-6 w-6 text-amber-600" />
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}
      </main>
      <Footer />
    </div>
  );
};

export default NewProductsPage;
