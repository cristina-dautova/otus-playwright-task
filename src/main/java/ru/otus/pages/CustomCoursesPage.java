package ru.otus.pages;

import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ru.otus.annotations.Path;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/custom_courses")
public class CustomCoursesPage extends AbsBasePage {

  private final static String PAGE_TITLE = "Разработка индивидуальных программ обучения для бизнеса";
  private final static String TRAINING_DIRECTIONS = ".tn-atom__sbs-anim-wrapper.js-sbs-anim-trigger_hover";
  private final static String COURSES_DEVELOPMENT_FOR_BUSINESS =
      "text=" + PAGE_TITLE;

  private final static String SIX_TRAINING_DIRECTIONS =
      "text=6 направлений обучения, более 130 курсов";


  public CustomCoursesPage(Page page) {
    super(page);
  }

  public CustomCoursesPage checkCustomCoursesForBusinessPageIsOpened() {
    Locator customCoursesForBusiness = page.locator(COURSES_DEVELOPMENT_FOR_BUSINESS);

    assertThat(customCoursesForBusiness.isVisible())
        .as("'Custom courses' page is not opened")
        .isTrue();
    assertThat(page.title())
        .as("Page title comparison")
        .isEqualTo(PAGE_TITLE);
    return this;
  }

  public CustomCoursesPage checkTrainingDirectionsAreVisible() {
    Locator sixTrainingDirections = page.locator(SIX_TRAINING_DIRECTIONS);
    sixTrainingDirections.scrollIntoViewIfNeeded();
    List<Locator> trainingDirections = page.locator(TRAINING_DIRECTIONS).all();
    assertThat(trainingDirections.size())
        .as("Training directions are not visible")
        .isEqualTo(6);
    return this;
  }

  public void clickTrainingDirectionAndVerifyIfOpenedPageIsCorrect(int trainingDirectionIndex) {
    var trainingDirection = page.locator(TRAINING_DIRECTIONS).nth(--trainingDirectionIndex);
    var linkFromAttribute = trainingDirection.locator("a").getAttribute("href");
    var trainingDirectionName = retrieveLastPartFromUri(linkFromAttribute);

    trainingDirection.click();
    page.waitForLoadState();
    page.waitForURL(Pattern.compile(".*categories=.*"));
    var trainingDirectionNameFromNewPage = retrieveLastPartFromUri(page.url());

    assertThat(trainingDirectionName)
        .as("Training direction is not correct")
        .isEqualTo(trainingDirectionNameFromNewPage);
  }

  private static String retrieveLastPartFromUri(String link) {
    Pattern pattern = Pattern.compile("(?:[=/])([^=/]+)$");
      Matcher matcher = pattern.matcher(link);
      if (matcher.find()) {
        return matcher.group(1);
      }
    return null;
  }
}
