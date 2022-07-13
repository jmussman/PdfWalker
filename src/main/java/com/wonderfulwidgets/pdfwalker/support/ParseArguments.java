// ParseArguments
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.pdfwalker.support;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParseArguments implements IParseArguments {

    public List<Path> parse(String[] args, IOptions options) throws InvalidPathException, IllegalArgumentException {

        // Scan the args logging and stripping out options.

        List<Path> pathnames = new ArrayList<>();

        for (String arg : args) {

            if (arg.startsWith("--")) {

                switch (arg.toLowerCase()) {

                    case "--deepinspection":
                        options.setDeepInspection(true);
                        break;

                    case "--showall":
                        options.setLogAllPdfFiles(true);
                        break;

                    default:
                        throw new IllegalArgumentException("Illegal argument " + arg);
                }

            } else {

                pathnames.add(Paths.get(arg));
            }
        }

        return pathnames;
    }
}
