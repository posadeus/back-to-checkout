package com.posadeus.btc

class Checkout(private val rules: List<Rule>) {

  private val receipt: Receipt = Receipt(mutableMapOf())

  fun price(products: String): Int {

    products.chunked(1).forEach { scan(it) }

    return total()
  }

  fun scan(product: String) {
    receipt.addProduct(product, getProductInfo(product))
  }

  fun total(): Int =
      when {
        receipt.isEmpty() -> 0
        else -> receipt.getTotal()
      }

  private fun getProductInfo(product: String): ProductInfo =
      when {
        receipt.hasProduct(product) -> updateProductInfo(product)
        else -> newProductInfo(product)
      }

  private fun newProductInfo(product: String): ProductInfo =
      ProductInfo(1, getPrice(product))

  private fun updateProductInfo(product: String): ProductInfo =
      receipt.products[product]!!
          .copy(quantity = receipt.products[product]!!.quantity.plus(1),
                price = getPrice(product))

  private fun getPrice(product: String): Int {

    val productRule = rules.first { product == it.productName }
    val promoPieces = productRule.promo?.pieces
    val newProductQuantity = receipt.products[product]?.quantity?.plus(1) ?: 1
    val productPrice = productRule.productPrice

    return if (promoPieces != null)
      productRule.promo.applyPromotion(newProductQuantity, productPrice, promoPieces)
    else
      productPrice
  }
}