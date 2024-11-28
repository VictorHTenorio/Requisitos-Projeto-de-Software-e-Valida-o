import React from 'react';

const Footer = () => {
  return (
    <footer className="bg-black mt-12">
      <div className="max-w-7xl mx-auto px-4 py-8">
        <p className="text-center text-amber-400">© {new Date().getFullYear()} CoolmeiaStreetWear Store. Todos os direitos reservados.</p>
      </div>
    </footer>
  );
};

export default Footer;