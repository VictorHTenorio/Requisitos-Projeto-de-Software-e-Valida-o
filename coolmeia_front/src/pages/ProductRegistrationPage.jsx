import React, { useState } from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { X } from 'lucide-react';

const ProductRegistrationPage = () => {
  const [product, setProduct] = useState({
    name: '',
    description: '',
    price: '',
    quantity: '',
    photo: null,
    categories: [],
    colors: [],
  });
  const [category, setCategory] = useState('');
  const [color, setColor] = useState({ name: '', hex: '' });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProduct((prevProduct) => ({
      ...prevProduct,
      [name]: value,
    }));
  };

  const handlePhotoChange = (e) => {
    const file = e.target.files[0];
    setProduct((prevProduct) => ({
      ...prevProduct,
      photo: file,
    }));
  };

  const handleCategoryChange = (e) => {
    setCategory(e.target.value);
  };

  const handleCategoryAdd = () => {
    if (category.trim() !== '') {
      setProduct((prevProduct) => ({
        ...prevProduct,
        categories: [...prevProduct.categories, category.trim()],
      }));
      setCategory('');
    }
  };

  const handleCategoryRemove = (categoryToRemove) => {
    setProduct((prevProduct) => ({
      ...prevProduct,
      categories: prevProduct.categories.filter((cat) => cat !== categoryToRemove),
    }));
  };

  const handleColorNameChange = (e) => {
    setColor((prevColor) => ({
      ...prevColor,
      name: e.target.value,
    }));
  };

  const handleColorHexChange = (e) => {
    setColor((prevColor) => ({
      ...prevColor,
      hex: e.target.value,
    }));
  };

  const handleColorAdd = () => {
    if (color.name.trim() !== '' && color.hex.trim() !== '') {
      setProduct((prevProduct) => ({
        ...prevProduct,
        colors: [...prevProduct.colors, color],
      }));
      setColor({ name: '', hex: '' });
    }
  };

  const handleColorRemove = (colorToRemove) => {
    setProduct((prevProduct) => ({
      ...prevProduct,
      colors: prevProduct.colors.filter((c) => c.name !== colorToRemove.name && c.hex !== colorToRemove.hex),
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append('name', product.name);
    formData.append('description', product.description);
    formData.append('price', product.price);
    formData.append('quantity', product.quantity);
    formData.append('photo', product.photo);
    product.categories.forEach((category) => {
      formData.append('categories[]', category);
    });
    product.colors.forEach((color) => {
      formData.append('colors[]', JSON.stringify(color));
    });

    try {
      const response = await fetch('/api/products', {
        method: 'POST',
        body: formData,
      });

      if (response.ok) {
        // Produto cadastrado com sucesso, faça algo (por exemplo, redirecionar, exibir mensagem de sucesso)
        console.log('Produto cadastrado com sucesso!');
      } else {
        // Erro no cadastro, verifique a resposta do servidor para detalhes
        const errorData = await response.json();
        console.error('Erro no cadastro:', errorData.message);
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
    }
  };

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />

      <main className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-black mb-8">Cadastro de Produto</h1>

        <form onSubmit={handleSubmit}>
          <div className="grid grid-cols-1 gap-6">
            <div>
              <label htmlFor="name" className="block text-sm font-medium text-black">
                Nome
              </label>
              <input
                type="text"
                id="name"
                name="name"
                value={product.name}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label htmlFor="description" className="block text-sm font-medium text-black">
                Descrição
              </label>
              <textarea
                id="description"
                name="description"
                value={product.description}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label htmlFor="price" className="block text-sm font-medium text-black">
                Preço
              </label>
              <input
                type="number"
                id="price"
                name="price"
                step="0.01"
                value={product.price}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label htmlFor="quantity" className="block text-sm font-medium text-black">
                Quantidade em Estoque
              </label>
              <input
                type="number"
                id="quantity"
                name="quantity"
                value={product.quantity}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                required
              />
            </div>
            <div>
              <label htmlFor="photo" className="block text-sm font-medium text-black">
                Foto
              </label>
              <input
                type="file"
                id="photo"
                name="photo"
                onChange={handlePhotoChange}
                className="mt-1 block w-full text-sm text-black file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-amber-50 file:text-amber-700 hover:file:bg-amber-100"
                required
              />
            </div>
            <div>
              <label htmlFor="category" className="block text-sm font-medium text-black">
                Categoria
              </label>
              <div className="mt-1 flex">
                <input
                  type="text"
                  id="category"
                  name="category"
                  value={category}
                  onChange={handleCategoryChange}
                  className="flex-grow rounded-none rounded-l-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                />
                <button
                  type="button"
                  onClick={handleCategoryAdd}
                  className="inline-flex items-center px-3 rounded-r-md border border-l-0 border-amber-400 bg-amber-50 text-black sm:text-sm"
                >
                  Adicionar
                </button>
              </div>
              <div className="mt-2 flex flex-wrap gap-2">
                {product.categories.map((category, index) => (
                  <div
                    key={index}
                    className="inline-flex items-center px-2 py-1 rounded-md bg-amber-200 text-black text-sm"
                  >
                    {category}
                    <button
                      type="button"
                      onClick={() => handleCategoryRemove(category)}
                      className="ml-1 text-black hover:text-red-500"
                    >
                      <X className="h-4 w-4" />
                    </button>
                  </div>
                ))}
              </div>
            </div>
            <div>
              <label htmlFor="colorName" className="block text-sm font-medium text-black">
                Cor
              </label>
              <div className="mt-1 flex">
                <input
                  type="text"
                  id="colorName"
                  name="colorName"
                  value={color.name}
                  onChange={handleColorNameChange}
                  className="flex-grow rounded-none rounded-l-md border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500 sm:text-sm"
                  placeholder="Nome da cor"
                />
                <input
                  type="color"
                  id="colorHex"
                  name="colorHex"
                  value={color.hex}
                  onChange={handleColorHexChange}
                  className="w-10 h-10 rounded-none border-amber-400 shadow-sm focus:border-amber-500 focus:ring-amber-500"
                />
                <button
                  type="button"
                  onClick={handleColorAdd}
                  className="inline-flex items-center px-3 rounded-r-md border border-l-0 border-amber-400 bg-amber-50 text-black sm:text-sm"
                >
                  Adicionar
                </button>
              </div>
              <div className="mt-2 flex flex-wrap gap-2">
                {product.colors.map((color, index) => (
                  <div
                    key={index}
                    className="inline-flex items-center px-2 py-1 rounded-md bg-amber-200 text-black text-sm"
                  >
                    <div 
                      className="w-4 h-4 rounded-full mr-2"
                      style={{ backgroundColor: color.hex }}
                    />
                    {color.name}
                    <button
                      type="button"
                      onClick={() => handleColorRemove(color)}
                      className="ml-1 text-black hover:text-red-500"
                    >
                      <X className="h-4 w-4" />
                    </button>
                  </div>
                ))}
              </div>
            </div>
          </div>

          <div className="mt-8">
            <button
              type="submit"
              className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-amber-600 hover:bg-amber-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-amber-500"
            >
              Cadastrar
            </button>
          </div>
        </form>
      </main>

      <Footer />
    </div>
  );
};

export default ProductRegistrationPage;