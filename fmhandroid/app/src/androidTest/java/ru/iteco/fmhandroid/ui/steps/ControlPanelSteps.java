package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;


import android.view.View;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;


import org.hamcrest.Matcher;
import org.hamcrest.Matchers;


import java.time.LocalDateTime;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.CustomRecyclerViewActions;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;

public class ControlPanelSteps {

    NewsPageSteps newsPageSteps = new NewsPageSteps();


    public Matcher<View> addNewsImBut = withId(R.id.add_news_image_view);
    //Форма создания новости
    public Matcher<View> newsItemCategoryField = withId(R.id.news_item_category_text_auto_complete_text_view);
    public Matcher<View> newsItemTitleField = withId(R.id.news_item_title_text_input_edit_text);
    public Matcher<View> newsItemMaterialCardView = withId(R.id.news_item_material_card_view);
    public Matcher<View> newsItemPublishDateField = withId(R.id.news_item_publish_date_text_input_edit_text);
    public Matcher<View> newsItemPublishTimeField = withId(R.id.news_item_publish_time_text_input_edit_text);
    public Matcher<View> newsItemDescriptionField = withId(R.id.news_item_description_text_input_edit_text);
    public Matcher<View> switcherActive = withId(R.id.switcher);
    public Matcher<View> switcherNotActive = withText("Not active");
    public Matcher<View> saveBut = withId(R.id.save_button);
    public Matcher<View> cancelBut = withId(R.id.cancel_button);

