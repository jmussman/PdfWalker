// IParseArguments
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.pdfwalker.support;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

public interface IParseArguments {

    List<Path> parse(String[] args, IOptions options) throws InvalidPathException;
}
