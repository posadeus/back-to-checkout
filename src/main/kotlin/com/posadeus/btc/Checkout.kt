package com.posadeus.btc

class Checkout(private val rule: Rule) {

  fun price(product: String): Int =
      if (product == "A") rule.productPrice
      else 0
}
