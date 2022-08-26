package com.posadeus.btc

typealias Product = String
typealias Products = String
typealias Quantity = Int

class Receipt(var products: MutableMap<Product, Quantity> = mutableMapOf()) {

  fun isEmpty(): Boolean =
      products.isEmpty()

  fun addProduct(product: Product, productInfo: Quantity) {
    products[product] = productInfo
  }

  fun hasProduct(product: Product): Boolean =
      products.containsKey(product)
}