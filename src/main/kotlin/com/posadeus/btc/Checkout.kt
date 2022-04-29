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
    val quantity = receipt.products[product]?.quantity

    return if (promoPieces != null && quantity != null)
      if (isProductInPromo(promoPieces, quantity))
        productRule.promo.price * (quantity.plus(1) / promoPieces)
      else
        productRule.promo.price
    else
      productRule.productPrice
  }

  private fun isProductInPromo(promoPieces: Int, quantity: Int): Boolean =
      quantity.plus(1) % promoPieces == 0
}

data class PromoPrice(val quantity: Int,
                      val price: Int)

