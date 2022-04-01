package com.posadeus.btc

class Checkout(private val rules: List<Rule>) {

  fun price(product: String): Int =
      rules.first { product == it.productName }.productPrice
}
