/* @Provengo summon selenium */

/**
 * The customer is logging in
 */
defineEvent(SeleniumSession, "loginUser", function(session, e) {
    bp.log.info(e);
    session.click("//a[@title='Log in to your customer account']");
    session.writeText("//input[@id='field-email']", e.email);
    session.writeText("//input[@id='field-password']", e.password);
    session.click("//*[@id='submit-login']");
})

/**
 * The customer clicks on n product that he wants to buy, and enters its page
 */
defineEvent(SeleniumSession, "navigateToTheProductPage", function(session, e) {
    session.click("//img[contains(@alt,'" + e.prodName + "')]");
})

/**
 * The customer adds the product to his cart
 */
defineEvent(SeleniumSession, "addProductToCart", function(session, e) {
    session.click("//*[@class='btn btn-primary add-to-cart']");
})

/**
 * Check existence of the product in the cart
 * we compare the name of the product in the cart and the product that was added to the cart
 */
defineEvent(SeleniumSession, "checkExistenceOfProductInCart", function(session, e) {
    session.click("//*[@class='btn btn-secondary']");
    session.click("//*[@class='blockcart cart-preview active']");
    session.assertText("//a[contains(text(),'"+ e.prodName +"' )]", e.prodName)
})

/**
 * Check if the product price was changed
 */
defineEvent(SeleniumSession, "checkProductPriceChangedInCart", function(session, e) {
    session.click("//*[@class='blockcart cart-preview active']");
    session.assertText("//strong[contains(text(),'"+ e.prodNewPrice +"' )]", e.prodNewPrice)
})



/**
 *  The seller is logging in
 */
defineEvent(SeleniumSession, "loginAdmin", function(session, e) {
    bp.log.info(e);
    session.writeText("//input[@id='email']", e.email);
    session.writeText("//input[@id='passwd']", e.password);
    session.click("//*[@id='submit_login']");
})

/**
 * The seller navigates to the product editing page of the product he wants to change its price
 */
defineEvent(SeleniumSession, "navigateToTheProductEditingPage", function(session, e) {
    session.click("//*[@class='material-icons mi-store']");
    session.click("//*[@id='subtab-AdminProducts']");
    session.click("//a[text()='" + e.prodName + "']");
})

/**
 * The seller changes the price of the product
 */
defineEvent(SeleniumSession, "changeProductPrice", function(session, e) {
    session.writeText("//*[@id='form_step1_price_shortcut']", e.prodNewPrice, true);
    session.click("//button[@class='btn btn-primary js-btn-save ml-3']");
})

/**
 * Check if the price of the product in the products dashboard was changed correctly
 */
defineEvent(SeleniumSession, "checkProductPriceChanged", function(session, e) {
    session.click("//*[@id='subtab-AdminProducts']");
    session.assertText("//*[@id='product_catalog_list']/div/table/tbody/tr[16]/td[7]/a", "$"+e.prodNewPrice)
})
