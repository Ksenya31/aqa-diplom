package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.DataHelper.authInfo;

import android.os.RemoteException;

import androidx.test.espresso.PerformException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.data.TestUtils;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsPageSteps;
import ru.iteco.fmhandroid.ui.steps.MainPageSteps;
import ru.iteco.fmhandroid.ui.steps.NewsPageSteps;

@RunWith(AllureAndroidJUnit4.class)

public class FilterNewsPageTest extends BaseTest {
    private UiDevice device;

    private static AuthSteps authSteps = new AuthSteps();
    private static MainPageSteps mainPageSteps = new MainPageSteps();
    private static NewsPageSteps newsPageSteps = new NewsPageSteps();
    private static FilterNewsPageSteps filterNewsPageSteps = new FilterNewsPageSteps();
    private static ControlPanelSteps controlPanelSteps = new ControlPanelSteps();


    LocalDateTime today = LocalDateTime.now();

    @Before
    public void logoutCheck() throws RemoteException {
        device =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.setOrientationNatural();
        try {
            authSteps.isAuthScreen();
        } catch (PerformException e) {
            mainPageSteps.clickLogOutBut();
        }
        authSteps.authWithValidData(authInfo());
        mainPageSteps.isMainPage();
        mainPageSteps.openNewsPageThroughTheMainMenu();
    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Обьявление")
    public void shouldFilterTheNewsWithCategoryAnnouncement() {
        DataHelper.CreateNews firstAnnouncementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();
        DataHelper.CreateNews secondAnnouncementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();
        DataHelper.CreateNews thirdAnnouncementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();
        DataHelper.CreateNews forthBirthdayNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryBirthday()).withDueDate(today).build();
        DataHelper.CreateNews fifthBirthdayNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryBirthday()).withDueDate(today).build();
        DataHelper.CreateNews sixthBirthdayNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryBirthday()).withDueDate(today).build();

        String categoryAnnouncement = "Объявление";
        String publishDateStartExpected = TestUtils.getDateToString(today);
        String publishDateEndExpected = TestUtils.getDateToString(today.plus(1, ChronoUnit.DAYS));

        newsPageSteps.openControlPanel();//Создание новостей
        controlPanelSteps.createNews(firstAnnouncementNews, secondAnnouncementNews, thirdAnnouncementNews,
                forthBirthdayNews, fifthBirthdayNews, sixthBirthdayNews);
        controlPanelSteps.isControlPanel(); //Переходим в раздел Новости
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        newsPageSteps.openFilterNews();//Открываем форму фильтра и заполняем её
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(categoryAnnouncement, today, today.plus(1, ChronoUnit.DAYS));
        //Проверяем, что в полях формы отображаются введенные данные
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryAnnouncement)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        filterNewsPageSteps.filterNewsButtonClick();  //Включаем фильтрацию
        //Проверка
        newsPageSteps.isNewsPage();
        controlPanelSteps.checkNewsIsPresent(firstAnnouncementNews);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории День рождения")
    public void shouldFilterTheNewsWithCategoryBirthday() {
        DataHelper.CreateNews firstAnnouncementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();
        DataHelper.CreateNews secondAnnouncementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();
        DataHelper.CreateNews thirdAnnouncementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();
        DataHelper.CreateNews forthBirthdayNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryBirthday()).withDueDate(today).build();
        DataHelper.CreateNews fifthBirthdayNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryBirthday()).withDueDate(today).build();
        DataHelper.CreateNews sixthBirthdayNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryBirthday()).withDueDate(today).build();

        String categoryBirthday = "День рождения";
        String publishDateStartExpected = TestUtils.getDateToString(today);
        String publishDateEndExpected = TestUtils.getDateToString(today.plus(1, ChronoUnit.DAYS));
        controlPanelSteps.createNews(firstAnnouncementNews, secondAnnouncementNews, thirdAnnouncementNews,
                forthBirthdayNews, fifthBirthdayNews, sixthBirthdayNews);
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(categoryBirthday,
                today, today.plus(1, ChronoUnit.DAYS));
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryBirthday)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка
        newsPageSteps.isNewsPage();
        controlPanelSteps.checkNewsIsPresent(forthBirthdayNews);
    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Зарплата")
    public void shouldFilterTheNewsWithCategorySalary() {
        DataHelper.CreateNews firstSalaryNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategorySalary()).withDueDate(today).build();
        DataHelper.CreateNews secondSalaryNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategorySalary()).withDueDate(today).build();
        DataHelper.CreateNews thirdSalaryNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategorySalary()).withDueDate(today).build();
        DataHelper.CreateNews forthTradeUnionNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryTradeUnion()).withDueDate(today).build();
        DataHelper.CreateNews fifthTradeUnionNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryTradeUnion()).withDueDate(today).build();
        DataHelper.CreateNews sixthTradeUnionNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryTradeUnion()).withDueDate(today).build();

        String categorySalary = "Зарплата";
        String publishDateStartExpected = TestUtils.getDateToString(today);
        String publishDateEndExpected = TestUtils.getDateToString(today.plus(1, ChronoUnit.DAYS));
        controlPanelSteps.createNews(firstSalaryNews, secondSalaryNews, thirdSalaryNews,
                forthTradeUnionNews, fifthTradeUnionNews, sixthTradeUnionNews);

        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(categorySalary,
                today, today.plus(1, ChronoUnit.DAYS));
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categorySalary)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка
        newsPageSteps.isNewsPage();
        controlPanelSteps.checkNewsIsPresent(firstSalaryNews);
    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Профсоюз")
    public void shouldFilterTheNewsWithCategoryTradeUnion() {
        DataHelper.CreateNews firstSalaryNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategorySalary()).withDueDate(today).build();
        DataHelper.CreateNews secondSalaryNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategorySalary()).withDueDate(today).build();
        DataHelper.CreateNews thirdSalaryNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategorySalary()).withDueDate(today).build();
        DataHelper.CreateNews forthTradeUnionNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryTradeUnion()).withDueDate(today).build();
        DataHelper.CreateNews fifthTradeUnionNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryTradeUnion()).withDueDate(today).build();
        DataHelper.CreateNews sixthTradeUnionNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryTradeUnion()).withDueDate(today).build();

        String categoryTradeUnion = "Профсоюз";
        String publishDateStartExpected = TestUtils.getDateToString(today);
        String publishDateEndExpected = TestUtils.getDateToString(today.plus(1, ChronoUnit.DAYS));
        controlPanelSteps.createNews(firstSalaryNews, secondSalaryNews, thirdSalaryNews,
                forthTradeUnionNews, fifthTradeUnionNews, sixthTradeUnionNews);

        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(categoryTradeUnion,
                today, today.plus(1, ChronoUnit.DAYS));
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryTradeUnion)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        //Включаем фильтрацию
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка
        newsPageSteps.isNewsPage();
        controlPanelSteps.checkNewsIsPresent(forthTradeUnionNews);

    }

    @Test
    @DisplayName("Фильтрация новостей по Категории Праздник")
    public void shouldFilterTheNewsWithCategoryHoliday() {
        DataHelper.CreateNews firstHolidayNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryHoliday()).withDueDate(today).build();
        DataHelper.CreateNews secondHolidayNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryHoliday()).withDueDate(today).build();
        DataHelper.CreateNews thirdHolidayNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryHoliday()).withDueDate(today).build();
        DataHelper.CreateNews forthTradeUnionNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryTradeUnion()).withDueDate(today).build();
        DataHelper.CreateNews fifthTradeUnionNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryTradeUnion()).withDueDate(today).build();
        DataHelper.CreateNews sixthTradeUnionNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryTradeUnion()).withDueDate(today).build();

        String categoryHoliday = "Праздник";
        String publishDateStartExpected = TestUtils.getDateToString(today);
        String publishDateEndExpected = TestUtils.getDateToString(today.plus(1, ChronoUnit.DAYS));
        controlPanelSteps.createNews(firstHolidayNews, secondHolidayNews, thirdHolidayNews,
                forthTradeUnionNews,fifthTradeUnionNews,sixthTradeUnionNews
                /*forthMassageNews, fifthMassageNews, sixthMassageNews*/);
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(categoryHoliday,
                today, today.plus(1, ChronoUnit.DAYS));
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryHoliday)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка
        newsPageSteps.isNewsPage();
        controlPanelSteps.checkNewsIsPresent(firstHolidayNews);

    }

    @Test
    @DisplayName("Отмена поиска CANCEL в разделе Filter news")
    public void shouldNotFilterNewsByCategoryAnnouncement() {
        DataHelper.CreateNews firstAnnouncementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();
        DataHelper.CreateNews secondBirthdayNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryBirthday()).withDueDate(today).build();
        DataHelper.CreateNews thirdHolidayNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryHoliday()).withDueDate(today).build();
                String categoryAnnouncement = "Объявление";
        String publishDateStartExpected = TestUtils.getDateToString(today);
        String publishDateEndExpected = TestUtils.getDateToString(today.plus(1, ChronoUnit.DAYS));
        controlPanelSteps.createNews(firstAnnouncementNews, secondBirthdayNews, thirdHolidayNews);
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(categoryAnnouncement,
                today, today.plus(1, ChronoUnit.DAYS));
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryAnnouncement)));
        filterNewsPageSteps.getNewsFilterPublishDateStartField().check(matches(withText(publishDateStartExpected)));
        filterNewsPageSteps.getNewsFilterPublishDateEndField().check(matches(withText(publishDateEndExpected)));
        filterNewsPageSteps.cancelFilterNewsButtonClick();
        //Проверка, что фильтр не включился и отображаются последние введенные новости
        newsPageSteps.isNewsPage();
        controlPanelSteps.checkNewsIsPresent(firstAnnouncementNews);
        controlPanelSteps.checkNewsIsPresent(secondBirthdayNews);
        controlPanelSteps.checkNewsIsPresent(thirdHolidayNews);}


