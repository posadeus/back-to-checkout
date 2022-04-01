package com.posadeus.btc

class Checkout(private val rules: List<Rule>) {

  fun price(products: String): Int =
      when {
        products.isEmpty() -> 0
        else -> products.chunked(1)
            .map { scan(it) }
            .reduce { acc, i -> acc + i }
      }

  private fun scan(product: String): Int =
      rules.first { product == it.productName }.productPrice
}
