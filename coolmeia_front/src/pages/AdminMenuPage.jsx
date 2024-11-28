import React from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { ArrowRight } from 'lucide-react';

const AdminMenuPage = () => {
  const pages = [
    { name: 'Cadastro de Categorias', path: '/categorias' },
    { name: 'Cadastro de Cupons', path: '/cupons' },
    { name: 'Gerenciamento de Produtos', path: '/cadastro-produto' },
  ];

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />
      <main className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-black mb-8">Menu do Administrador</h1>
        <div className="grid grid-cols-1 gap-6">
          {pages.map((page) => (
            <a
              key={page.name}
              href={page.path}
              className="block bg-amber-200 hover:bg-amber-300 text-black font-medium py-4 px-6 rounded-md shadow-md flex items-center justify-between"
            >
              {page.name}
              <ArrowRight className="h-5 w-5" />
            </a>
          ))}
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default AdminMenuPage;
