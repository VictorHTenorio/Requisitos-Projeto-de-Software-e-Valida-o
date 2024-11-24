import React, { useState, useMemo, useEffect } from 'react';
import { useSearchParams, useNavigate, Link } from 'react-router-dom';
import { ShoppingCart } from 'lucide-react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import ProductDetailsPopup from '../components/ProductDetailsPopup';
import { ChevronDown, ChevronLeft, ChevronRight, SlidersHorizontal } from 'lucide-react';

const AllProductsPage = () => {
  const [sortBy, setSortBy] = useState('newest');
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [currentPage, setCurrentPage] = useState(1);
  const productsPerPage = 12;
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  // Dados simulados dos produtos - depois virão da API
  const products = [
    { id: 1, name: "Camiseta Oversized", price: 129.90, image: "https://via.placeholder.com/200", category: "roupas" },
    { id: 2, name: "Calça Cargo", price: 259.90, image: "https://via.placeholder.com/200", category: "roupas" },
    { id: 3, name: "Moletom Graphic", price: 199.90, image: "https://via.placeholder.com/200", category: "roupas" },
    { id: 4, name: "Tênis Urban", price: 399.90, image: "https://via.placeholder.com/200", category: "sapatos" },
    { id: 5, name: "Camiseta Basic", price: 89.90, image: "https://via.placeholder.com/200", category: "roupas" },
    { id: 6, name: "Jaqueta Corta-Vento", price: 299.90, image: "https://via.placeholder.com/200", category: "roupas" },
    { id: 7, name: "Tênis Street", price: 359.90, image: "https://via.placeholder.com/200", category: "sapatos" },
    { id: 8, name: "Calça Wide", price: 229.90, image: "https://via.placeholder.com/200", category: "roupas" },
    { id: 9, name: "Camiseta Basic", price: 89.90, image: "https://via.placeholder.com/200", category: "roupas" },
    { id: 10, name: "Jaqueta Corta-Vento", price: 299.90, image: "https://via.placeholder.com/200", category: "roupas" },
    { id: 11, name: "Tênis Street", price: 359.90, image: "https://via.placeholder.com/200", category: "sapatos" },
    { id: 12, name: "Calça Wide", price: 229.90, image: "https://via.placeholder.com/200", category: "roupas" },
  ];

  // Obter a categoria da URL
  useEffect(() => {
    const category = searchParams.get('category');
    if (category) {
      setSelectedCategory(category);
    } else {
      setSelectedCategory('all');
    }
  }, [searchParams]);

  // Filtrar produtos por categoria
  const filteredProducts = useMemo(() => {
    return selectedCategory === 'all'
      ? products
      : products.filter(product => product.category === selectedCategory);
  }, [products, selectedCategory]);

  // Calcular o número total de páginas
  const totalPages = useMemo(() => {
    return Math.ceil(filteredProducts.length / productsPerPage);
  }, [filteredProducts, productsPerPage]);

  // Obter os produtos da página atual
  const indexOfLastProduct = currentPage * productsPerPage;
  const indexOfFirstProduct = indexOfLastProduct - productsPerPage;
  const currentProducts = filteredProducts.slice(indexOfFirstProduct, indexOfLastProduct);

  // Funções de navegação de página
  const handlePrevPage = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };

  const handlePageClick = (page) => {
    setCurrentPage(page);
  };

  const handleCategoryChange = (e) => {
    setSelectedCategory(e.target.value);
    setCurrentPage(1); // Reseta a página para a primeira quando a categoria mudar
  };

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />

      <main className="max-w-7xl mx-auto px-4 py-8">
        {/* Cabeçalho com título e filtros */}
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-black">Coolmeia</h1>
          
          <div className="flex gap-4">
            {/* Dropdown de ordenação */}
            <div className="relative">
              <select 
                className="appearance-none bg-white px-4 py-2 pr-8 rounded-lg border border-amber-400 focus:outline-none focus:ring-2 focus:ring-amber-400"
                value={sortBy}
                onChange={(e) => setSortBy(e.target.value)}
              >
                <option value="newest">Mais Recentes</option>
                <option value="price-asc">Menor Preço</option>
                <option value="price-desc">Maior Preço</option>
                <option value="name">Nome</option>
              </select>
              <ChevronDown className="absolute right-2 top-1/2 transform -translate-y-1/2 text-amber-600 w-5 h-5 pointer-events-none" />
            </div>

            {/* Dropdown de filtro por categoria */}
            <div className="relative">
              <select
                className="appearance-none bg-white px-4 py-2 pr-8 rounded-lg border border-amber-400 focus:outline-none focus:ring-2 focus:ring-amber-400"
                value={selectedCategory}
                onChange={handleCategoryChange}
              >
                <option value="all">Todas as Categorias</option>
                <option value="roupas">Roupas</option>
                <option value="sapatos">Sapatos</option>
              </select>
              <ChevronDown className="absolute right-2 top-1/2 transform -translate-y-1/2 text-amber-600 w-5 h-5 pointer-events-none" />
            </div>
            
          </div>
        </div>

        {/* Grid de produtos */}
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
          {currentProducts.map((product) => (
            <div 
              key={product.id} 
              className="bg-white rounded-lg shadow-md overflow-hidden border-2 border-amber-400 hover:border-black transition-colors duration-300"
            >
              <img
                src={product.image}
                alt={product.name}
                className="w-full h-48 object-cover"
              />
              <div className="p-4">
                <h3 className="font-medium text-black">{product.name}</h3>
                <p className="text-lg font-bold text-amber-600 mt-1">
                  R$ {product.price.toFixed(2)}
                </p>
                <ProductDetailsPopup product={product} />
              </div>
            </div>
          ))}
        </div>

        {/* Paginação */}
        <div className="flex justify-center mt-8 gap-2">
          <button
            className={`px-4 py-2 border rounded-lg text-black hover:bg-amber-400 hover:text-white transition-colors ${currentPage === 1 ? 'border-amber-400 cursor-not-allowed' : 'border-amber-400'}`}
            onClick={handlePrevPage}
            disabled={currentPage === 1}
          >
            <ChevronLeft className="w-5 h-5" />
          </button>
          {Array.from({ length: totalPages }, (_, index) => index + 1).map((page) => (
            <button
              key={page}
              className={`px-4 py-2 rounded-lg ${currentPage === page ? 'bg-black text-amber-400' : 'border border-amber-400 text-black hover:bg-amber-400 hover:text-white transition-colors'}`}
              onClick={() => handlePageClick(page)}
            >
              {page}
            </button>
          ))}
          <button
            className={`px-4 py-2 border rounded-lg text-black hover:bg-amber-400 hover:text-white transition-colors ${currentPage === totalPages ? 'border-amber-400 cursor-not-allowed' : 'border-amber-400'}`}
            onClick={handleNextPage}
            disabled={currentPage === totalPages}
          >
            <ChevronRight className="w-5 h-5" />
          </button>
        </div>
      </main>

      <Footer />
    </div>
  );
};

export default AllProductsPage;