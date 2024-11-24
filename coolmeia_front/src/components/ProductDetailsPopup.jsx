import React, { useState } from 'react';
import { useContext } from 'react';
import { CartContext } from '../CartContext';

const ProductDetailsPopup = ({ product }) => {
  const [showModal, setShowModal] = useState(false);
  const [selectedSize, setSelectedSize] = useState('');
  const [selectedColor, setSelectedColor] = useState('');

  const handleAddToCart = () => {
    // Você pode adicionar a lógica de adicionar ao carrinho aqui no futuro
    alert(`Adicionado ao carrinho: ${product.name} - Tamanho: ${selectedSize}, Cor: ${selectedColor}`);
    setShowModal(false);
  };

  return (
    <>
      <button
        className="bg-amber-400 text-white px-4 py-2 rounded-md hover:bg-amber-500 transition-colors"
        onClick={() => setShowModal(true)}
      >
        Ver detalhes (View Details)
      </button>

      {showModal && (
        <div className="fixed top-0 left-0 w-full h-full bg-gray-800 bg-opacity-50 flex items-center justify-center">
          <div className="bg-white p-6 rounded-lg shadow-lg max-w-md w-full">
            <h2 className="text-lg font-bold mb-4">{product.name}</h2>
            <img src={product.image} alt={product.name} className="w-full h-48 object-cover mb-4" />
            <p className="text-gray-600 mb-4">{product.description}</p>
            <p className="font-bold text-black text-lg mb-4">R$ {product.price.toFixed(2)}</p>
            <div className="mb-4">
              <label htmlFor="size" className="block font-medium mb-2">Tamanho (Size):</label>
              <select
                id="size"
                value={selectedSize}
                onChange={(e) => setSelectedSize(e.target.value)}
                className="border border-gray-300 rounded-md px-4 py-2 w-full"
              >
                <option value="">Selecione (Select)</option>
                <option value="P">P</option>
                <option value="M">M</option>
                <option value="G">G</option>
              </select>
            </div>
            <div className="mb-4">
              <label htmlFor="color" className="block font-medium mb-2">Cor (Color):</label>
              <select
                id="color"
                value={selectedColor}
                onChange={(e) => setSelectedColor(e.target.value)}
                className="border border-gray-300 rounded-md px-4 py-2 w-full"
              >
                <option value="">Selecione (Select)</option>
                <option value="preto">Preto (Black)</option>
                <option value="branco">Branco (White)</option>
                <option value="azul">Azul (Blue)</option>
              </select>
            </div>
            <div className="flex justify-end">
              <button
                className="bg-amber-400 text-white px-4 py-2 rounded-md hover:bg-amber-500 transition-colors mr-2"
                onClick={handleAddToCart}
              >
                Adicionar ao Carrinho (Add to Cart)
              </button>
              <button
                className="bg-gray-300 text-gray-700 px-4 py-2 rounded-md hover:bg-gray-400 transition-colors"
                onClick={() => setShowModal(false)}
              >
                Fechar (Close)
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default ProductDetailsPopup;