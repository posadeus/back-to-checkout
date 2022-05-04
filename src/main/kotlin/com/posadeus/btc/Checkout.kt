package com.posadeus.btc

class Checkout(private val rules: List<Rule>) {

  private val receipt: Receipt = Receipt(mutableMapOf())

  fun price(products: String): Int {

    products.chunked(1).forEach { scan(it) }

    return total()
  }

  fun scan(product: String) {
    receipt.addProduct(product, addProductToReceipt(product))
  }

  fun total(): Int =
      when {
        receipt.products.isEmpty() -> 0
        else -> receipt.getTotal()
      }

  private fun addProductToReceipt(product: String): PromoPrice =
      when {
        receipt.products.containsKey(product) -> updateProductAlreadyInTheReceipt(product)
        else -> addNewProductToReceipt(product)
      }

  private fun addNewProductToReceipt(product: String): PromoPrice =
      PromoPrice(1, getPrice(product))

  private fun updateProductAlreadyInTheReceipt(product: String): PromoPrice =
      receipt.products[product]!!
          .copy(quantity = receipt.products[product]!!.quantity.plus(1),
                price = getPrice(product))

  private fun getPrice(product: String): Int {

    val productRule = rules.first { product == it.productName }
    val promoPieces = productRule.promo?.pieces
    val actualProductQuantity = receipt.products[product]?.quantity?.plus(1)

    return if (promoPieces != null && actualProductQuantity != null)
      if (hasApplicablePromotion(promoPieces, actualProductQuantity))
        productRule.promo.price * (actualProductQuantity / promoPieces)
      else if (promoPieces < actualProductQuantity)
        productRule.promo.price + productRule.productPrice * (actualProductQuantity - promoPieces)
      else
        productRule.productPrice * actualProductQuantity
    else
      productRule.productPrice
  }

  private fun hasApplicablePromotion(promoPieces: Int, quantity: Int): Boolean =
      quantity % promoPieces == 0
}

data class PromoPrice(val quantity: Int,
                      val price: Int)

