package com.wonderfulwidgets.pdfwalker.featuresteps;// ApplicationFeatureSteps
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wonderfulwidgets.pdfwalker.application.PdfWalker;

public class ApplicationSteps {

    String resourceDir;
    String encryptedFileA;
    String encryptedFileB;
    ByteArrayOutputStream output;

    @Before
    public void setupSteps() {

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @Given(".\\/test-classes\\/resources has files to check")
    public void test_classes_resources_has_files_to_check() {

        // Dynamically locate the test-classes resource folder as the base for scanning PDFs.

        String resourceName = "HelloWorld.pdf";
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(resourceName);
        File resourceFile = new File(url.getFile());
        String absolutePath = resourceFile.getAbsolutePath();

        resourceDir = absolutePath.substring(0, absolutePath.lastIndexOf(resourceName));
        encryptedFileA = resourceDir + "HelloWorldEncrypted.pdf";
        encryptedFileB = resourceDir + "HelloWorldEncrypted.hide";
    }

    @When("pdfwalker --deepinspection .\\/test-classes\\/resources")
    public void pdfwalker_deepinspection_test_classes_resources() {

        String[] args = { "--deepinspection", resourceDir };

        PdfWalker.main(args);
    }

    @Then("result should be two encrypted file paths are printed")
    public void result_should_be_two_encrypted_file_paths_are_printed() {

        String out = output.toString();

        assertTrue(out.contains(encryptedFileA));
        assertTrue(out.contains(encryptedFileB));
    }
}