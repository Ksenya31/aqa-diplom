package ru.iteco.fmhandroid.ui.steps;

import android.os.SystemClock;
import android.view.View;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.TestUtils;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;


import org.hamcrest.Matcher;

public class MainPageSteps {

    public Matcher<View> newsBlockHeader = withText("News");
    public Matcher<View> claimsBlockHeader = withText("Claims");
    public Matcher<View> authImBut = withId(R.id.authorization_image_button);
    public Matcher<View> logOutBut = withText("Log out");

    public Matcher<View> allNewsBut = withId(R.id.all_news_text_view);
    public Matcher<View> mainMenuImBut = withId(R.id.main_menu_image_button);
    public Matcher<View> mainInTheMainMenu = withText("Main");
    public Matcher<View> newsInTheMainMenu = withText("News");
    public Matcher<View> claimsInTheMainMenu = withText("Claims");
    public Matcher<View> allClaimsBut = withId(R.id.all_claims_text_view);
    public Matcher<View> aboutInTheMainMenu = withText("About");
    public Matcher<View> ourMissionImBut = withId(R.id.our_mission_image_button);
    public Matcher<View> newsExpandMaterialBut = allOf(withId(R.id.expand_material_button), withParent(withParent(withId(R.id.container_list_news_include_on_fragment_main))));
    public Matcher<View> allNewsCardsBlockConstraintLayout = withId(R.id.all_news_cards_block_constraint_layout);
    public Matcher<View> claimsExpandMaterialBut = allOf(withId(R.id.expand_material_button), withParent(withParent(withId(R.id.container_list_claim_include_on_fragment_main))));
    public Matcher<View> allClaimsCardsBlockConstraintLayout = withId(R.id.all_claims_cards_block_constraint_layout);
    public Matcher<View> scrollView = withClassName(endsWith("ScrollView"));
    public Matcher<View> addNewClaimBut = withId(R.id.add_new_claim_material_button);


    public void isMainPage() {
        Allure.step("Отображение информации о разделе Main");
        TestUtils.waitView(newsBlockHeader).check(matches(isDisplayed()));
        TestUtils.waitView(withId(R.id.container_list_news_include_on_fragment_main)).check(matches(isDisplayed()));
        TestUtils.waitView(withId(R.id.container_list_claim_include_on_fragment_main)).check(matches(isDisplayed()));
        TestUtils.waitView(claimsBlockHeader).check(matches(isDisplayed()));
    }

    public void clickLogOutBut() {
        Allure.step("Нажать Log Out");
        TestUtils.waitView(authImBut).check(matches(isDisplayed())).perform(click());
        TestUtils.waitView(logOutBut).check(matches(isDisplayed())).perform(click());
    }

    public void openNewsPageThroughTheMainMenu() {
        Allure.step("Открыть страницу новостей через главное меню");
        TestUtils.waitView(mainMenuImBut).perform(click());
        TestUtils.waitView(newsInTheMainMenu).perform(click());
    }

    public void openClaimsPageThroughTheMainMenu() {
        Allure.step("Открыть страницу Claims через главное меню");
        TestUtils.waitView(mainMenuImBut).perform(click());
        TestUtils.waitView(claimsInTheMainMenu).perform(click());
    }

    public void openAboutPageThroughTheMainMenu() {
        Allure.step("Открыть страницу AboutPage через главное меню");
        TestUtils.waitView(mainMenuImBut).perform(click());
        TestUtils.waitView(aboutInTheMainMenu).perform(click());
    }

    public void isNewsBlockCollapsed() {
        Allure.step("Закрыт ли блок новостей");
        TestUtils.waitView(newsBlockHeader).check(matches(isDisplayed()));
        TestUtils.waitView(allNewsBut).check(matches(not(isDisplayed())));
        TestUtils.waitView(allNewsCardsBlockConstraintLayout).check(matches(not(isDisplayed())));
    }

    public void isClaimsBlockCollapsed() {
        Allure.step("Закрыт ли блок Claims");
        TestUtils.waitView(claimsBlockHeader).check(matches(isDisplayed()));
        TestUtils.waitView(allClaimsBut).check(matches(not(isDisplayed())));
        TestUtils.waitView(allClaimsCardsBlockConstraintLayout).check(matches(not(isDisplayed())));
    }

    public void openOurMissionPage() {
        Allure.step("Открыть страницу Love is all");
        TestUtils.waitView(ourMissionImBut).perform(click());
    }

    public void clickAllNewsBut() {
        Allure.step("Открыть All News");
        TestUtils.waitView(allNewsBut).perform(click());
    }

    public void clickAllClaimsBut() {
        Allure.step("Открыть All Claims");
        TestUtils.waitView(allClaimsBut).perform(click());
    }

    public void newsExpandMaterialButtonClick() {
        Allure.step("Нажатие на кнопку раскрытия новостей");
        TestUtils.waitView(newsExpandMaterialBut).perform(click());
    }

    public void claimsExpandMaterialButtonClick() {
        Allure.step("Нажатие на кнопку раскрытия Сlaims");
        TestUtils.waitView(claimsExpandMaterialBut).perform(click());
    }

    public void openClaimItemDescription(int position) {
        Allure.step("Открыть описание элемента Claim");
        TestUtils.waitView(scrollView).check(matches(isEnabled()));
        TestUtils.waitView(scrollView).perform(swipeUp());
        SystemClock.sleep(3000);
        TestUtils.waitView(TestUtils.withRecyclerView(R.id.claim_list_recycler_view)
                .atPositionOnView(position, R.id.claim_list_card)).perform(click());
    }

    public void addNewClaimButtonClick() {
        Allure.step("Добавить новую жалобу");
        TestUtils.waitView(addNewClaimBut).perform(click());
    }

}
