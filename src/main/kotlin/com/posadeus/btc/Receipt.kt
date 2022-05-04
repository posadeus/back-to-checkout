package com.posadeus.btc

class Receipt(var products: MutableMap<String, ProductInfo> = mutableMapOf()) {

  fun getTotal(): Int =
      products.map { it.value.price }
          .reduce { acc, i -> acc + i }

  fun addProduct(product: String, productInfo: ProductInfo) {
    this.products[product] = productInfo
  }

  fun hasProduct(product: String): Boolean =
      this.products.containsKey(product)
}