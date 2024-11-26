import React, { useState, useMemo, useEffect } from 'react';
import { useSearchParams, useNavigate, Link } from 'react-router-dom';
import { ShoppingCart } from 'lucide-react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import ProductDetailsPopup from '../components/ProductDetailsPopup';
import { ChevronDown, ChevronLeft, ChevronRight, SlidersHorizontal } from 'lucide-react';

const AllProductsPage = () => {
  const [sortBy, setSortBy] = useState('newest');
  const [selectedCategory, setSelectedCategory] = useState(''); // Inicialmente sem categoria selecionada
  const [currentPage, setCurrentPage] = useState(1);
  const productsPerPage = 12;
  const [products, setProducts] = useState([]); // Garantir que produtos seja sempre um array vazio
  const [categories, setCategories] = useState([]);
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  // Carregar categorias da API
  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await fetch("http://127.0.0.1:8080/coolmeia/categorias");
        const data = await response.json();
        setCategories(data);
      } catch (error) {
        console.error("Erro ao carregar categorias:", error);
      }
    };

    fetchCategories();
  }, []);

  // Carregar produtos com base na categoria selecionada
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const categoryId = selectedCategory || searchParams.get('category'); // Use a categoria da URL se estiver presente
        const url = categoryId
          ? `http://127.0.0.1:8080/coolmeia/produtos/categoria/${categoryId}`
          : `http://127.0.0.1:8080/coolmeia/produtos`;
        const response = await fetch(url);
        const data = await response.json();

        // Verificar se a resposta é um array antes de setar
        if (Array.isArray(data)) {
          setProducts(data);
        } else {
          console.error("A resposta não é um array:", data);
        }
      } catch (error) {
        console.error("Erro ao carregar produtos:", error);
      }
    };

    fetchProducts();
  }, [selectedCategory, searchParams]);

  // Obter a categoria da URL para manter a seleção de filtro
  useEffect(() => {
    const category = searchParams.get('category');
    if (category) {
      setSelectedCategory(category); // Atualiza o estado com a categoria da URL
    }
  }, [searchParams]);

  // Filtrar produtos com base na categoria selecionada
  const filteredProducts = useMemo(() => {
    if (selectedCategory === '') {
      return products; // Se nenhuma categoria estiver selecionada, mostra todos os produtos
    }
    return products.filter(product =>
      product.categorias.some(category => category.id === parseInt(selectedCategory)) // Filtra os produtos pela categoria selecionada
    );
  }, [products, selectedCategory]);
  

  // Ordenar produtos com base no filtro de preço e outros critérios
  const sortedProducts = useMemo(() => {
    let sorted = [...filteredProducts];

    switch (sortBy) {
      case 'price-asc':
        sorted.sort((a, b) => a.valor - b.valor);
        break;
      case 'price-desc':
        sorted.sort((a, b) => b.valor - a.valor);
        break;
      case 'newest':
        sorted.sort((a, b) => new Date(b.created_at) - new Date(a.created_at));
        break;
      case 'name':
        sorted.sort((a, b) => a.nome.localeCompare(b.nome));
        break;
      default:
        break;
    }

    return sorted;
  }, [filteredProducts, sortBy]);

  // Calcular o número total de páginas
  const totalPages = useMemo(() => {
    return Math.ceil(sortedProducts.length / productsPerPage);
  }, [sortedProducts, productsPerPage]);

  // Obter os produtos da página atual
  const indexOfLastProduct = currentPage * productsPerPage;
  const indexOfFirstProduct = indexOfLastProduct - productsPerPage;
  const currentProducts = sortedProducts.slice(indexOfFirstProduct, indexOfLastProduct);

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
    const selectedCategory = e.target.value;
    setSelectedCategory(selectedCategory); // Atualiza o estado com a categoria selecionada
    setCurrentPage(1); // Reseta a página para 1 quando a categoria for alterada
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
  <option value="">Selecione a Categoria</option>
  {categories.map((category) => (
    <option key={category.id.id} value={category.id.id}>
      {category.nome}
    </option>
  ))}
</select>

              <ChevronDown className="absolute right-2 top-1/2 transform -translate-y-1/2 text-amber-600 w-5 h-5 pointer-events-none" />
            </div>
          </div>
        </div>

        {/* Grid de produtos */}
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
  {currentProducts.map((product) => (
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


        {/* Paginação */}
        <div className="flex justify-center mt-8 gap-2">
          <button
            className={`px-4 py-2 border rounded-lg text-black hover:bg-amber-400 hover:text-white transition-colors ${currentPage === 1 ? 'opacity-50 cursor-not-allowed' : ''}`}
            onClick={handlePrevPage}
            disabled={currentPage === 1}
          >
            <ChevronLeft />
          </button>
          {[...Array(totalPages).keys()].map(page => (
            <button
              key={page + 1}
              className={`px-4 py-2 border rounded-lg text-black hover:bg-amber-400 hover:text-white transition-colors ${currentPage === page + 1 ? 'bg-amber-400 text-white' : ''}`}
              onClick={() => handlePageClick(page + 1)}
            >
              {page + 1}
            </button>
          ))}
          <button
            className={`px-4 py-2 border rounded-lg text-black hover:bg-amber-400 hover:text-white transition-colors ${currentPage === totalPages ? 'opacity-50 cursor-not-allowed' : ''}`}
            onClick={handleNextPage}
            disabled={currentPage === totalPages}
          >
            <ChevronRight />
          </button>
        </div>
      </main>

      <Footer />
    </div>
  );
};

export default AllProductsPage;