//БАГ, не должен искать не существующюю категорию
    @Test
    @DisplayName("Фильтрация новостей ввод категории отсутствующей во всплывающем списке")
    public void shouldShowAMessageSelectACategoryFromTheList() {
        String myCategoryTitle = "Несуществующаяя";
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        controlPanelSteps.replaceNewsCategoryText(myCategoryTitle);
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageSteps.newsItemPublishDateStartField,
                today);
        controlPanelSteps.okButtonClick();
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageSteps.newsItemPublishDateEndField,
                today.plus(1, ChronoUnit.DAYS));
        controlPanelSteps.okButtonClick();
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка, что появляется сообщение
        controlPanelSteps.checkToast("Invalid category. Select a category from the list.", true);
    }


    @Test
    @DisplayName("Фильтрация новостей по временному промежутку в будушем времени")
    public void shouldShowATextThereIsNothingHere() {
        String categoryAnnouncement = "Объявление";
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.fillingOutTheFilterNewsForm(categoryAnnouncement,
                today.plus(1, ChronoUnit.MONTHS),
                today.plus(2, ChronoUnit.MONTHS));
        filterNewsPageSteps.getNewsFilterCategoryField().check(matches(withText(categoryAnnouncement)));
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка отображения кнопка REFRESH
        newsPageSteps.isEmptyNewsList();
    }

