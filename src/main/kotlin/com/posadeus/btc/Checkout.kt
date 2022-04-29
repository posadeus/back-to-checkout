package com.posadeus.btc

class Checkout(private val rules: List<Rule>) {

  private val receipt: Receipt = Receipt(emptyMap())

  fun price(products: String): Int {

    when {
      products.isEmpty() -> return 0
      else -> products.chunked(1)
          .forEach { receipt.products = addProductToReceipt(it) }
    }

    return receipt.getTotal()
  }

  private fun addProductToReceipt(product: String): Map<String, PromoPrice> =
      when {
        receipt.products.containsKey(product) -> updateProductAlreadyInTheReceipt(product)
        else -> addNewProductToReceipt(product)
      }

  private fun addNewProductToReceipt(product: String): Map<String, PromoPrice> =
      HashMap(receipt.products + mapOf(product to PromoPrice(1, scan(product))))

  private fun updateProductAlreadyInTheReceipt(product: String): Map<String, PromoPrice> =
      HashMap(receipt.products +
              mapOf(product to
                        receipt.products[product]!!
                            .copy(quantity = receipt.products[product]!!.quantity.plus(1),
                                  price = scan(product))))

  private fun scan(product: String): Int {

    val productRule = rules.first { product == it.productName }
    val promoPieces = productRule.promo?.pieces
    val actualProductQuantity = receipt.products[product]?.quantity?.plus(1)

    return if (promoPieces != null && actualProductQuantity != null)
      if (hasApplicablePromotion(promoPieces, actualProductQuantity))
        productRule.promo.price * (actualProductQuantity / promoPieces)
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

