import React, { useState, useEffect } from 'react';

const ProductDetailsPopup = ({ productId }) => {
  const [showModal, setShowModal] = useState(false);
  const [productDetails, setProductDetails] = useState(null); // Detalhes do produto
  const [loading, setLoading] = useState(false);

  // Função para carregar os detalhes do produto
  const fetchProductDetails = async () => {
    try {
      setLoading(true);
      const response = await fetch(`http://127.0.0.1:8080/coolmeia/produtos/${productId}`);
      const data = await response.json();
      setProductDetails(data);
    } catch (error) {
      console.error('Erro ao carregar detalhes do produto:', error);
    } finally {
      setLoading(false);
    }
  };

  // Abrir o modal e carregar os detalhes do produto
  const handleOpenModal = () => {
    setShowModal(true);
    fetchProductDetails();
  };

  return (
    <>
      <button
        className="bg-amber-400 text-white px-4 py-2 rounded-md hover:bg-amber-500 transition-colors"
        onClick={handleOpenModal}
      >
        Ver detalhes
      </button>

      {showModal && (
        <div
          className="fixed top-0 left-0 w-full h-full bg-gray-800 bg-opacity-50 flex items-center justify-center z-50"
        >
          <div className="bg-white p-6 rounded-lg shadow-lg max-w-md w-full relative z-60">
            {loading ? (
              <p>Carregando...</p>
            ) : productDetails ? (
              <>
                <h2 className="text-lg font-bold mb-4">{productDetails.nome}</h2>
                <img
                  src={productDetails.image || 'https://via.placeholder.com/200'}
                  alt={productDetails.nome}
                  className="w-full h-48 object-cover mb-4"
                />
                <p className="text-gray-600 mb-4">{productDetails.descricao}</p>
                <p className="font-bold text-black text-lg mb-4">
                  R$ {productDetails.valor.toFixed(2)}
                </p>
                <p className="text-sm text-gray-500 mb-2">
                  Quantidade disponível: {productDetails.quantidade}
                </p>
                <div className="mb-4">
                  <strong>Cores disponíveis:</strong>
                  <ul className="flex gap-2">
                    {productDetails.cores.map((color, index) => (
                      <li
                        key={index}
                        className="w-6 h-6 rounded-full border border-gray-300"
                        style={{ backgroundColor: color.hex }}
                      >
                        {/* Espaço reservado para a cor */}
                      </li>
                    ))}
                  </ul>
                </div>
                <div className="flex justify-end">
                  <button
                    className="bg-gray-300 text-gray-700 px-4 py-2 rounded-md hover:bg-gray-400 transition-colors mr-2"
                    onClick={() => setShowModal(false)}
                  >
                    Fechar
                  </button>
                </div>
              </>
            ) : (
              <p>Erro ao carregar os detalhes do produto.</p>
            )}
          </div>
        </div>
      )}
    </>
  );
};

export default ProductDetailsPopup;
