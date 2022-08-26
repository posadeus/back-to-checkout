package com.posadeus.btc

typealias Product = String
typealias Products = String
typealias Quantity = Int

class Receipt(var products: MutableMap<Product, Quantity> = mutableMapOf()) {

  fun isEmpty(): Boolean =
      products.isEmpty()

  fun addProduct(product: Product, quantity: Quantity) {
    products[product] = quantity
  }

  fun hasProduct(product: Product): Boolean =
      products.containsKey(product)

  fun total(prices: List<Price>): Int =
      prices.reduce { acc, i -> acc + i }
}