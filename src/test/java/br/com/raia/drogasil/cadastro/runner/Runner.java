package br.com.raia.drogasil.cadastro.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/",glue ="br.com.raia.drogasil.cadastro.steps", plugin = {
		"pretty", "html:target/report-html" },monochrome = true, snippets = SnippetType.CAMELCASE, dryRun = false)
public class Runner {         
  
}
 