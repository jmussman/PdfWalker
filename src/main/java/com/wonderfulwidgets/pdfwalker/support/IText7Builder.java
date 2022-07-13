// IText7Builder
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.pdfwalker.support;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import java.io.IOException;

public class IText7Builder implements IIText7Builder {

    public PdfReader buildPdfReader(String path) throws IOException {

        return new PdfReader(path);
    }

    public PdfDocument buildPdfDocument(PdfReader reader) {

        return new PdfDocument(reader);
    }
}
