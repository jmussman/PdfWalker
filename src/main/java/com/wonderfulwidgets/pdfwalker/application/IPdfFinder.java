// IPdfFinder
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.pdfwalker.application;

import java.io.IOException;
import java.nio.file.Path;

public interface IPdfFinder {

    void searchFolder(Path root) throws IOException;
}
