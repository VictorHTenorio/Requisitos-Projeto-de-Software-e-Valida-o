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
    fetchCart();
  }, [user]);

  const fetchCart = async () => {
    try {
      const response = await fetch(`http://127.0.0.1:8080/coolmeia/carrinhos/${user.carrinhoId}`);
      if (!response.ok) throw new Error('Erro ao carregar carrinho');
      const data = await response.json();
      setCart(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleQuantityChange = async (produtoId, quantidade, valorUnitario) => {
    if (quantidade < 1) return;
    
    try {
      const response = await fetch(`http://127.0.0.1:8080/coolmeia/carrinhos/${user.carrinhoId}/itens`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          produtoId,
          quantidade,
          valorUnitario
        }),
      });

      if (!response.ok) throw new Error('Erro ao atualizar quantidade');
      fetchCart();
    } catch (err) {
      setError(err.message);
    }
  };

  const handleApplyCoupon = async () => {
    try {
      const response = await fetch(`http://127.0.0.1:8080/coolmeia/carrinhos/${user.carrinhoId}/cupom`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ codigo: couponCode }),
      });

      if (!response.ok) throw new Error('Cupom inválido');
      fetchCart();
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
      } catch (error) {
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
      // Criar compra
      const compraResponse = await fetch('http://127.0.0.1:8080/coolmeia/compras', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          carrinhoId: user.carrinhoId,
          enderecoEntrega: {
            cep,
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
          }
        }),
      });

      if (!compraResponse.ok) throw new Error('Erro ao criar compra');
      const compra = await compraResponse.json();

      // Realizar a compra
      const realizarResponse = await fetch(`http://127.0.0.1:8080/coolmeia/compras/${compra.id}/realizar`, {
        method: 'POST'
      });

      if (!realizarResponse.ok) throw new Error('Erro ao finalizar compra');
      
      // Redirecionar para sucesso
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