// IFilesUtility
// Copyright © Joel A Mussman. All rights reserved.
//
// The static methods in the Files class are a fundamental problem. First, they introduce a direct dependency in any
// class that uses them. Second, they are very difficult to mock for tests. This class is an injectable class which
// proxies the functionality of java.nio.file.Files.
//

package com.wonderfulwidgets.pdfwalker.support;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface IFilesUtility {

    boolean isRegularFile(Path path);
    Stream<Path> walk(Path path) throws IOException;
}
