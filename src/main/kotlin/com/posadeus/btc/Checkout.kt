package com.posadeus.btc

class Checkout(private val rules: List<Rule>) {

  private val receipt: Receipt = Receipt(mutableMapOf())

  fun price(products: Products): Int {

    products.chunked(1).forEach { scan(it) }

    return total()
  }

  fun scan(product: Product) {
    receipt.addProduct(product, getProductInfo(product))
  }

  fun total(): Int =
      when {
        receipt.isEmpty() -> 0
        else -> getPrice()
      }

  private fun getProductInfo(product: Product): ProductInfo =
      when {
        receipt.hasProduct(product) -> updateProductInfo(product)
        else -> newProductInfo()
      }

  private fun newProductInfo(): ProductInfo =
      ProductInfo(1)

  private fun updateProductInfo(product: Product): ProductInfo =
      ProductInfo(receipt.products[product]!!.quantity.plus(1))

  private fun getPrice(): Int =
      receipt.products
          .map { product ->
            val productDetail = rules.first { product.key == it.productName }

            if (productDetail.promo?.pieces != null)
              productDetail.promo.applyPromotion(product.value.quantity,
                                                 productDetail.productPrice,
                                                 productDetail.promo.pieces)
            else
              productDetail.productPrice * product.value.quantity
          }
          .reduce { acc, i -> acc + i }
}