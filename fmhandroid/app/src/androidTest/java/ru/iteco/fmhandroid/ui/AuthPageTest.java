package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.RemoteException;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;

import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;
import static ru.iteco.fmhandroid.ui.data.DataHelper.invalidAuthInfo;


@RunWith(AllureAndroidJUnit4.class)

public class AuthPageTest extends BaseTest {
    private UiDevice device;

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();


    @Before
    public void logoutCheck() throws RemoteException {
        device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        try {
            authSteps.isAuthScreen();
        } catch (PerformException e) {
            mainPageSteps.clickLogOutBut();
        }
    }



    @Test
    @DisplayName("Hаличие на основной странице названия Authorization")
    public void shouldCheckAuthPageElements() {
        authSteps.isAuthScreen();
    }

    @Test
    @DisplayName("Ввод валидного пароля и логина на латинице")
    public void shouldLogInWithValidData() {
        authSteps.authWithValidData(authInfo());
        mainPageSteps.isMainPage();
    }

    @Test
    @DisplayName("Ввод невалидного пароля и логина на латинице")
    public void shouldLogInWithInvalidData() {
        authSteps.authWithInvalidData(invalidAuthInfo());
        authSteps.checkToast(R.string.wrong_login_or_password,true);
    }

    @Test
    @DisplayName("Оставить поля ввода логина и пароля пустыми")
    public void shouldNotLogInWithEmptyFields() {
        authSteps.authWithEmptyFields();
        authSteps.checkToast(R.string.empty_login_or_password, true);
    }

}
