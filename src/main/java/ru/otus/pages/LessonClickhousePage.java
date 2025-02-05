package ru.otus.pages;

import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import ru.otus.annotations.Path;

@Path("/lessons/clickhouse")
public class LessonClickhousePage extends AbsBasePage {

  private final static String SWIPE_WRAPPER = ".swiper-wrapper";
  private final static String TEACHER_TILES = ".swiper-slide.sc-1s527z5-0.fxMjmR";
  private final static String TEACHERS_NAME = ".sc-1x9oq14-0.sc-1s527z5-1.gGtEnS.iiYkXk";
  private final static String TEACHERS_POPUP = ".swiper-slide.sc-1xbggqf-0.ejZqaz.swiper-slide-active";
  private final static String NEXT_BUTTON = ".sc-1bkbgbz-2.sc-1bkbgbz-3.dQreKk.iPzpLW";
  private final static String PREVIOUS_BUTTON = ".sc-1bkbgbz-2.sc-1bkbgbz-4.dQreKk.biZjNh";

  public LessonClickhousePage(Page page) {
    super(page);
  }

  public void checkTeachersBlockVisibility() {
    Locator teachersBlock = page.getByText("Преподаватели", new Page.GetByTextOptions().setExact(true));
    assertThat(teachersBlock.isVisible())
        .as("Teachers block is not visible")
        .isTrue();
  }

  public void checkTeacherTilesVisibility() {
    Locator teachersTiles = page.locator(TEACHER_TILES);
    assertThat(teachersTiles.count())
        .as("No teacher tiles found")
        .isGreaterThan(0);
  }

  public void swipeTeacherTilesLeft() {
    Locator scrollContainer = page.locator(SWIPE_WRAPPER).nth(0);
    var initialTransform = scrollContainer.evaluate("el => window.getComputedStyle(el).transform");
    scrollContainer.scrollIntoViewIfNeeded();

    BoundingBox box = scrollContainer.boundingBox();
    page.mouse().move(box.x + box.width - 10, box.y + (box.height / 2));
    page.mouse().down();
    page.mouse().move(box.x + 50, box.y + (box.height / 2));
    page.mouse().up();

    page.waitForTimeout(1000);

    var newTransform = scrollContainer.evaluate("el => window.getComputedStyle(el).transform");

    assertThat(initialTransform)
        .as("Scrolling did not work")
        .isNotEqualTo(newTransform);
  }

  public Locator getSpecificTeacherByTileNumber(int teachersTileNumber) {
    Locator teachersTiles = page.locator(TEACHER_TILES);
    Locator specificTeachersTile = teachersTiles.nth(--teachersTileNumber);
    if (!specificTeachersTile.isVisible()) {
      specificTeachersTile.waitFor();
    }
    return specificTeachersTile;
  }

  public String getSpecificTeachersName(Locator specificTeachersTile) {
    Locator specificTeachersName = specificTeachersTile.locator(TEACHERS_NAME);
    specificTeachersName.waitFor();
    return specificTeachersName.textContent();
  }

  public Locator click(Locator locator) {
    locator.click();
    return page.locator(TEACHERS_POPUP);
  }
  public void verifyTeachersPopupAndCompareName(Locator locator, String teachersNameToCompare) {
    assertThat(locator.isVisible())
        .as("Popup is not visible")
        .isTrue();
    assertThat(locator.textContent().contains(teachersNameToCompare))
        .as("Popup does not contain correct teacher info")
        .isTrue();
  }

  public void clickNextAndCheckTeacherTileIsChanged(Locator locator, String teacherName) {
    Locator nextButton = page.locator(NEXT_BUTTON);
    nextButton.click();
    assertThat(locator.textContent().contains(teacherName))
        .as("Next teacher opened")
        .isFalse();
  }

  public void clickPreviousAndVerifyPreviousTeacher(Locator locator, String teacherName) {
    Locator prevButton = page.locator(PREVIOUS_BUTTON);
    prevButton.click();
    assertThat(locator.textContent().contains(teacherName))
        .as("Previous teacher did not open correctly")
        .isTrue();
  }
}
