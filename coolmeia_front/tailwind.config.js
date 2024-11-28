/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'brand': {
          primary: '#FFC107', // Amarelo base da abelha
          secondary: '#000000', // Preto
          light: '#FFF8E1', // Amarelo claro para backgrounds
          hover: '#FFD54F', // Amarelo mais claro para hovers
          dark: '#FFA000', // Amarelo mais escuro para textos importantes
        }
      }
    },
  },
  plugins: [],
}