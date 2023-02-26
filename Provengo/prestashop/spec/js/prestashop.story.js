/* @provengo summon selenium */
const NEW_PRICE = "23.00"
const PROD_NAME = "The adventure begins Framed poster"

/**
 * This story opens a new browser window with the prestashop site as a customer.
 * the customer logs in, navigates to the product that he wants to buy, and adds the product to his cart.
 */
story('A customer adds a product to the cart', function () {
  let s = new SeleniumSession().start('http://localhost:8888')
  s.loginUser({email: 'harry@potter.com', password: 'H@rrP1!ot'})
  s.navigateToTheProductPage({prodName: PROD_NAME})
  s.addProductToCart()
  s.checkExistenceOfProductInCart({prodName: PROD_NAME})
  waitFor(Any("EndOfAction").and(Any({eventName: "changeProductPrice"})))
  s.checkProductPriceChangedInCart({prodNewPrice: NEW_PRICE})
})

/**
 * This story opens a new browser window with the prestashop site as a seller.
 * the seller logs in, navigates the product editing page of the product he wants to change its price, and changes its price.
 */
story('A seller changes the price of a product', function () {
  let s = new SeleniumSession().start('http://localhost:8888/admin2')
  s.loginAdmin({email: 'some@admin.com', password: 'greatP@ssw0rd!'})
  s.navigateToTheProductEditingPage({prodName: PROD_NAME})
  s.changeProductPrice({prodNewPrice: NEW_PRICE})
  s.checkProductPriceChanged({prodNewPrice: NEW_PRICE})
})
