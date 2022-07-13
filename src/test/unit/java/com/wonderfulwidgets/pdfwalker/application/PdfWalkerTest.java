// PdfWalkerTest
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.pdfwalker.application;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.wonderfulwidgets.pdfwalker.support.IOptions;
import com.wonderfulwidgets.pdfwalker.support.IParseArguments;
import com.wonderfulwidgets.pdfwalker.support.IText7Builder;

@ExtendWith(MockitoExtension.class)
public class PdfWalkerTest {

    @Mock
    IOptions optionsMock;
    @Mock
    IParseArguments parseArgumentsMock;
    @Mock
    IText7Builder itext7BuilderMock;
    @Mock
    IPdfFinder pdfFinderMock;
    @Captor ArgumentCaptor<Path> pathCaptor;
    PdfWalker pdfWalker;

    @BeforeEach
    public void beforeEach() {

        pdfWalker = new PdfWalker(optionsMock, parseArgumentsMock, itext7BuilderMock, pdfFinderMock);
    }

    @Test
    public void refusesBadPath() {

        String[] args = { "\0", "./two" };

        when(parseArgumentsMock.parse(args, optionsMock)).thenThrow(new InvalidPathException(args[0], "Null character"));

        assertThrows(InvalidPathException.class, () -> pdfWalker.run(args));
    }

    @Test
    public void walksAllRoots() throws IOException {

        String[] args = { "./one", "./two" };
        List<Path> paths = Arrays.asList(Paths.get(args[0]), Paths.get(args[1]));

        when(parseArgumentsMock.parse(args, optionsMock)).thenReturn(paths);

        pdfWalker.run(args);

        verify(pdfFinderMock, times(2)).searchFolder(pathCaptor.capture());

        List<Path> result = pathCaptor.getAllValues();

        assertTrue(result.contains(paths.get(0)));
        assertTrue(result.contains(paths.get(1)));
    }

    @Test
    public void walksDotIfNoPaths() throws IOException {

        String[] args = { };
        List<Path> paths = new ArrayList<>();

        when(parseArgumentsMock.parse(args, optionsMock)).thenReturn(paths);

        pdfWalker.run(args);

        verify(pdfFinderMock, times(1)).searchFolder(pathCaptor.capture());

        assertEquals(Paths.get("."), pathCaptor.getValue());
    }

    @Test
    public void handlesIOExceptionAndProceeds() throws IOException {

        String[] args = { "./one", "./two" };
        List<Path> paths = Arrays.asList(Paths.get(args[0]), Paths.get(args[1]));

        when(parseArgumentsMock.parse(args, optionsMock)).thenReturn(paths);
        doThrow(new IOException("Error opening file")).when(pdfFinderMock).searchFolder(any(Path.class));

        pdfWalker.run(args);

        verify(pdfFinderMock, times(2)).searchFolder(pathCaptor.capture());

        List<Path> result = pathCaptor.getAllValues();

        assertTrue(result.contains(paths.get(0)));
        assertTrue(result.contains(paths.get(1)));
    }
}