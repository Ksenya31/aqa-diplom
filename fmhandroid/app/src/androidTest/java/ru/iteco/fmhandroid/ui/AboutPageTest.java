package ru.iteco.fmhandroid.ui;

import android.content.Intent;
import android.os.RemoteException;
import android.os.SystemClock;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.intent.Intents;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;

import io.qameta.allure.kotlin.junit4.DisplayName;

import org.junit.Before;

import org.junit.Test;

import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.steps.AboutPageSteps;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

@RunWith(AllureAndroidJUnit4.class)

public class AboutPageTest extends BaseTest {
    private UiDevice device;

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static AboutPageSteps aboutPageSteps = new AboutPageSteps();


    @Before
    public void logoutCheck() throws RemoteException {
        device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        //device.setOrientationNatural();
        try {
            authSteps.isAuthScreen();
        } catch (PerformException e) {
            mainPageSteps.clickLogOutBut();
        }
        authSteps.authWithValidData(authInfo());
        mainPageSteps.isMainPage();
        mainPageSteps.openAboutPageThroughTheMainMenu();
    }

    @Test
    @DisplayName("Переход по ссылке в Privacy Policy")
    public void shouldOpenPrivacyPolicyDetailsPage() {
        String uriPrivacyPolicy = "https://vhospice.org/#/privacy-policy/";
        String headerPrivacyPolicyPage = "Privacy policy";
        Intents.init();
        aboutPageSteps.openPrivacyPolicy();
        intended(allOf(hasData(uriPrivacyPolicy), hasAction(Intent.ACTION_VIEW)));
        Intents.release();
        aboutPageSteps.getHeaderPrivacyPolicyPage().check(matches(withText(headerPrivacyPolicyPage)));//проверка

    }

    @Test
    @DisplayName("Переход по ссылке в Terms of use")
    public void shouldOpenTermsOfUseDetailsPage() {
        String uriTermsOfUse = "https://vhospice.org/#/terms-of-use";
        String headerTermsOfUsePege = "Terms of use";
        Intents.init();
        aboutPageSteps.openTermsOfUse();
        SystemClock.sleep(3000);
        intended(allOf(hasData(uriTermsOfUse), hasAction(Intent.ACTION_VIEW)));
        Intents.release();
        aboutPageSteps.getHeaderTermsOfUsePage().check(matches(withText(headerTermsOfUsePege)));//проверка

    }

    @Test
    @DisplayName("Возвращение на предыдущий экран")
    public void shouldGoBackBut() {
        aboutPageSteps.aboutBackImageButClick();
        mainPageSteps.isMainPage();
    }

}