//не стабильный тест, падает
    @Test
    @DisplayName("Фильтрация новостей только по Category")
    public void shouldShowAllActualNewsCategoryAnnouncement() {
        DataHelper.CreateNews firstAnnouncementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today.minus(1, ChronoUnit.MONTHS)).build();
        DataHelper.CreateNews secondAnnouncementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today.minus(10, ChronoUnit.DAYS)).build();
        DataHelper.CreateNews thirdAnnouncementNews = DataHelper.newsWithRandomNameAndDescription()
                .withCategory(DataHelper.getCategoryAnnouncement()).withDueDate(today).build();
        String categoryAnnouncement = "Объявление";
        controlPanelSteps.createNews(firstAnnouncementNews, secondAnnouncementNews, thirdAnnouncementNews);
        controlPanelSteps.isControlPanel();
        mainPageSteps.openNewsPageThroughTheMainMenu();
        newsPageSteps.isNewsPage();
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(categoryAnnouncement);
        filterNewsPageSteps.filterNewsButtonClick();
        newsPageSteps.isNewsPage();
        // проверка отображения новостей в категории Объявление
        controlPanelSteps.checkNewsIsPresent(firstAnnouncementNews);
        controlPanelSteps.checkNewsIsPresent(secondAnnouncementNews);
        controlPanelSteps.checkNewsIsPresent(thirdAnnouncementNews);
    }

    @Test
    @DisplayName("Фильтрация новостей выбор даты только в левом календаре")
    public void shouldShowMessageWrongPeriodPublishDateEndField() {
        String categoryAnnouncement = "Объявление";
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(categoryAnnouncement);
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageSteps.newsItemPublishDateStartField,
                today.minus(1, ChronoUnit.MONTHS));
        controlPanelSteps.okButtonClick();
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка
        filterNewsPageSteps.isMessageWrongPeriod();
    }

    @Test
    @DisplayName("Фильтрация новостей выбор даты в  правом календаре")
    public void shouldShowMessageWrongPeriodWithEmptyPublishDateStartField() {
        String categoryAnnouncement = "Объявление";
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        controlPanelSteps.selectANewsCategoryFromTheList(categoryAnnouncement);
        filterNewsPageSteps.setDateToDatePicker(filterNewsPageSteps.newsItemPublishDateEndField,
                today);
        controlPanelSteps.okButtonClick();
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка
        filterNewsPageSteps.isMessageWrongPeriod();
    }

    @Test
    @DisplayName("Фильтрация новостей без выбора категории и даты")
    public void shouFilteringNewsWithoutSelectingCategoryAndDate() {
        newsPageSteps.openFilterNews();
        filterNewsPageSteps.isFilterNewsForm();
        filterNewsPageSteps.filterNewsButtonClick();
        //Проверка
        newsPageSteps.isNewsPage();
    }

}
