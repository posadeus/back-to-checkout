package com.posadeus.btc

class Receipt(var products: MutableMap<String, PromoPrice> = mutableMapOf()) {

  fun getTotal(): Int =
      products.map { it.value.price }
          .reduce { acc, i -> acc + i }
}