package ru.iteco.fmhandroid.ui.steps;

import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.CustomRecyclerViewActions;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;


import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;

public class ClaimsPageSteps {
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();
    public Matcher<View> claimsItemDescription = withId(R.id.description_text_view);
    public Matcher<View> titleClaimField = allOf(withId(R.id.title_edit_text), withParent(withParent(withId(R.id.title_text_input_layout))));
    public Matcher<View> executorClaimField = withId(R.id.executor_drop_menu_auto_complete_text_view);
    public Matcher<View> dateClaimField = withId(R.id.date_in_plan_text_input_edit_text);
    public Matcher<View> timeClaimField = allOf(withHint("Time"), withParent(withParent(withId(R.id.time_in_plan_text_input_layout))));
    public Matcher<View> descriptionClaimField = allOf(withHint("Description"), withParent(withParent(withId(R.id.description_text_input_layout))));
    public Matcher<View> compatImageView = allOf(TestUtils.childAtPosition(
            TestUtils.childAtPosition(
                    withId(R.id.claim_list_card),
                    0),
            11));

    public Matcher<View> claimRecyclerList = withId(R.id.claim_list_recycler_view);

    //Элементы карточки заявки
    public Matcher<View> closeImBut = withId(R.id.close_image_button);
    public Matcher<View> statusProcessingImBut = withId(R.id.status_processing_image_button);

    public Matcher<View> toExecuteMenuItem = withText("To execute");
    public Matcher<View> throwOffMenuItem = withText("Throw off");
    public Matcher<View> cancelMenuItem = withText("Cancel");
    //Диалоговое окно изменения статуса заявки
    public Matcher<View> statusCommentTextInputField = allOf(withHint("Comment"), withId(R.id.editText));

    @Step("Получить вид и запросить элемент ")
    public ViewInteraction getItemClaimCompatImView(String title) {
        return TestUtils.waitView(allOf(compatImageView, hasSibling(withText(title))));
    }

    @Step("Статус коментария ")
    public void isStatusCommentDialog() {
        TestUtils.waitView(statusCommentTextInputField).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.okBut).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.cancelDeleteBut).check(matches(isDisplayed()));
    }

    @Step("Страница заявок ")
    public void isClaimsPage() {
        TestUtils.waitView(withText("Claims")).check(matches(isDisplayed()));
        TestUtils.waitView(withId(R.id.claim_list_recycler_view)).check(matches(isDisplayed()));
    }

    @Step("Форма заявок ")
    public void isClaimsForm() {
        TestUtils.waitView(withText("Creating")).check(matches(isDisplayed()));
        TestUtils.waitView(withText("Claims")).check(matches(isDisplayed()));
        TestUtils.waitView(titleClaimField).check(matches(isDisplayed()));
        TestUtils.waitView(executorClaimField).check(matches(isDisplayed()));
        TestUtils.waitView(dateClaimField).check(matches(isDisplayed()));
        TestUtils.waitView(timeClaimField).check(matches(isDisplayed()));
        TestUtils.waitView(descriptionClaimField).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.saveBut).check(matches(isDisplayed()));
        TestUtils.waitView(controlPanelSteps.cancelBut).check(matches(isDisplayed()));
    }

    @Step("Прокрутить к элементу в списке ")
    public ViewInteraction scrollToElementInRecyclerList(String description) {
        return TestUtils.waitView(claimRecyclerList).check(matches(isDisplayed()))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(description))));
    }

    @Step("открыть карточку заявки")
    public void openClaimCard(DataHelper.CreateClaim claim) {
        scrollToElementInRecyclerList(claim.getClaimName());
        getItemClaimCompatImView(claim.getClaimName()).perform(click());
    }

    @Step("Получить описание элемента заявки ")
    public ViewInteraction getClaimItemDescription() {
        return TestUtils.waitView(claimsItemDescription);
    }

    @Step("Статус Execute")
    public void setStatusExecute() {
        TestUtils.waitView(statusProcessingImBut).perform(click());
        TestUtils.waitView(toExecuteMenuItem).perform(click());
    }

    @Step("Заменить текст комментария статуса заявки")
    public void replaceClaimStatusCommentText(String comment) {
        TestUtils.waitView(statusCommentTextInputField).perform(replaceText(comment));
    }

    @Step("Статус отменен ")
    public void setStatusCanceled() {
        TestUtils.waitView(statusProcessingImBut).perform(click());
        TestUtils.waitView(cancelMenuItem).perform(click());
    }

    @Step("Нажать кнопку закрыть ")
    public void closeImButtonClick() {
        TestUtils.waitView(closeImBut).perform(click());
    }

    @Step("Статус открыт ")
    public void setStatusOpen() {
        TestUtils.waitView(statusProcessingImBut).perform(click());
        TestUtils.waitView(throwOffMenuItem).perform(click());
    }


}


