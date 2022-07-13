// Files
// Copyright © 2022 Joel A Mussman. All rights reserved.
//
// This class is the production implementation of IFilesUtility to provide an injectable class that acts
// as a proxy for java.nio.file.Files.
//

package com.wonderfulwidgets.pdfwalker.support;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FilesUtility implements IFilesUtility {

    public boolean isRegularFile(Path path) {

        return java.nio.file.Files.isRegularFile(path);
    }

    public Stream<Path> walk(Path path) throws IOException {

        return java.nio.file.Files.walk(path);
    }
}
