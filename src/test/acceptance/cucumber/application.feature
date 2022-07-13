# Application.feature
# Copyright © 2022 Joel A Mussman. All rights reserved.

Feature: Test program arguments

  # The integration tests put everything together from PdfWalker forward, they
  # just do not cover invoking PdfWalker from main which is the sole acceptance test.

  Scenario: Program runs and passes arguments
    Given ./test-classes/resources has files to check
    When pdfwalker --deepinspection ./test-classes/resources
    Then result should be two encrypted file paths are printed
