package ru.otus.pages;

import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ru.otus.annotations.Path;

@Path("/uslugi-kompaniyam")
public class ServicesPage extends AbsBasePage {

  private final static String NO_COURSE_FOUND = "text=Не нашли нужный курс?";
  private final static String MORE_INFO_BUTTON = "text=Подробнее";
  public ServicesPage(Page page) {
    super(page);
  }

  public ServicesPage checkNoCourseFoundBlockVisibility() {
    Locator noCourseFoundBlock = page.locator(NO_COURSE_FOUND);
    noCourseFoundBlock.waitFor();
    noCourseFoundBlock.scrollIntoViewIfNeeded();
    assertThat(noCourseFoundBlock.isVisible())
        .as("'No course found' block is not visible")
        .isTrue();
    return this;
  }

  public CustomCoursesPage clickMoreInfoButton() {
    Locator moreInfoButton = page.locator(MORE_INFO_BUTTON);
    Page customCoursesPage = page.waitForPopup(moreInfoButton::click);
    customCoursesPage.waitForLoadState();
    return new CustomCoursesPage(customCoursesPage);
  }
}
