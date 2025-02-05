package ru.otus.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import ru.otus.annotations.Path;

public abstract class AbsBasePage {

  private String baseUrl = System.getProperty("base.url", "https://otus.ru");

  protected Page page;

  public AbsBasePage(Page page) {
    this.page = page;
  }

  protected String getPath() {

    Class clazz = this.getClass();
    if(clazz.isAnnotationPresent(Path.class)) {
      Path path = (Path) clazz.getDeclaredAnnotation(Path.class);
      return path.value();
    }
    return null;
  }

  public void open() {
    page.navigate(baseUrl + getPath());
    page.waitForLoadState(LoadState.DOMCONTENTLOADED);
  }
}
