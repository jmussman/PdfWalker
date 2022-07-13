// PdfWalkerIntegrationTest
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.pdfwalker.application;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import com.wonderfulwidgets.pdfwalker.support.FilesUtility;
import com.wonderfulwidgets.pdfwalker.support.IText7Builder;
import com.wonderfulwidgets.pdfwalker.support.Options;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PdfWalkerIntegrationIT {

    @Mock
    Options optionsMock;
    @Mock PrintStream outStreamMock;
    @Mock PrintStream errorStreamMock;
    @Captor ArgumentCaptor<String> streamCaptor;
    String resourceDir;
    String encryptedFileA;
    String encryptedFileB;
    PdfFinder pdfFinder;

    @BeforeEach
    public void beforeEach() {

        // Dynamically locate the test-classes resource folder as the base for scanning PDFs.

        String resourceName = "HelloWorld.pdf";
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(resourceName);
        File resourceFile = new File(url.getFile());
        String absolutePath = resourceFile.getAbsolutePath();

        resourceDir = absolutePath.substring(0, absolutePath.lastIndexOf(resourceName));
        encryptedFileA = resourceDir + "HelloWorldEncrypted.pdf";
        encryptedFileB = resourceDir + "HelloWorldEncrypted.hide";

        IText7Builder itext7Builder = new IText7Builder();
        pdfFinder = new PdfFinder(optionsMock, new FilesUtility(), itext7Builder, outStreamMock, errorStreamMock);
    }

    @Test
    public void checkShallowFileWalker() throws IOException {

        when(optionsMock.isDeepInspection()).thenReturn(false);

        pdfFinder.searchFolder(Paths.get(resourceDir));

        verify(outStreamMock, times(1)).println(encryptedFileA);
    }

    @Test
    public void checkDeepFileWalker() throws IOException {

        when(optionsMock.isDeepInspection()).thenReturn(true);

        pdfFinder.searchFolder(Paths.get(resourceDir));

        verify(outStreamMock, times(2)).println(streamCaptor.capture());

        List<String> captures = streamCaptor.getAllValues();

        assertTrue(captures.contains(encryptedFileA));
        assertTrue(captures.contains(encryptedFileB));
    }
}