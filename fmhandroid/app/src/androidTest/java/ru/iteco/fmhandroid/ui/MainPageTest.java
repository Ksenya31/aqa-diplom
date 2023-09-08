package ru.iteco.fmhandroid.ui;

import android.content.Context;
import android.os.RemoteException;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.data.CustomViewAssertion;
import ru.iteco.fmhandroid.ui.steps.*;

import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

@RunWith(AllureAndroidJUnit4.class)

public class MainPageTest extends BaseTest {
    private UiDevice device;

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static NewsPageSteps newsPageSteps = new NewsPageSteps();
    private static ClaimsPageSteps claimsPageSteps = new ClaimsPageSteps();
    private static AboutPageSteps aboutPageSteps = new AboutPageSteps();
    private static OurMissionPageSteps ourMissionPageSteps = new OurMissionPageSteps();

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
    }

    //Панель управления перехода на страницы и выхода из профиля (экран Main)

    @Test
    @DisplayName("Переход на страницу News из Action menu")
    public void shouldOpenNewsPageByButNewsInTheMainMenu() {
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
    }

    @Test
    @DisplayName("Переход на страницу Claims из Action menu")
    public void shouldOpenClaimsPageByButClaimsInTheMainMenu() {
        mainPageSteps.openClaimsPageThroughTheMainMenu();
        claimsPageSteps.isClaimsPage();
    }

    @Test
    @DisplayName("Переход на страницу About из Action menu")
    public void shouldOpenAboutPage() {
        mainPageSteps.openAboutPageThroughTheMainMenu();
        aboutPageSteps.isAboutPage();
    }

    @Test
    @DisplayName("Переход на страницу LOVE IS ALL при нажатии на кнопку ")
    public void shouldOpenOurMissionPage() {
        mainPageSteps.openOurMissionPage();
        ourMissionPageSteps.isOurMissionPage();
    }

    @Test
    @DisplayName("Выход из профиля Log out")
    public void shouldOpenTheLoginPage() {
        mainPageSteps.clickLogOutBut();
        authSteps.isAuthScreen();
    }

    //Раздел Main
    @Test
    @DisplayName("Разворачивание новостного блока News")
    public void shouldOpenNews() {
        int positionNews = 1;
        newsPageSteps.openNewsOnNewsPage(positionNews);
        newsPageSteps.getNewsItemDescription(positionNews).check(matches(isDisplayed()));
    }


    @Test
    @DisplayName("Разворачивание новостной ленты с блоками News")
    public void shouldOpenNewsItemDescription() {
        mainPageSteps.newsExpandMaterialButtonClick();
        mainPageSteps.newsExpandMaterialButtonClick();
        mainPageSteps.isMainPage();
    }

   @Test
    @DisplayName("Сворачивание новостной ленты с блоками News")
    public void shouldCollapseTheListOfNews() {
        mainPageSteps.newsExpandMaterialButtonClick();
        mainPageSteps.isNewsBlockCollapsed();
    }

    @Test
    @DisplayName("Сворачивание блока Claims")
    public void shouldCollapseTheListOfClaims() {
        mainPageSteps.claimsExpandMaterialButtonClick();
        mainPageSteps.isClaimsBlockCollapsed();
    }

    @Test
    @DisplayName("Переход в Creating Claims")
    public void shouldOpenTheClaimsForm() {
        mainPageSteps.addNewClaimButtonClick();
        claimsPageSteps.isClaimsForm();
    }

    @Test
    @DisplayName("Переход на страницу Claims при нажатии на all claims")
    public void shouldOpenClaimsPageByButAllClaims() {
        mainPageSteps.clickAllClaimsBut();
        claimsPageSteps.isClaimsPage();
    }

    @Test
    @DisplayName("Переход на страницу News при нажатии на all news")
    public void shouldOpenNewsPageByButAllNews() {
        mainPageSteps.clickAllNewsBut();
        newsPageSteps.isNewsPage();
    }

    @Test
    @DisplayName("Переход в Claims (блок с претензиями)")
    public void shouldOpenClaimsItemDescription() {
        int claimPosition = 0;
        mainPageSteps.openClaimItemDescription(claimPosition);
        claimsPageSteps.getClaimItemDescription().check(matches(isDisplayed()));
    }


}
