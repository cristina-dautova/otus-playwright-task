package ru.otus.pages;

import com.microsoft.playwright.Page;
import ru.otus.annotations.Path;

@Path("/")
public class MainPage extends AbsBasePage {
  public MainPage(Page page) {
    super(page);
  }
}
