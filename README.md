![](.common/joels-private-stock.png?raw=true)

# PDF Walker

This program arose out of a security need: when auditing a system you may find that
some users will password-protect (encrypt) Adobe PDF files, and some will hide
the files by changing the name (do not assume the .pdf extension).
The core purpose of this program is to walk directories and files looking for
encrypted PDF files.
It only identifies them, it will not crack the encryption

### Program Usage

The program will walk any folders specified, defaulting to the current folder when
launched.

The basic use of the program is to look for encrypted PDF files and print out the path
to files found: _java pdfwalker [root folder] [root folder] ..._

By default the program only examines files with a .pdf extension.
The _--deepinspection_ option may be added on the command line to cause
the program to examine every regular file found while walking the directory
to see if it is a PDF file and check it.

The _--showall_ flag will cause the program to output the path to every PDF file
found.
It may be used in combination with the _--deepinspection_ flag.

### Execution

This project does not take the additional step of building a Microsoft Windows excetuable or a MacOS app.
That is left to the developer cloning this repository if so desired.

The MVN package task will create an "executable" jar file with dependencies in the
target folder.
Execute the program with _java -jar \<jar file\> [folders or options]._

## License

This code is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the 
[License](./LICENSE.md) (or at the [GNU Licenses](https://www.gnu.org/licenses/licenses.html#AGPL) web page), or
(at your option) any later version. 

### Attribution

This code depends on the iText7 package which is also under the GNU AGPL license, you can find the project
at [Github - iText7](https://github.com/itext/itext7).

## Software Configuration

This project targets Java 8 and above, although retargeting to earlier versions
of Java should be possible (you do the work on dependencies).
The tests are built with JUnit 5 (Jupiter), Mockito, and Cucumber.

<hr>
Copyright © 2022 Joel A Mussman. All rights reserved.