import React from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { CheckCircle } from 'lucide-react';

const PurchaseSuccessPage = () => {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />
      <main className="max-w-7xl mx-auto px-4 py-16">
        <div className="bg-white rounded-lg shadow-md p-8 max-w-2xl mx-auto text-center border-2 border-amber-400">
          <CheckCircle className="w-24 h-24 text-green-500 mx-auto mb-6" />
          
          <h1 className="text-3xl font-bold text-black mb-4">
            Compra Realizada com Sucesso!
          </h1>
          
          <p className="text-gray-600 mb-8">
            Obrigado por sua compra. Você receberá mais informações por email.
          </p>

          <button
            onClick={() => navigate('/')}
            className="bg-amber-500 hover:bg-amber-600 text-white font-bold py-3 px-8 rounded-lg transition-colors duration-300"
          >
            Voltar para Home
          </button>
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default PurchaseSuccessPage;