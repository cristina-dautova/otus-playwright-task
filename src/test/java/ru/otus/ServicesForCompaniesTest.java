package ru.otus;

import com.google.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.extensions.junit.UIExtensions;
import ru.otus.pages.ServicesPage;

@ExtendWith(UIExtensions.class)
public class ServicesForCompaniesTest {

  @Inject
  private ServicesPage servicesPage;

  @Test
  void testMoreInfoButtonFunctionality() {

    servicesPage.open();
    servicesPage.checkNoCourseFoundBlockVisibility()
        .clickMoreInfoButton()
        .checkCustomCoursesForBusinessPageIsOpened()
        .checkTrainingDirectionsAreVisible()
        .clickTrainingDirectionAndVerifyIfOpenedPageIsCorrect(1);
  }
}
