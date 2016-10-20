package com.miguelbcr.io.rx_billing_service;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import com.miguelbcr.io.rx_billing_service.entities.ProductType;
import com.miguelbcr.io.rx_billing_service.entities.Purchase;
import com.miguelbcr.io.rx_billing_service.entities.SkuDetails;
import io.reactivex.Single;
import java.util.List;
import rx_activity_result2.RxActivityResult;

/**
 * https://developer.android.com/google/play/billing/billing_integrate.html<br/>
 * https://developer.android.com/google/play/billing/billing_testing.html <br/><br/>
 *
 * <b>Make sure you complete these steps in order to test purchases witouth charges:</b>
 *
 * <ul>
 *
 * <li>Make sure it's actually published in alpha, beta, or production. If it appears, for example,
 * as "Draft in Alpha" or beta then you haven't published in. Publishing means that you also need
 * to
 * upload the minimum required assets for Google Play, descriptions, content ratings, etc (See the
 * APK, Store Listing, and Content Rating tabs in the developer console).</li>
 *
 * <li>At least for alpha and beta testing, there is a list of testers that you must create and add
 * each tester's email. Look for the "Manage Testers" section in the Alpha Testing or Beta Testing
 * tab.</li>
 *
 * <li>Add tester's email on Google Play Developer Console in Settings > Account Data > Testing
 * License</li>
 *
 * <li>Each tester must also accept to be a tester. There is an opt-in URL link which you must send
 * to your testers, and they must click on the link and accepts the conditions.</li>
 *
 * <li>After all that is done, you still have to wait about 1 hour before the purchase details
 * start
 * to appear.</li>
 *
 * </ul>
 * <b>Errors:</b> *
 * <ul>
 *
 * <li>This version of the application is not configured for billing through Google Play<br/>
 * http://stackoverflow.com/questions/11068686/this-version-of-the-application-is-not-configured-for-billing-through-google-pla
 *
 * <br/>
 *
 * - Google takes a while to process applications and update them to their servers, for me it
 * takes about half a day. So after saving the apk as a draft on Google Play, you must wait a few
 * hours before the in-app products will respond normally and allow for regular purchases.
 *
 * <br/>
 *
 * - Export and sign APK. Unsigned APK trying to make purchases will get error. </li>
 *
 * </ul>
 */
public class RxBillingService {
  private final RxBillingServiceImpl rxBillingServiceImpl;

  public static void register(Application application) {
    RxActivityResult.register(application);
  }

  public static <T extends Activity> RxBillingService getInstance(T activity) {
    return new RxBillingService(activity);
  }

  public static <T extends Fragment> RxBillingService getInstance(T fragment) {
    return new RxBillingService(fragment);
  }

  private RxBillingService(Object targetUiObject) {
    this.rxBillingServiceImpl = new RxBillingServiceImpl(targetUiObject);
  }

  public Single<Boolean> isBillingSupported(ProductType productType) {
    return rxBillingServiceImpl.isBillingSupported(productType);
  }

  public Single<List<SkuDetails>> getSkuDetails(ProductType productType, List<String> productIds) {
    return rxBillingServiceImpl.getSkuDetails(productType, productIds);
  }

  public Single<Purchase> purchase(ProductType productType, String productId,
      String developerPayload) {
    return rxBillingServiceImpl.purchase(productType, productId, developerPayload);
  }

  public Single<Boolean> consumePurchase(String token) {
    return rxBillingServiceImpl.consumePurchase(token);
  }

  public Single<Purchase> purchaseAndConsume(ProductType productType, String productId,
      String developerPayload) {
    return rxBillingServiceImpl.purchaseAndConsume(productType, productId, developerPayload);
  }
}