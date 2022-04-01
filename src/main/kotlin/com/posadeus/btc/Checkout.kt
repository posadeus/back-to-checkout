package com.posadeus.btc

class Checkout {

  fun price(product: String): Int =
      if (product == "A") 50
      else 0
}
