import React, { useState } from 'react';
import ProductCard from '../components/ProductCard';
import { useCart } from '../context/CartContext';

const categories = ['All', 'Electronics', 'Clothing', 'Accessories'];

const products = [
  { id: 1, name: 'Apple MacBook Pro', price: 1200, category: 'Electronics' },
  { id: 2, name: 'Nike Running T-Shirt', price: 30, category: 'Clothing' },
  { id: 3, name: 'Samsung Galaxy S23', price: 999, category: 'Electronics' },
  { id: 4, name: 'Levis Slim Fit Jeans', price: 50, category: 'Clothing' },
  { id: 5, name: 'Sony Wireless Headphones', price: 150, category: 'Accessories' },
  { id: 6, name: 'Adidas Hoodie', price: 60, category: 'Clothing' },
  { id: 7, name: 'Apple AirPods Pro', price: 250, category: 'Accessories' },
  { id: 8, name: 'Dell XPS 13', price: 1300, category: 'Electronics' }
];

const Home = () => {
  const { addToCart } = useCart();
  const [selectedCategory, setSelectedCategory] = useState('All');

  const filteredProducts = selectedCategory === 'All'
    ? products
    : products.filter((product) => product.category === selectedCategory);

  return (
    <div className="p-6 bg-indigo-100 min-h-screen">
      <div className="mb-6 flex justify-center space-x-4">
        {categories.map((category) => (
          <button
            key={category}
            onClick={() => setSelectedCategory(category)}
            className={`px-6 py-2 rounded-lg text-lg font-medium transition-all ${selectedCategory === category ? 'bg-indigo-600 text-white' : 'bg-indigo-300 hover:bg-indigo-400'}`}
          >
            {category}
          </button>
        ))}
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {filteredProducts.map((product) => (
          <ProductCard key={product.id} product={product} addToCart={addToCart} />
        ))}
      </div>
    </div>
  );
};

export default Home;
