import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { Heart, ShoppingCart } from 'lucide-react';

const ProductDetailsPopup = ({ productId }) => {
  const [showModal, setShowModal] = useState(false);
  const [productDetails, setProductDetails] = useState(null);
  const [loading, setLoading] = useState(false);
  const [isInWishlist, setIsInWishlist] = useState(false);
  const [quantidade, setQuantidade] = useState(1);
  const [addingToCart, setAddingToCart] = useState(false);
  const [error, setError] = useState(null);
  const { user } = useAuth();
  const navigate = useNavigate();

  // Função para carregar os detalhes do produto
  const fetchProductDetails = async () => {
    try {
      setLoading(true);
      const response = await fetch(`http://127.0.0.1:8080/coolmeia/produtos/${productId}`);
      const data = await response.json();
      setProductDetails(data);
      
      // Se usuário estiver logado, verifica se o produto está na lista de desejos
      if (user) {
        checkWishlistStatus();
      }
    } catch (error) {
      console.error('Erro ao carregar detalhes do produto:', error);
      setError('Erro ao carregar detalhes do produto');
    } finally {
      setLoading(false);
    }
  };

  // Verifica se o produto está na lista de desejos do usuário
  const checkWishlistStatus = async () => {
    try {
      const response = await fetch(`http://127.0.0.1:8080/coolmeia/clientes/${user.cpf}/lista-desejos`);
      const wishlist = await response.json();
      setIsInWishlist(wishlist.some(item => item.id === productId));
    } catch (error) {
      console.error('Erro ao verificar lista de desejos:', error);
    }
  };

  // Toggle produto na lista de desejos
  const toggleWishlist = async () => {
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
        setIsInWishlist(!isInWishlist);
      }
    } catch (error) {
      console.error('Erro ao atualizar lista de desejos:', error);
      setError('Erro ao atualizar lista de desejos');
    }
  };

  const handleAddToCart = async () => {
    if (!user) {
      navigate('/login');
      return;
    }

    try {
      setAddingToCart(true);
      setError(null);
      
      // Primeiro busca os dados do cliente para pegar o ID do carrinho
      const clienteResponse = await fetch(`http://127.0.0.1:8080/coolmeia/clientes/${user.cpf}`);
      if (!clienteResponse.ok) throw new Error('Erro ao buscar dados do cliente');
      const clienteData = await clienteResponse.json();

      // Adiciona o item ao carrinho
      const response = await fetch(`http://127.0.0.1:8080/coolmeia/carrinhos/${clienteData.carrinhoId.id}/itens`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          produtoId: productId,
          quantidade: quantidade,
          valorUnitario: productDetails.valor
        }),
      });

      if (!response.ok) throw new Error('Erro ao adicionar ao carrinho');
      
      // Fecha o modal após adicionar com sucesso
      setShowModal(false);
      
      // Opcional: redirecionar para o carrinho
      // navigate('/carrinho');
    } catch (error) {
      console.error('Erro ao adicionar ao carrinho:', error);
      setError('Erro ao adicionar produto ao carrinho');
    } finally {
      setAddingToCart(false);
    }
  };

  // Abrir o modal e carregar os detalhes do produto
  const handleOpenModal = () => {
    setShowModal(true);
    setQuantidade(1); // Reset quantidade
    setError(null); // Reset erro
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
        <div className="fixed top-0 left-0 w-full h-full bg-gray-800 bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white p-6 rounded-lg shadow-lg max-w-md w-full relative z-60">
            {loading ? (
              <p className="text-center py-4">Carregando...</p>
            ) : productDetails ? (
              <>
                <div className="flex justify-between items-start mb-4">
                  <h2 className="text-lg font-bold">{productDetails.nome}</h2>
                  {user && (
                    <button
                      onClick={toggleWishlist}
                      className="p-2 hover:bg-gray-100 rounded-full transition-colors"
                    >
                      <Heart 
                        className={`w-6 h-6 ${isInWishlist ? 'text-red-500 fill-current' : 'text-gray-400'}`} 
                      />
                    </button>
                  )}
                </div>
                <img
                  src={productDetails.image || 'https://via.placeholder.com/200'}
                  alt={productDetails.nome}
                  className="w-full h-48 object-cover mb-4 rounded-lg"
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
                  <ul className="flex gap-2 mt-2">
                    {productDetails.cores.map((color, index) => (
                      <li
                        key={index}
                        className="w-6 h-6 rounded-full border border-gray-300"
                        style={{ backgroundColor: color.hex }}
                      />
                    ))}
                  </ul>
                </div>

                {error && (
                  <div className="mb-4 p-3 bg-red-100 border border-red-400 text-red-700 rounded-md">
                    {error}
                  </div>
                )}
                
                {/* Seção de quantidade e botão de adicionar ao carrinho */}
                <div className="mt-6 space-y-4">
                  <div className="flex items-center justify-between">
                    <label className="text-sm font-medium text-gray-700">Quantidade:</label>
                    <div className="flex items-center border border-amber-400 rounded">
                      <button 
                        className="px-3 py-1 hover:bg-amber-100 text-black disabled:opacity-50"
                        onClick={() => setQuantidade(q => Math.max(1, q - 1))}
                        disabled={quantidade <= 1}
                      >
                        -
                      </button>
                      <span className="px-4 text-black">{quantidade}</span>
                      <button 
                        className="px-3 py-1 hover:bg-amber-100 text-black disabled:opacity-50"
                        onClick={() => setQuantidade(q => Math.min(productDetails.quantidade, q + 1))}
                        disabled={quantidade >= productDetails.quantidade}
                      >
                        +
                      </button>
                    </div>
                  </div>

                  <div className="flex justify-between gap-2">
                    <button
                      className="flex-1 bg-gray-300 text-gray-700 px-4 py-2 rounded-md hover:bg-gray-400 transition-colors"
                      onClick={() => setShowModal(false)}
                    >
                      Fechar
                    </button>
                    <button
                      className="flex-1 bg-amber-600 text-white px-4 py-2 rounded-md hover:bg-amber-700 transition-colors disabled:opacity-70"
                      onClick={handleAddToCart}
                      disabled={addingToCart || quantidade > productDetails.quantidade}
                    >
                      <div className="flex items-center justify-center gap-2">
                        <ShoppingCart className="w-5 h-5" />
                        {addingToCart ? 'Adicionando...' : 'Adicionar'}
                      </div>
                    </button>
                  </div>
                </div>
              </>
            ) : (
              <p className="text-center py-4 text-red-600">Erro ao carregar os detalhes do produto.</p>
            )}
          </div>
        </div>
      )}
    </>
  );
};

export default ProductDetailsPopup;