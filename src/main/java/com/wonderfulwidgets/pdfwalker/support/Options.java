// Options
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.pdfwalker.support;

public class Options implements IOptions {

    private boolean deepInspection;
    private boolean logAllPdfFiles;

    public boolean isDeepInspection() {

        return deepInspection;
    }

    public void setDeepInspection(boolean deepInspection) {

        this.deepInspection = deepInspection;
    }

    public boolean isLogAllPdfFiles() {

        return logAllPdfFiles;
    }

    public void setLogAllPdfFiles(boolean logAllPdfFiles) {

        this.logAllPdfFiles = logAllPdfFiles;
    }
}