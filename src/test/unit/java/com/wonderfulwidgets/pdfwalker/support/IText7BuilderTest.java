// IText7BuilderTest
// Copyright © 2022 Joel A Mussman. All rights reserved.
//
// The only thing to test in the class is that the path is passed to the constructor. The only way to test that
// with a real PdfReader is to pass it a bad path and look at the exception. So, we don't handle exceptions
// as Jupiter normally does, we catch it and look at it.
//

package com.wonderfulwidgets.pdfwalker.support;

import java.io.IOException;
import com.itextpdf.kernel.pdf.PdfReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IText7BuilderTest {

    @Test
    public void checkPathIsPreserved() {

        String myPath = "/doesnotexist.pdf";
        IIText7Builder pdfReaderBuilder = new IText7Builder();

        IOException thrown = assertThrows(IOException.class, () -> {

            PdfReader pdfReader = pdfReaderBuilder.buildPdfReader(myPath);
        });

        assertTrue(thrown.getMessage().contains(myPath));
    }
}