    //Календарь и часы
    public Matcher<View> datePicker = isAssignableFrom(DatePicker.class);
    public Matcher<View> okBut = withId(android.R.id.button1);
    public Matcher<View> cancelDeleteBut = withId(android.R.id.button2);
    public Matcher<View> timePicker = isAssignableFrom(TimePicker.class);
    public Matcher<View> timePickerToggleMode = withContentDescription("Switch to text input mode for the time input.");
    public Matcher<View> inputHour = Matchers.allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
            TestUtils.childAtPosition(
                    Matchers.allOf(withClassName(is("android.widget.RelativeLayout")),
                            TestUtils.childAtPosition(
                                    withClassName(is("android.widget.TextInputTimePickerView")),
                                    1)),
                    0));
    public Matcher<View> inputMinute = Matchers.allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
            TestUtils.childAtPosition(
                    Matchers.allOf(withClassName(is("android.widget.RelativeLayout")),
                            TestUtils.childAtPosition(
                                    withClassName(is("android.widget.TextInputTimePickerView")),
                                    1)),
                    3));

    public Matcher<View> messageAboutDelete = withText("Are you sure you want to permanently delete the document? These changes cannot be reversed in the future.");
    public Matcher<View> newsRecyclerList = withId(R.id.news_list_recycler_view);
    public Matcher<View> categoryTextInputStartIcon = allOf(withId(R.id.text_input_start_icon), withParent(withParent(withParent(withId(R.id.news_item_category_text_input_layout)))));
    public Matcher<View> titleTextInputEndIcon = allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.news_item_title_text_input_layout))))));
    public Matcher<View> createDateTextInputEndIcon = allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.news_item_create_date_text_input_layout))))));
    public Matcher<View> createTimeInputEndIcon = allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.news_item_publish_time_text_input_layout))))));
    public Matcher<View> descriptionTextInputEndIcon = allOf(withId(R.id.text_input_end_icon), withParent(withParent(withParent(withParent(withId(R.id.news_item_description_text_input_layout))))));
    public Matcher<View> messageChangesWonTBeSaved = withText("The changes won't be saved, do you really want to log out?");
    public Matcher<View> editNewsMaterialButton = allOf(withId(R.id.edit_news_material_button), withParent(withParent(withParent(withParent(withId(R.id.container_list_news_include))))));

    public int newsItemTitleTextView = (R.id.news_item_title_text_view);


    public void openNewsCard(DataHelper.CreateNews news) {
        Allure.step("Открыть новость");
        checkNewsIsPresent(news);
        getItemNewsEditElement(news.getNewsName()).perform(click());
    }


    public ViewInteraction wrongСategoryToast(String text) {
        Allure.step("Всплывающее уведомление о неправильной категории");
        return onView(withText(text)).inRoot(new DataHelper.ToastMatcher());
    }

    public void isControlPanel() {
        Allure.step("Панель управления");
        TestUtils.waitView(withText("Control panel")).check(matches(isDisplayed()));
        TestUtils.waitView(addNewsImBut).check(matches(isDisplayed()));
    }

    public void isCreatingNewsForm() {
        Allure.step("Проверка формы Creating News");
        TestUtils.waitView(newsItemCategoryField).check(matches(isDisplayed()));
        TestUtils.waitView(newsItemTitleField).check(matches(isDisplayed()));
        TestUtils.waitView(newsItemPublishDateField).check(matches(isDisplayed()));
        TestUtils.waitView(newsItemPublishTimeField).check(matches(isDisplayed()));
        TestUtils.waitView(newsItemDescriptionField).check(matches(isDisplayed()));
        TestUtils.waitView(saveBut).check(matches(isDisplayed()));
        TestUtils.waitView(cancelBut).check(matches(isDisplayed()));
        TestUtils.waitView(switcherActive).check(matches(isDisplayed()));
    }

    public void isCardTestNews(String title, String description) {
        Allure.step("Проверка карточки новостей");
        TestUtils.waitView(newsItemCategoryField).check(matches(withText("Объявление")));
        TestUtils.waitView(newsItemTitleField).check(matches(withText(title)));
        TestUtils.waitView(newsItemDescriptionField).check(matches(withText(description)));
        TestUtils.waitView(saveBut).check(matches(isDisplayed()));
        TestUtils.waitView(cancelBut).check(matches(isDisplayed()));
        TestUtils.waitView(switcherActive).check(matches(isDisplayed()));
    }


    public void isCreatingTestNews() {
        Allure.step("Проверка карточки новостей");
        TestUtils.waitView(withText("Creating News")).check(matches(isDisplayed()));
        TestUtils.waitView(switcherActive).check(matches(isDisplayed()));
    }


    public void selectANewsCategoryFromTheList(String nameCategory) {
        Allure.step("Выбрать категорию новостей из списка");
        TestUtils.waitView(newsItemCategoryField).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withText(nameCategory)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed())).perform(click());

    }

    public void setDateToDatePicker(LocalDateTime date) {
        Allure.step("Установить дату в виджете выбора даты");
        TestUtils.waitView(newsItemPublishDateField).perform(click());
        TestUtils.waitView(datePicker).check(matches(isDisplayed()));
        TestUtils.waitView(datePicker).perform(setDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
    }

    public void setTimeToTimePicker(int hour, int minute) {
        Allure.step("Установить время в виджете выбора времени");
        TestUtils.waitView(timePicker).check(matches(isDisplayed()));
        TestUtils.waitView(timePicker).perform(setTime(hour, minute));
    }

    public void setTimeToTimePickerFromTheKeyboard(String hour, String minutes) {
        Allure.step("Установить время в виджете выбора времени с клавиатуры");
        TestUtils.waitView(timePicker).check(matches(isDisplayed()));
        TestUtils.waitView(timePickerToggleMode).perform(click());
        TestUtils.waitView(inputHour).check(matches(isDisplayed())).perform(replaceText(hour));
        TestUtils.waitView(inputMinute).check(matches(isDisplayed())).perform(replaceText(minutes));
        okButtonClick();
    }

    public void openNewsTimePicker() {
        Allure.step("Открыть виджет выбора времени новостей");
        TestUtils.waitView(newsItemPublishTimeField).perform(click());
    }

    public void openNews() {
        Allure.step("Открыть новость");
        TestUtils.waitView(newsItemMaterialCardView).perform(click());
    }


    public void setTimeToTimeField(LocalDateTime date) {
        Allure.step("Установить время");
        TestUtils.waitView(newsItemPublishTimeField).perform(click());
        setTimeToTimePicker(date.getHour(), date.getMinute());
        okButtonClick();
    }

    public void fillingOutTheFormCreatingNewsWithDate(DataHelper.CreateNews news) {
        Allure.step("Заполнение формы создания новости с указанием даты");
        TestUtils.waitView(newsItemTitleField).perform(replaceText(news.getNewsName()));
        setDateToDatePicker(news.getDueDate());
        TestUtils.waitView(okBut).perform(click());
        setTimeToTimeField(news.getDueDate());
        TestUtils.waitView(newsItemDescriptionField).perform(replaceText(news.getNewsDescription()));
    }

    public void replaceNewsTitleText(String title) {
        Allure.step("Заменить текст заголовка новости");
        TestUtils.waitView(newsItemTitleField).perform(replaceText(title));
    }

    public void replaceNewsDescriptionText(String description) {
        Allure.step("Заменить текст описания новости");
        TestUtils.waitView(newsItemDescriptionField).perform(replaceText(description));
    }

    public ViewInteraction getItemNewsDeleteElement(String title) {
        Allure.step("Элемент удаления новости");
        return TestUtils.waitView(allOf(withId(R.id.delete_news_item_image_view),
                withParent(withParent(allOf(withId(R.id.news_item_material_card_view),
                        withChild(withChild(withText(title))))))));
    }

    public ViewInteraction getItemNewsEditElement(String title) {
        Allure.step("Элемент добавления новости");
        return onView(allOf(withId(R.id.edit_news_item_image_view),
                withParent(withParent(allOf(withId(R.id.news_item_material_card_view),
                        withChild(withChild(withText(title))))))));
    }

    public ViewInteraction getItemNewsButViewElement(String title) {
        Allure.step("Элемент для просмотра новости");
        return onView(allOf(withId(R.id.news_item_material_card_view)));
    }

    public ViewInteraction getItemNewsDescriptionElement(String title) {
        Allure.step("Элемент описания новости");
        return onView(allOf(withId(R.id.news_item_description_text_view),
                withParent(withParent(allOf(withId(R.id.news_item_material_card_view),
                        withChild(withChild(withText(title))))))));
    }

    public void openEditNewsForm() {
        Allure.step("Открыть форму редактирования новости");
        TestUtils.waitView(editNewsMaterialButton).perform(click());
    }


    public void deleteItemNews(String title) {
        Allure.step("Удалить элемент новости");
        scrollToElementInRecyclerList(title);
        getItemNewsDeleteElement(title).check(matches(isDisplayed())).perform(click());
        TestUtils.waitView(messageAboutDelete).check(matches(isDisplayed()));
        TestUtils.waitView(okBut).perform(click());
    }

    public ViewInteraction scrollToElementInRecyclerList(String description) {
        Allure.step("Прокрутить к элементу в списке");
        return TestUtils.waitView(newsRecyclerList)
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(description)))));
    }


    public void checkNewsIsPresent(DataHelper.CreateNews news) {
        Allure.step("Проверить наличие новости");
        scrollToElementInRecyclerList(news.getNewsName()).check(matches(isDisplayed()));
    }

    public void checkNewsDoesNotPresent(DataHelper.CreateNews news) {
        Allure.step("Проверить отсутствие новости");
        getNewsRecyclerList()
                .check(matches(CustomRecyclerViewActions.RecyclerViewMatcher
                        .matchChildViewIsNotExist(newsItemTitleTextView, withText(news.getNewsName()))));
    }


    public void openNewsDescription(DataHelper.CreateNews news) {
        Allure.step("Открыть описание новости");
        getItemNewsButViewElement(news.getNewsName()).perform(click());
    }


    public void checkToast(String text, boolean visible) {
        Allure.step("Проверить всплывающее уведомление");
        if (visible) {
            wrongСategoryToast(text).check(matches(isDisplayed()));
        } else {
            wrongСategoryToast(text).check(matches(not(isDisplayed())));
        }
    }

    public void isWrongEmptyFormNews() {
        Allure.step("Ошибке пустая форма новости");
        TestUtils.waitView(categoryTextInputStartIcon).check(matches(isDisplayed()));
        TestUtils.waitView(titleTextInputEndIcon).check(matches(isDisplayed()));
        TestUtils.waitView(createDateTextInputEndIcon).check(matches(isDisplayed()));
        TestUtils.waitView(createTimeInputEndIcon).check(matches(isDisplayed()));
        TestUtils.waitView(descriptionTextInputEndIcon).check(matches(isDisplayed()));
    }

    public void isDialogWindowMessageSavingFailed() {
        Allure.step("Сообщение о невозможности сохранения");
        TestUtils.waitView(withText("Saving failed. Try again later.")).check(matches(isDisplayed()));
        TestUtils.waitView(okBut).check(matches(isDisplayed()));
    }

    public void openCreatingNewsForm() {
        Allure.step("Открыть форму создания новости");
        TestUtils.waitView(addNewsImBut).perform(click());
    }

    public ViewInteraction getNewsItemTitle() {
        Allure.step("Получить заголовок новости");
        return TestUtils.waitView(newsItemTitleField);
    }

    public void saveButtonClick() {
        Allure.step("Нажать сохранить");
        TestUtils.waitView(saveBut).perform(click());
    }

    public void cancelButtonClick() {
        Allure.step("Нажать отмена");
        TestUtils.waitView(cancelBut).perform(click());
    }

    public ViewInteraction getMessageChangesWonTBeSaved() {
        Allure.step("Сообщение о несохранении");
        return TestUtils.waitView(messageChangesWonTBeSaved);
    }

    public void okButtonClick() {
        Allure.step("Нажать ок");
        TestUtils.waitView(okBut).perform(click());
    }

    public void cancelDeleteButtonClick() {
        Allure.step("Отмена удаления");
        TestUtils.waitView(cancelDeleteBut).perform(click());
    }

    public ViewInteraction getNewsRecyclerList() {
        Allure.step("Получить список новостей");
        return TestUtils.waitView(newsRecyclerList);
    }

    public void replaceNewsCategoryText(String category) {
        Allure.step("Заменить текст категории новости");
        TestUtils.waitView(newsItemCategoryField).perform(replaceText(category));
    }

    public void switchNewsStatus() {
        Allure.step("Изменить статус новости");
        TestUtils.waitView(switcherActive).perform(click());
    }

    public ViewInteraction getSwitcherNoteActive() {
        Allure.step("Переключение  Note Active");
        return TestUtils.waitView(switcherNotActive);
    }

    public ViewInteraction getNewsItemPublishDate() {
        Allure.step("Получить дату публикации новости");
        return TestUtils.waitView(newsItemPublishDateField);
    }

    public ViewInteraction getNewsItemPublishTime() {
        Allure.step("Получить время публикации новости");
        return TestUtils.waitView(newsItemPublishTimeField);
    }

    public ViewInteraction getNewsItemCategory() {
        Allure.step("Получить категорию новости");
        return TestUtils.waitView(newsItemCategoryField);
    }

    public ViewInteraction getNewsItemDescription() {
        Allure.step("Получить описание новости");
        return TestUtils.waitView(newsItemDescriptionField);
    }

    public ViewInteraction getMessageAboutDelete() {
        Allure.step("Сообщение об удалении");
        return TestUtils.waitView(messageAboutDelete);
    }

    public void creatingNews(DataHelper.CreateNews news) {
        Allure.step("Создать новость");
        TestUtils.waitView(addNewsImBut).perform(click());
        selectANewsCategoryFromTheList(news.getNewsCategory());
        fillingOutTheFormCreatingNewsWithDate(news);
        TestUtils.waitView(saveBut).perform(click());
    }

    public void createNews(DataHelper.CreateNews... array) {
        Allure.step("Создать новость");
        newsPageSteps.openControlPanel();
        for (DataHelper.CreateNews news : array) {
            creatingNews(news);
        }
    }

}
