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
That is left to the developer using this repository.

The MVN package task will create an "executable" jar file with dependencies in the
target folder.
Execute the program with _java -jar <jar file> [folders or options]._

## License

The code is licensed under the MIT license. You may use and modify all or part of it as you choose, as long as attribution to the source is provided per the license. See the details in the [license file](./LICENSE.md) or at the [Open Source Initiative](https://opensource.org/licenses/MIT)

### Software Configuration

This project targets Java 17 and above, although retargeting to earlier versions
of Java should be possible (you do the work on dependencies). The JUnit tests are built with JUnit 5 and Jupiter.

<hr>
Copyright © 2022 Joel A Mussman. All rights reserved.