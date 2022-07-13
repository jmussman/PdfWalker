// Options
// Copyright © 2022 Joel A Mussman. All rights reserved.
//
// Options are command-line flags that
//

package com.wonderfulwidgets.pdfwalker.support;

public interface IOptions {

    boolean isDeepInspection();
    void setDeepInspection(boolean deepInspection);
    boolean isLogAllPdfFiles();
    void setLogAllPdfFiles(boolean logAllPdfFiles);
}
