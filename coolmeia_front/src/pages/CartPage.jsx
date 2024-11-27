import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { ShoppingCart } from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { CartItemList } from '../components/cart/CartItemList';
import { AddressForm } from '../components/cart/AddressForm';
import { PaymentForm } from '../components/cart/PaymentForm';
import { OrderSummary } from '../components/cart/OrderSummary';

const CartPage = () => {
  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activeStep, setActiveStep] = useState('cart');
  const [clientData, setClientData] = useState(null);
  
  // Estados para cupom
  const [couponCode, setCouponCode] = useState('');
  
  // Estados para endereço
  const [cep, setCep] = useState('');
  const [number, setNumber] = useState('');
  const [addressInfo, setAddressInfo] = useState(null);

  // Estados para cartão
  const [cardData, setCardData] = useState({
    name: '',
    number: '',
    validity: '',
    cvv: ''
  });

  const { user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!user) {
      navigate('/login');
      return;
    }
    fetchClientAndCart();
  }, [user]);

  const fetchClientAndCart = async () => {
    try {
      setLoading(true);
      // Primeiro busca os dados do cliente
      const clientResponse = await fetch(`http://127.0.0.1:8080/coolmeia/clientes/${user.cpf}`);
      if (!clientResponse.ok) throw new Error('Erro ao carregar dados do cliente');
      const clientData = await clientResponse.json();
      setClientData(clientData);
  
      // Depois busca o carrinho usando o ID do carrinho do cliente
      const cartResponse = await fetch(`http://127.0.0.1:8080/coolmeia/carrinhos/${clientData.carrinhoId.id}`);
      if (!cartResponse.ok) throw new Error('Erro ao carregar carrinho');
      const cartData = await cartResponse.json();
  
      // Para cada item do carrinho, buscar os detalhes do produto
      const itemsWithProducts = await Promise.all(
        cartData.itens.map(async (item) => {
          const productResponse = await fetch(`http://127.0.0.1:8080/coolmeia/produtos/${item.produto.id}`);
          const productData = await productResponse.json();
          return {
            ...item,
            produto: productData // Substitui o produto que só tinha ID pelo produto completo
          };
        })
      );
  
      setCart({
        ...cartData,
        itens: itemsWithProducts
      });
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleQuantityChange = async (produtoId, novaQuantidade) => {
    try {
      const response = await fetch(`http://127.0.0.1:8080/coolmeia/carrinhos/${clientData.carrinhoId.id}/itens/${produtoId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          quantidade: novaQuantidade
        }),
      });

      if (!response.ok) throw new Error('Erro ao atualizar quantidade');
      fetchClientAndCart();
    } catch (err) {
      setError(err.message);
    }
  };

  const handleRemoveItem = async (produtoId) => {
    try {
      const response = await fetch(
        `http://127.0.0.1:8080/coolmeia/carrinhos/${clientData.carrinhoId.id}/itens/${produtoId}`,
        {
          method: 'DELETE',
        }
      );

      if (!response.ok) throw new Error('Erro ao remover item');
      fetchClientAndCart();
    } catch (err) {
      setError(err.message);
    }
  };

  const handleApplyCoupon = async () => {
    try {
      const response = await fetch(`http://127.0.0.1:8080/coolmeia/carrinhos/${clientData.carrinhoId.id}/cupom`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ codigo: couponCode }),
      });

      if (!response.ok) throw new Error('Cupom inválido');
      fetchClientAndCart();
      setCouponCode('');
    } catch (err) {
      setError(err.message);
    }
  };

  const handleCepBlur = async () => {
    if (cep.length === 8) {
      try {
        const response = await fetch(`https://viacep.com.br/ws/${cep}/json/`);
        const data = await response.json();

        if (!data.erro) {
          setAddressInfo(data);
        } else {
          setError('CEP não encontrado');
          setAddressInfo(null);
        }
      } catch (err) {
        setError('Erro ao buscar CEP');
        setAddressInfo(null);
      }
    }
  };

  const handleFinishPurchase = async () => {
    if (!addressInfo || !number) {
      setError('Preencha o endereço completo');
      return;
    }

    if (!cardData.name || !cardData.number || !cardData.validity || !cardData.cvv) {
      setError('Preencha todos os dados do cartão');
      return;
    }

    try {
      const compraResponse = await fetch('http://127.0.0.1:8080/coolmeia/compras', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          carrinhoId: clientData.carrinhoId.id,
          enderecoEntrega: {
            cep: cep,
            cidade: addressInfo.localidade,
            bairro: addressInfo.bairro,
            rua: addressInfo.logradouro,
            numero: parseInt(number)
          },
          pagamento: {
            nome: cardData.name,
            numero: cardData.number,
            validade: cardData.validity,
            cvv: cardData.cvv
          },
          frete: 0.0
        }),
      });

      if (!compraResponse.ok) throw new Error('Erro ao criar compra');
      const compra = await compraResponse.json();

      // Realizar a compra incluindo o CPF do cliente
      const realizarResponse = await fetch(`http://127.0.0.1:8080/coolmeia/compras/${compra.id.id}/realizar`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          clienteCpf: user.cpf
        }),
      });

      if (!realizarResponse.ok) throw new Error('Erro ao finalizar compra');
      
      navigate('/compra-sucesso');
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-amber-50">
        <Header />
        <main className="max-w-7xl mx-auto px-4 py-8">
          <div className="flex justify-center items-center h-64">
            <p className="text-lg">Carregando carrinho...</p>
          </div>
        </main>
        <Footer />
      </div>
    );
  }
  const handleRemoveCoupon = async (produtoId) => {
    try {
      const response = await fetch(
        `http://127.0.0.1:8080/coolmeia/carrinhos/${clientData.carrinhoId.id}/itens/${produtoId}/cupom`,
        {
          method: 'DELETE'
        }
      );

      if (!response.ok) throw new Error('Erro ao remover cupom');
      fetchClientAndCart();
    } catch (err) {
      setError(err.message);
    }
  };
  
  // No render do CartPage:
  {activeStep === 'cart' && (
    <CartItemList 
      items={cart.itens} 
      onQuantityChange={handleQuantityChange}
      onRemoveItem={handleRemoveItem}
      onRemoveCoupon={handleRemoveCoupon}
    />
  )}

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />

      <main className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-black mb-8">Carrinho de Compras</h1>

        {error && (
          <div className="mb-4 p-4 bg-red-100 border border-red-400 text-red-700 rounded">
            {error}
          </div>
        )}

        {(!cart?.itens || cart.itens.length === 0) ? (
          <div className="text-center py-16">
            <ShoppingCart className="mx-auto h-12 w-12 text-amber-400" />
            <h3 className="mt-2 text-sm font-medium text-black">Seu carrinho está vazio</h3>
            <div className="mt-6">
              <Link
                to="/produtos"
                className="inline-flex items-center px-4 py-2 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-amber-600 hover:bg-amber-700"
              >
                Continuar comprando
              </Link>
            </div>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {activeStep === 'cart' && (
              <CartItemList 
                items={cart.itens} 
                onQuantityChange={handleQuantityChange}
                onRemoveItem={handleRemoveItem}
                onRemoveCoupon={handleRemoveCoupon}
              />
            )}
            {activeStep === 'address' && (
              <div className="md:col-span-2">
                <AddressForm
                  cep={cep}
                  setCep={setCep}
                  number={number}
                  setNumber={setNumber}
                  addressInfo={addressInfo}
                  onCepBlur={handleCepBlur}
                />
              </div>
            )}
            {activeStep === 'payment' && (
              <div className="md:col-span-2">
                <PaymentForm
                  cardData={cardData}
                  setCardData={setCardData}
                />
              </div>
            )}
            
            <OrderSummary 
              cart={cart}
              activeStep={activeStep}
              couponCode={couponCode}
              setCouponCode={setCouponCode}
              onApplyCoupon={handleApplyCoupon}
              onStepChange={setActiveStep}
              onFinishPurchase={handleFinishPurchase}
              canProceed={addressInfo && number}
            />
          </div>
        )}
      </main>

      <Footer />
    </div>
  );
};

export default CartPage;