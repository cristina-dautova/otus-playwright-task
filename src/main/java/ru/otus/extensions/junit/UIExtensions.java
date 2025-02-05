package ru.otus.extensions.junit;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ru.otus.extensions.JuicePageModule;

import java.nio.file.Paths;

public class UIExtensions implements BeforeEachCallback, AfterEachCallback {

  private Injector injector;
  @Override
  public void beforeEach(ExtensionContext extensionContext) {
    injector = Guice.createInjector(new JuicePageModule());
    injector.injectMembers(extensionContext.getTestInstance().get());
  }

  @Override
  public void afterEach(ExtensionContext extensionContext) {
    injector.getProvider(BrowserContext.class)
        .get()
        .tracing()
        .stop(new Tracing.StopOptions().setPath(Paths.get("trace.zip")));
  }
}
