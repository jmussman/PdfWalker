// IIText7Builder
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.pdfwalker.support;

import java.io.IOException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;

public interface IIText7Builder {

    PdfReader buildPdfReader(String path) throws IOException;
    PdfDocument buildPdfDocument(PdfReader reader) throws com.itextpdf.io.exceptions.IOException;
}
