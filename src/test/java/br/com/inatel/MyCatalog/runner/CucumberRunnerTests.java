package br.com.inatel.MyCatalog.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Class to run the feature files
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = {"src/test/resources/Features"}, glue = {"br.com.inatel.MyCatalog.definitions"})
public class CucumberRunnerTests {
}
