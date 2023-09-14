package ru.iteco.fmhandroid.ui.steps;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;

public class AuthSteps {
    private UiDevice device;

    public Matcher<View> loginField = allOf(withHint("Login"), withParent(withParent(withId(R.id.login_text_input_layout))));
    public Matcher<View> passField = (allOf(withHint("Password"), withParent(withParent(withId(R.id.password_text_input_layout)))));
    public Matcher<View> signBtn = (allOf(withId(R.id.enter_button), withText("SIGN IN"), withContentDescription("Save"), withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))));

    public ViewInteraction emptyToast(int id) {
        return onView(withText(id)).inRoot(new DataHelper.ToastMatcher());
    }

    public void signBtnClick() {
        Allure.step("Нажать на кнопку Войти");
        TestUtils.waitView(signBtn).perform(click());
    }

    public ViewInteraction getLoginText() {
        Allure.step("Ввести логин");
        return TestUtils.waitView(loginField);
    }

    public ViewInteraction getPasswordText() {
        Allure.step("Ввести пароль");
        return TestUtils.waitView(passField);
    }


    public void isAuthScreen() {
        Allure.step("Отображение информации об экране Authorization");
        TestUtils.waitView(allOf(withText("Authorization"),
                withParent(withParent(withId(R.id.nav_host_fragment))))).check(matches(isDisplayed()));
        TestUtils.waitView(loginField).check(matches(isDisplayed()));
        TestUtils.waitView(passField).check(matches(isDisplayed()));
        TestUtils.waitView(signBtn).check(matches(isDisplayed()));
    }

    public void authWithValidData(DataHelper.AuthInfo info) {
        Allure.step("Валидные данные");
        isAuthScreen();
        enterAValidUsernameAndPassword(info);
        TestUtils.waitView(signBtn).check(matches(isClickable()));
        TestUtils.waitView(signBtn).perform(click());
    }

    public void enterAValidUsernameAndPassword(DataHelper.AuthInfo info) {
        Allure.step("Ввести валидный пароль и логин");
        isAuthScreen();
        TestUtils.waitView(loginField).perform(replaceText(info.getLogin()));
        TestUtils.waitView(passField).perform(replaceText(info.getPass()));
        TestUtils.waitView(signBtn).check(matches(isClickable()));
    }

    public void authWithInvalidData(DataHelper.AuthInfo invalidInfo) {
        Allure.step("Невалидные данные");
        isAuthScreen();
        TestUtils.waitView(loginField).perform(replaceText(invalidInfo.getLogin()));
        TestUtils.waitView(passField).perform(replaceText(invalidInfo.getPass()));
        TestUtils.waitView(signBtn).check(matches(isClickable()));
        TestUtils.waitView(signBtn).perform(click());
    }

    public void authWithEmptyFields() {
        Allure.step("Аутентификация с пустыми полями");
        isAuthScreen();
        TestUtils.waitView(loginField).perform(replaceText(""));
        TestUtils.waitView(passField).perform(replaceText(""));
        TestUtils.waitView(signBtn).check(matches(isClickable()));
        TestUtils.waitView(signBtn).perform(click());
    }

    public void checkToast(int id, boolean visible) {
        Allure.step("Проверка уведомления");
        if (visible) {
            emptyToast(id).check(matches(isDisplayed()));
        } else {
            emptyToast(id).check(matches(not(isDisplayed())));
        }
    }

}
