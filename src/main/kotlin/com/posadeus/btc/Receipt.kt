package com.posadeus.btc

class Receipt(var products: Map<String, PromoPrice>) {

  fun getTotal(): Int =
      products.map { it.value.price }
          .reduce { acc, i -> acc + i }
}