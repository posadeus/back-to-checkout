package com.posadeus.btc

typealias Product = String
typealias Products = String

class Receipt(var products: MutableMap<Product, ProductInfo> = mutableMapOf()) {

  fun isEmpty(): Boolean =
      products.isEmpty()

  fun getTotal(): Int =
      products.map { it.value.price }
          .reduce { acc, i -> acc + i }

  fun addProduct(product: Product, productInfo: ProductInfo) {
    products[product] = productInfo
  }

  fun hasProduct(product: Product): Boolean =
      products.containsKey(product)
}