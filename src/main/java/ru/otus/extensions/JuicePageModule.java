package ru.otus.extensions;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.microsoft.playwright.*;
import ru.otus.pages.LessonClickhousePage;
import ru.otus.pages.MainPage;
import ru.otus.pages.ServicesPage;

public class JuicePageModule extends AbstractModule {

  private Page page;
  private BrowserContext context;

  public JuicePageModule() {
    Playwright playwright = Playwright.create();
    Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    BrowserContext context = browser.newContext();

    context.tracing().start(new Tracing.StartOptions()
        .setScreenshots(true)
        .setSnapshots(true)
        .setSources(true));

    this.context = context;
    this.page = context.newPage();
  }

  @Provides
  @Singleton
  public BrowserContext getContext() {
    return context;
  }

  @Provides
  @Singleton
  public MainPage getMainPage() {
    return new MainPage(page);
  }

  @Provides
  @Singleton
  public LessonClickhousePage getLessonPage() {
    return new LessonClickhousePage(page);
  }

  @Provides
  @Singleton
  public ServicesPage getServicesPage() {
    return new ServicesPage(page);
  }
}
