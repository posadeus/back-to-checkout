package com.posadeus.btc

class Checkout(private val rules: List<Rule>) {

  private var products: Map<String, PromoPrice> = mapOf()

  fun price(products: String): Int {

    when {
      products.isEmpty() -> return 0
      else -> products.chunked(1)
          .forEach { collectProduct(it) }
    }

    return calculateTotal()
  }

  private fun calculateTotal(): Int =
      products.map { it.value.price }
          .reduce { acc, i -> acc + i }

  private fun collectProduct(product: String) {

    if (products.isEmpty()) {

      products = mapOf(product to PromoPrice(1, scan(product)))
    }
    else {

      if (products.containsKey(product)) {

        products =
            HashMap(products + mapOf(product to products[product]!!.copy(quantity = products[product]!!.quantity.plus(1),
                                                                         price = priceWithPromo(product))))
      }
      else {

        products = HashMap(products + mapOf(product to PromoPrice(1, scan(product))))
      }
    }
  }

  private fun priceWithPromo(product: String): Int =
      if (rules.first { product == it.productName }.promo?.pieces == products[product]!!.quantity.plus(1))
        rules.first { product == it.productName }.promo!!.price
      else products[product]!!.price +
           rules.first { product == it.productName }.productPrice

  private fun scan(product: String): Int =
      rules.first { product == it.productName }
          .productPrice
}

data class PromoPrice(val quantity: Int,
                      val price: Int)
