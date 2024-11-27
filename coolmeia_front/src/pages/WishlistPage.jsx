import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { AlertCircle, X } from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';
import ProductDetailsPopup from '../components/ProductDetailsPopup';

const WishlistPage = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [wishlistItems, setWishlistItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!user) {
      navigate('/login');
      return;
    }

    fetchWishlist();
  }, [user, navigate]);

  const fetchWishlist = async () => {
    try {
      const wishlistResponse = await fetch(`http://127.0.0.1:8080/coolmeia/clientes/${user.cpf}/lista-desejos`);
      const wishlistIds = await wishlistResponse.json();

      const productsPromises = wishlistIds.map(async (productId) => {
        const productResponse = await fetch(`http://127.0.0.1:8080/coolmeia/produtos/${productId.id}`);
        return productResponse.json();
      });

      const products = await Promise.all(productsPromises);
      setWishlistItems(products);
    } catch (error) {
      console.error('Erro ao carregar lista de desejos:', error);
    } finally {
      setLoading(false);
    }
  };

  const removeFromWishlist = async (productId) => {
    try {
      const response = await fetch(
        `http://127.0.0.1:8080/coolmeia/clientes/${user.cpf}/lista-desejos/${productId}`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
        }
      );

      if (response.ok) {
        fetchWishlist();
      }
    } catch (error) {
      console.error('Erro ao remover item da lista de desejos:', error);
    }
  };

  if (!user) {
    return null;
  }

  if (loading) {
    return (
      <div className="min-h-screen bg-amber-50">
        <Header />
        <main className="max-w-7xl mx-auto px-4 py-8">
          <div className="flex justify-center items-center h-64">
            <p className="text-lg">Carregando sua lista de desejos...</p>
          </div>
        </main>
        <Footer />
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />

      <main className="max-w-7xl mx-auto px-4 py-8">
        <div className="flex justify-between items-center mb-8">
          <div className="flex items-center gap-4">
            <h1 className="text-3xl font-bold text-black">Lista de Desejos</h1>
          </div>
          <p className="text-gray-600">
            {wishlistItems.length} {wishlistItems.length === 1 ? 'item' : 'itens'}
          </p>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
          {wishlistItems.map((item) => (
            <div
              key={item.id.id}
              className="bg-white rounded-lg shadow-md overflow-hidden border-2 border-amber-400 hover:border-black transition-colors duration-300 group relative"
            >
              {/* Botão de remover no canto superior direito */}
              <button
                onClick={() => removeFromWishlist(item.id.id)}
                className="absolute top-2 right-2 p-1.5 bg-white/80 hover:bg-red-50 rounded-full 
                          transition-colors duration-300 opacity-0 group-hover:opacity-100 z-20"
                title="Remover da lista de desejos"
              >
                <X className="w-4 h-4 text-red-500" />
              </button>

              <div className="relative">
                {item.quantidade < 10 && (
                  <div className="absolute top-2 left-2 right-10 bg-amber-100/60 backdrop-blur-sm p-2 flex items-center gap-2 rounded-md z-10">
                    <AlertCircle className="h-5 w-5 text-amber-600" />
                    <p className="text-sm text-amber-600 font-medium">
                      Últimas {item.quantidade} peças!
                    </p>
                  </div>
                )}
                <img
                  src={item.image || 'https://via.placeholder.com/200'}
                  alt={item.nome}
                  className="w-full h-48 object-cover"
                />
              </div>

              <div className="p-4">
                <h3 className="font-medium text-black">{item.nome}</h3>
                <p className="text-lg font-bold text-amber-600 mt-1">
                  R$ {item.valor.toFixed(2)}
                </p>
                <div className="mt-4">
                  <ProductDetailsPopup productId={item.id.id} />
                </div>
              </div>
            </div>
          ))}

          {wishlistItems.length > 0 && (
            <div className="bg-white rounded-lg shadow-md overflow-hidden border-2 border-dashed border-gray-300 flex items-center justify-center h-[350px]">
              <div className="text-center">
                <p className="text-gray-400 px-4">
                  Continue navegando para<br />adicionar mais itens
                </p>
                <button
                  onClick={() => navigate('/')}
                  className="mt-4 text-amber-600 hover:text-amber-700 font-medium"
                >
                  Ver produtos
                </button>
              </div>
            </div>
          )}
        </div>

        {wishlistItems.length === 0 && (
          <div className="text-center py-16">
            <p className="text-gray-500 mb-4">Sua lista de desejos está vazia</p>
            <button
              onClick={() => navigate('/')}
              className="bg-amber-400 text-white px-6 py-2 rounded-md hover:bg-amber-500 transition-colors"
            >
              Explorar produtos
            </button>
          </div>
        )}
      </main>

      <Footer />
    </div>
  );
};

export default WishlistPage;