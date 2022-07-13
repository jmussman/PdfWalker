// ParseArgumentsTest
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.pdfwalker.support;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParseArgumentsTest {

    @Mock
    IOptions optionsMock;
    ParseArguments parseArguments;

    @BeforeEach
    public void beforeEach() {

        parseArguments = new ParseArguments();
    }

    @Test
    public void optionsLeftUntouched() {

        String[] args = { "./one", "./two" };

        parseArguments.parse(args, optionsMock);

        verify(optionsMock, never()).setDeepInspection(true);
        verify(optionsMock, never()).setLogAllPdfFiles(true);
    }

    @Test
    public void deepInspectionMarked() {

        String[] args = { "--deepinspection", "./one", "./two" };

        parseArguments.parse(args, optionsMock);

        verify(optionsMock, times(1)).setDeepInspection(true);
        verify(optionsMock, never()).setLogAllPdfFiles(true);
    }

    @Test
    public void showAllMarked() {

        String[] args = { "--showall", "./one", "./two" };

        parseArguments.parse(args, optionsMock);

        verify(optionsMock, never()).setDeepInspection(true);
        verify(optionsMock, times(1)).setLogAllPdfFiles(true);
    }

    @Test
    public void optionPositionIrrelevant() {

        String[] args = { "./one", "--showall", "./two" };

        parseArguments.parse(args, optionsMock);

        verify(optionsMock, never()).setDeepInspection(true);
        verify(optionsMock, times(1)).setLogAllPdfFiles(true);
    }

    @Test
    public void allPathsReturned() {

        String[] args = { "./one", "--showall", "./two" };

        List<Path> results = parseArguments.parse(args, optionsMock);

        assertEquals(2, results.size())    ;
        assertTrue(results.contains(Paths.get(args[0])));
        assertTrue(results.contains(Paths.get(args[2])));
    }

    @Test
    public void badPathRejected() {

        String[] args = { "\0", "./two" };

        assertThrows(InvalidPathException.class, () -> parseArguments.parse(args, optionsMock));
    }
}