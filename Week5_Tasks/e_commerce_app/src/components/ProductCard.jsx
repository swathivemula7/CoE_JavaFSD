import React from 'react';

const ProductCard = ({ product, addToCart }) => {
  return (
    <div className="border p-4 rounded-lg shadow-md">
      <h2 className="text-xl font-bold">{product.name}</h2>
      <p className="text-gray-600">Price: ${product.price}</p>
      <button 
        onClick={() => addToCart(product)} 
        className="mt-2 px-4 py-2 bg-blue-500 text-white rounded">
        Add to Cart
      </button>
    </div>
  );
};

export default ProductCard;