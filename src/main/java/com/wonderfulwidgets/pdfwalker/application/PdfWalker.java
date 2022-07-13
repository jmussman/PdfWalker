// PdfWalker
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.pdfwalker.application;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.wonderfulwidgets.pdfwalker.support.*;

public class PdfWalker {

    private IOptions options;
    private IParseArguments parseArguments;
    private FilesUtility filesUtility;
    private IIText7Builder itext7Builder;
    private IPdfFinder pdfFinder;

    public PdfWalker() {

        options = new Options();
        parseArguments = new ParseArguments();
        filesUtility = new FilesUtility();
        itext7Builder = new IText7Builder();
        pdfFinder = new PdfFinder(options, filesUtility, itext7Builder, System.out, System.err);
    }

    public PdfWalker(IOptions options, IParseArguments parseArguments, IIText7Builder itext7Builder, IPdfFinder pdfFinder) {

        this.options = options;
        this.parseArguments = parseArguments;
        this.pdfFinder = pdfFinder;
        this.itext7Builder = itext7Builder;
    }

    public static void main(String[] args) {

        // Create the instance and run.

        try {

            PdfWalker pdfWalker = new PdfWalker();

            pdfWalker.run(args);
        }

        catch (Exception e) {

            System.exit(1);
        }
    }

    protected void run(String[] args) {

        // Setup the arguments

        try {

            List<Path> rootPaths = parseArguments.parse(args, options);

            if (rootPaths.size() == 0) {

                rootPaths.add(Paths.get("."));
            }

            for (Path rootPath : rootPaths) {

                try {

                    pdfFinder.searchFolder(rootPath);
                }

                catch (IOException e) {

                    System.err.println(e.getMessage());
                }
            }
        }

        catch (InvalidPathException e) {

            System.err.println(e.getMessage());
            throw e;
        }
    }
}
