package com.posadeus.btc

class Checkout(private val rule: Rule) {

  fun price(product: String): Int =
      if (product == rule.productName) rule.productPrice
      else 0
}
