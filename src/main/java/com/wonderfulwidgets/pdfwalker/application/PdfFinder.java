// PdfFinder
// Copyright © 2022 Joel A Mussman. All rights reserved.
//
// This class walks a directory structure and checks the files looking for encrypted PDFs. IText7 and java.nio.Files
// are NOT set up very well to work in an environment with dependency injection, so look to the IIText7Builder and
// IFilesUtility interfaces to wrap the functionality to inject classes with helper functions into this class.
//

package com.wonderfulwidgets.pdfwalker.application;

import com.itextpdf.kernel.exceptions.BadPasswordException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

import com.wonderfulwidgets.pdfwalker.support.IFilesUtility;
import com.wonderfulwidgets.pdfwalker.support.IIText7Builder;
import com.wonderfulwidgets.pdfwalker.support.IOptions;

public class PdfFinder implements IPdfFinder {

    private IOptions options;
    private IFilesUtility filesUtility;
    private PrintStream outputStream;
    private PrintStream errorStream;
    private IIText7Builder itext7Builder;

    public PdfFinder(IOptions options, IFilesUtility filesUtility, IIText7Builder itext7Builder, PrintStream outputStream, PrintStream errorStream) {

        this.options = options;
        this.filesUtility = filesUtility;
        this.itext7Builder = itext7Builder;
        this.outputStream = outputStream;
        this.errorStream = errorStream;
    }

    public void searchFolder(Path root) throws IOException {

        filesUtility.walk(root).forEach(path -> checkFile(path));
    }

    protected void checkFile(Path path) {

        try {

            if (options.isDeepInspection() || path.toString().toLowerCase().endsWith(".pdf")) {

                if (filesUtility.isRegularFile(path)) {

                    PdfReader reader = itext7Builder.buildPdfReader(path.toString());
                    PdfDocument doc = itext7Builder.buildPdfDocument(reader);

                    // If this worked, it's an unencrypted PDF document. Look for the BadPasswordException below if
                    // if was password protected, or the IOException if it was not a true PDF file.

                    // Log all PDF files if instructed to do so.

                    if (options.isLogAllPdfFiles()) {

                        outputStream.println(path.toString());
                    }
                }
            }
        }

        catch (BadPasswordException e) {

            outputStream.println(path.toString());
        }

        catch (com.itextpdf.io.exceptions.IOException e) {

            // Ignore non-PDF files on deep inspection. This is a crappy way to do it, itext7 should
            // be returning a specific exception for this issue, not a message embedded in IOException.

            if (!e.getMessage().equals("PDF header not found.")) {

                errorStream.println(e.getMessage());
            }
        }

        catch (IOException e) {

            errorStream.println(e.getMessage());
        }
    }
}
