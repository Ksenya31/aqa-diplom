package ru.iteco.fmhandroid.ui.steps;

import android.os.SystemClock;
import android.view.View;

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

    @Step("Отображение информации о разделе Main")
    public void isMainPage() {
        TestUtils.waitView(newsBlockHeader).check(matches(isDisplayed()));
        TestUtils.waitView(withId(R.id.container_list_news_include_on_fragment_main)).check(matches(isDisplayed()));
        TestUtils.waitView(withId(R.id.container_list_claim_include_on_fragment_main)).check(matches(isDisplayed()));
        TestUtils.waitView(claimsBlockHeader).check(matches(isDisplayed()));
    }
    @Step("Нажать Log Out")
    public void clickLogOutBut() {
        TestUtils.waitView(authImBut).check(matches(isDisplayed())).perform(click());
        TestUtils.waitView(logOutBut).check(matches(isDisplayed())).perform(click());
    }
    @Step("Открыть страницу новостей через главное меню ")
    public void openNewsPageThroughTheMainMenu() {
        TestUtils.waitView(mainMenuImBut).perform(click());
        TestUtils.waitView(newsInTheMainMenu).perform(click());
    }
    @Step("Открыть страницу Claims через главное меню ")
    public void openClaimsPageThroughTheMainMenu() {
        TestUtils.waitView(mainMenuImBut).perform(click());
        TestUtils.waitView(claimsInTheMainMenu).perform(click());
    }
    @Step("Открыть страницу AboutPage через главное меню ")
    public void openAboutPageThroughTheMainMenu() {
        TestUtils.waitView(mainMenuImBut).perform(click());
        TestUtils.waitView(aboutInTheMainMenu).perform(click());
    }
    @Step("Закрыт ли блок новостей")
    public void isNewsBlockCollapsed() {
        TestUtils.waitView(newsBlockHeader).check(matches(isDisplayed()));
        TestUtils.waitView(allNewsBut).check(matches(not(isDisplayed())));
        TestUtils.waitView(allNewsCardsBlockConstraintLayout).check(matches(not(isDisplayed())));
    }
    @Step("Закрыт ли блок Claims")
    public void isClaimsBlockCollapsed() {
        TestUtils.waitView(claimsBlockHeader).check(matches(isDisplayed()));
        TestUtils.waitView(allClaimsBut).check(matches(not(isDisplayed())));
        TestUtils.waitView(allClaimsCardsBlockConstraintLayout).check(matches(not(isDisplayed())));
    }
    @Step("Открыть страницу Love is all ")
    public void openOurMissionPage() {
        TestUtils.waitView(ourMissionImBut).perform(click());
    }
    @Step("Открыть All News ")
    public void clickAllNewsBut() {
        TestUtils.waitView(allNewsBut).perform(click());
    }
    @Step("Открыть All Claims")
    public void clickAllClaimsBut() {
        TestUtils.waitView(allClaimsBut).perform(click());
    }
    @Step("Нажатие на кнопку раскрытия новостей")
    public void newsExpandMaterialButtonClick() {
        TestUtils.waitView(newsExpandMaterialBut).perform(click());
    }
    @Step("Нажатие на кнопку раскрытия Сlaims")
    public void claimsExpandMaterialButtonClick() {
        TestUtils.waitView(claimsExpandMaterialBut).perform(click());
    }
    @Step("Открыть описание элемента Claim ")
    public void openClaimItemDescription(int position) {
        TestUtils.waitView(scrollView).check(matches(isEnabled()));
        TestUtils.waitView(scrollView).perform(swipeUp());
        SystemClock.sleep(3000);
        TestUtils.waitView(TestUtils.withRecyclerView(R.id.claim_list_recycler_view)
                .atPositionOnView(position, R.id.claim_list_card)).perform(click());
    }
    @Step("Добавить новую жалобу ")
    public void addNewClaimButtonClick() {
        TestUtils.waitView(addNewClaimBut).perform(click());
    }

}
