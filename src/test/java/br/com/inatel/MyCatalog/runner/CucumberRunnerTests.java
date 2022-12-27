package br.com.inatel.MyCatalog.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = {"src/test/resources/Features"}, glue = { "br.com.inatel.MyCatalog.definitions"})
public class CucumberRunnerTests {
}
