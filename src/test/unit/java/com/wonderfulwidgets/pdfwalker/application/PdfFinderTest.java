// PdfFinderTest
// Copyright © 2022 Joel A Mussman. All rights reserved.
//
// Note the removal of strictness below.

package com.wonderfulwidgets.pdfwalker.application;

import com.itextpdf.kernel.exceptions.BadPasswordException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.wonderfulwidgets.pdfwalker.support.IFilesUtility;
import com.wonderfulwidgets.pdfwalker.support.IText7Builder;
import com.wonderfulwidgets.pdfwalker.support.Options;

@ExtendWith(MockitoExtension.class)
public class PdfFinderTest {

    @Mock IFilesUtility filesUtility;
    @Mock IText7Builder itext7BuilderMock;
    @Mock Options optionsMock;
    @Mock Path pathMock;
    @Mock PdfReader pdfReaderMock;
    @Mock PdfDocument pdfDocumentMock;
    @Mock PrintStream outStreamMock;
    @Mock PrintStream errorStreamMock;
    PdfFinder pdfFinder;

    @BeforeEach
    public void beforeEach() {

        pdfFinder = new PdfFinder(optionsMock, filesUtility, itext7BuilderMock, outStreamMock, errorStreamMock);
    }

    @Test
    public void checkFileOnlyChecksRegularFiles() throws IOException {

        String filepath = "./mysubdirectory";

        when(optionsMock.isDeepInspection()).thenReturn(true);
        when(filesUtility.isRegularFile(pathMock)).thenReturn(false);

        pdfFinder.checkFile(pathMock);

        verify(itext7BuilderMock, never()).buildPdfReader(filepath);
    }

    @Test
    public void checkFileEnforcesShallowScan() {

        String filepath = "./maybepdffile.txt";

        when(optionsMock.isDeepInspection()).thenReturn(false);
        when(pathMock.toString()).thenReturn(filepath);

        pdfFinder.checkFile(pathMock);

        verify(filesUtility, never()).isRegularFile(pathMock);
    }

    @Test
    public void checkFileRunsDeepCheck() {

        String filepath = "./maybepdffile.txt";

        when(optionsMock.isDeepInspection()).thenReturn(true);
        when(optionsMock.isLogAllPdfFiles()).thenReturn(false);
        when(pathMock.toString()).thenReturn(filepath);
        when(filesUtility.isRegularFile(pathMock)).thenReturn(true);

        pdfFinder.checkFile(pathMock);

        verify(filesUtility, times(1)).isRegularFile(pathMock);
    }

    @Test
    public void checkFileCorrectFile() throws IOException {

        String filepath = "./mypdffile.pdf";

        when(optionsMock.isDeepInspection()).thenReturn(false);
        when(optionsMock.isLogAllPdfFiles()).thenReturn(false);
        when(pathMock.toString()).thenReturn(filepath);
        when(filesUtility.isRegularFile(pathMock)).thenReturn(true);
        when(itext7BuilderMock.buildPdfReader(filepath)).thenReturn(pdfReaderMock);
        when(itext7BuilderMock.buildPdfDocument(pdfReaderMock)).thenReturn(pdfDocumentMock);

        pdfFinder.checkFile(pathMock);

        verify(itext7BuilderMock, times(1)).buildPdfReader(filepath);
    }

    @Test
    public void checkFileUnencryptedFileNotLogged() throws IOException {

        String filepath = "./mynotencryptedfile.pdf";

        when(optionsMock.isDeepInspection()).thenReturn(false);
        when(optionsMock.isLogAllPdfFiles()).thenReturn(false);
        when(pathMock.toString()).thenReturn(filepath);
        when(filesUtility.isRegularFile(pathMock)).thenReturn(true);
        when(itext7BuilderMock.buildPdfReader(filepath)).thenReturn(pdfReaderMock);
        when(itext7BuilderMock.buildPdfDocument(pdfReaderMock)).thenReturn(pdfDocumentMock);

        pdfFinder.checkFile(pathMock);

        verify(outStreamMock, Mockito.never()).println();
    }

    @Test
    public void checkFileEncryptedFileLogged() throws IOException {

        String filepath = "./myencryptedfile.pdf";

        when(optionsMock.isDeepInspection()).thenReturn(false);
        when(pathMock.toString()).thenReturn(filepath);
        when(filesUtility.isRegularFile(pathMock)).thenReturn(true);
        when(itext7BuilderMock.buildPdfReader(filepath)).thenReturn(pdfReaderMock);
        when(itext7BuilderMock.buildPdfDocument(pdfReaderMock)).thenThrow(new BadPasswordException(BadPasswordException.PdfReaderNotOpenedWithOwnerPassword));

        pdfFinder.checkFile(pathMock);

        verify(outStreamMock, times(1)).println(filepath);
    }

    @Test
    public void checkFileAllPdfFilesLogged() throws IOException {

        String filepath = "./myencryptedfile.txt";

        when(optionsMock.isDeepInspection()).thenReturn(true);
        when(optionsMock.isLogAllPdfFiles()).thenReturn(true);
        when(pathMock.toString()).thenReturn(filepath);
        when(filesUtility.isRegularFile(pathMock)).thenReturn(true);
        when(itext7BuilderMock.buildPdfReader(filepath)).thenReturn(pdfReaderMock);
        when(itext7BuilderMock.buildPdfDocument(pdfReaderMock)).thenReturn(pdfDocumentMock);

        pdfFinder.checkFile(pathMock);

        verify(outStreamMock, times(1)).println(filepath);
    }

    @Test
    public void checkFileIOEexceptionLogged() throws IOException {

        String filepath = "./nofile.pdf";
        String exceptionMessage = String.format("%s not found", filepath);

        when(optionsMock.isDeepInspection()).thenReturn(false);
        when(pathMock.toString()).thenReturn(filepath);
        when(filesUtility.isRegularFile(pathMock)).thenReturn(true);
        when(itext7BuilderMock.buildPdfReader(anyString())).thenThrow(new IOException(exceptionMessage));

        pdfFinder.checkFile(pathMock);

        verify(errorStreamMock, times(1)).println(exceptionMessage);
    }

    @Test
    public void searchFolderCorrectPathWalked() throws IOException {

        String filepath = "./documents";

        when(pathMock.toString()).thenReturn(filepath);
        when(filesUtility.walk(pathMock)).thenReturn(Stream.of(pathMock));

        pdfFinder.searchFolder(pathMock);

        verify(filesUtility, times(1)).walk(pathMock);
    }
}
