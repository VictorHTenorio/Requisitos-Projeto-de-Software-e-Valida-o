import React from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { AlertCircle } from 'lucide-react';

const WishlistPage = () => {
  // Dados simulados dos produtos na wishlist
  const wishlistItems = [
    { 
      id: 1, 
      name: "Camiseta Oversized", 
      price: 129.90, 
      image: "https://via.placeholder.com/200",
      lastPieces: true
    },
    { 
      id: 2, 
      name: "Calça Cargo", 
      price: 259.90, 
      image: "https://via.placeholder.com/200",
      lastPieces: false
    },
    { 
      id: 3, 
      name: "Moletom Graphic", 
      price: 199.90, 
      image: "https://via.placeholder.com/200",
      lastPieces: false
    }
  ];

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />

      <main className="max-w-7xl mx-auto px-4 py-8">
        {/* Título com ícone de menu */}
        <div className="flex items-center gap-4 mb-8">
          <div className="flex flex-col gap-1">
            <div className="w-6 h-0.5 bg-black"></div>
            <div className="w-6 h-0.5 bg-black"></div>
            <div className="w-6 h-0.5 bg-black"></div>
          </div>
          <h1 className="text-3xl font-bold text-black">Lista de Desejos</h1>
        </div>

        {/* Grid de produtos */}
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          {wishlistItems.map((item) => (
            <div key={item.id} className="bg-white rounded-lg shadow-md overflow-hidden border border-gray-200">
              {/* Container para imagem com posicionamento relativo */}
              <div className="p-4 relative">
                {/* Aviso de últimas peças sobreposto */}
                {item.lastPieces && (
                  <div className="absolute top-4 left-4 right-4 bg-amber-100/60 backdrop-blur-sm p-2 flex items-center gap-2 rounded-t-md">
                    <AlertCircle className="h-5 w-5 text-amber-600" />
                    <p className="text-sm text-amber-600 font-medium">Últimas peças desse produto!</p>
                  </div>
                )}
                <img
                  src={item.image}
                  alt={item.name}
                  className="w-full h-48 object-cover rounded-md"
                />
              </div>

              {/* Informações do produto */}
              <div className="p-4">
                <h3 className="font-medium text-black mb-2">{item.name}</h3>
                <p className="text-lg font-bold text-amber-600 mb-4">
                  R$ {item.price.toFixed(2)}
                </p>
                <button className="w-full bg-black text-amber-400 py-2 px-4 rounded-md hover:bg-gray-800 transition-colors duration-300">
                  Comprar
                </button>
              </div>
            </div>
          ))}

          {/* Card vazio para novos itens */}
          <div className="bg-white rounded-lg shadow-md overflow-hidden border-2 border-dashed border-gray-300 flex items-center justify-center h-[350px]">
            <p className="text-gray-400 text-center">
              Continue navegando para<br />adicionar mais itens
            </p>
          </div>
        </div>
      </main>

      <Footer />
    </div>
  );
};

export default WishlistPage;