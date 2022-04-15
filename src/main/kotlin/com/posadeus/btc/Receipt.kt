package com.posadeus.btc

class Receipt(var products: Map<String, PromoPrice> = emptyMap()) {

  fun getTotal(): Int =
      products.map { it.value.price }
          .reduce { acc, i -> acc + i }

  fun isEmpty(): Boolean =
      products.isEmpty()
}