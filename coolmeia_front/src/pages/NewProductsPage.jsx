import React from 'react';
import { useNavigate} from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { ArrowRight } from 'lucide-react';

const NewProductsPage = () => {
  const navigate = useNavigate();

  const newProducts = [
    { id: 1, name: "Camiseta Oversized", price: 129.90, image: "https://via.placeholder.com/200" },
    { id: 2, name: "Calça Cargo", price: 259.90, image: "https://via.placeholder.com/200" },
    { id: 3, name: "Moletom Graphic", price: 199.90, image: "https://via.placeholder.com/200" },
    { id: 4, name: "Tênis Urban", price: 399.90, image: "https://via.placeholder.com/200" },
  ];

  const categories = [
    {
      id: 1,
      title: "Roupas",
      description: "Explore nossa coleção de roupas street wear.",
      image: "https://via.placeholder.com/400x200"
    },
    {
      id: 2,
      title: "Sapatos",
      description: "Tênis e calçados para seu estilo urbano.",
      image: "https://via.placeholder.com/400x200"
    },
    {
      id: 3,
      title: "Ver todos",
      description: "Confira todos os nossos produtos disponíveis.",
      image: "https://via.placeholder.com/400x200"
    }
  ];

  const handleCategoryClick = (category) => {
    if (category.title === "Sapatos") {
      navigate("/produtos?category=sapatos");
    } else if (category.title === "Roupas") {
      navigate("/produtos?category=roupas");
    } else if (category.title === "Ver todos") {
      navigate("/produtos");
    } else {
      navigate(`/${category.title.toLowerCase()}`);
    }
  };

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-black mb-8 text-center">Novos produtos</h1>

        {/* Products Grid */}
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 mb-12">
          {newProducts.map((product) => (
            <div key={product.id} className="bg-white rounded-lg shadow-md overflow-hidden border-2 border-amber-400 hover:border-black transition-colors duration-300">
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
              </div>
            </div>
          ))}
        </div>

        {/* Categories Section */}
        <div className="space-y-6">
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
      </main>

      <Footer />
    </div>
  );
};

export default NewProductsPage;