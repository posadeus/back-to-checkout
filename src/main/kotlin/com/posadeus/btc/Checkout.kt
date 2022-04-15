package com.posadeus.btc

class Checkout(private val rules: List<Rule>) {

  private var receipt: Receipt = Receipt(emptyMap())

  fun price(products: String): Int {

    when {
      products.isEmpty() -> return 0
      else -> products.chunked(1)
          .forEach { createReceipt(it) }
    }

    return calculateTotal()
  }

  private fun calculateTotal(): Int =
      receipt.products.map { it.value.price }
          .reduce { acc, i -> acc + i }

  private fun createReceipt(product: String) {

    receipt.products = if (receipt.products.isEmpty()) {

      mapOf(product to PromoPrice(1, scan(product)))
    }
    else {

      addQuantityAndPriceToProduct(product)
    }
  }

  private fun addQuantityAndPriceToProduct(product: String) =
      if (receipt.products.containsKey(product)) {

        HashMap(receipt.products +
                mapOf(product to receipt.products[product]!!.copy(quantity = receipt.products[product]!!.quantity.plus(1),
                                                                  price = priceWithPromo(product))))
      }
      else {

        HashMap(receipt.products + mapOf(product to PromoPrice(1, scan(product))))
      }

  private fun priceWithPromo(product: String): Int =
      if (rules.first { product == it.productName }.promo?.pieces == receipt.products[product]!!.quantity.plus(1))
        rules.first { product == it.productName }.promo!!.price
      else receipt.products[product]!!.price +
           rules.first { product == it.productName }.productPrice

  private fun scan(product: String): Int =
      rules.first { product == it.productName }
          .productPrice
}

data class PromoPrice(val quantity: Int,
                      val price: Int)

