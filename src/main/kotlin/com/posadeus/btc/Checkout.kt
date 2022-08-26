package com.posadeus.btc

typealias Price = Int

class Checkout(private val rules: List<Rule>) {

  private val receipt: Receipt = Receipt(mutableMapOf())

  fun price(products: Products): Int {

    products.chunked(1).forEach { scan(it) }

    return total()
  }

  fun scan(product: Product) {
    receipt.addProduct(product, quantity(product))
  }

  fun total(): Int =
      when {
        receipt.isEmpty() -> 0
        else -> receipt.total(getPrices())
      }

  private fun quantity(product: Product): Quantity =
      when {
        receipt.hasProduct(product) -> updateQuantity(product)
        else -> 1
      }

  private fun updateQuantity(product: Product): Quantity =
      receipt.products[product]!!.plus(1)

  private fun getPrices(): List<Price> =
      receipt.products
          .map { product ->
            val productRule = rules.first { product.key == it.productName }

            if (productRule.promo?.pieces != null)
              productRule.promo.applyPromotion(product.value,
                                               productRule.productPrice,
                                               productRule.promo.pieces)
            else
              productRule.productPrice * product.value
          }
}