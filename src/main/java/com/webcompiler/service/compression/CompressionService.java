package com.webcompiler.service.compression;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;

import com.google.inject.Inject;
import com.webcompiler.app.WebcompilerException;
import com.webcompiler.service.logs.LoggingService;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class CompressionService {
  // Should be externalized to yml
  private String uncompressFolder = "/tmp";
  private LoggingService loggingService;

  @Inject
  public CompressionService(LoggingService loggingService) {
    this.loggingService = loggingService;
  }

  public String uncompressFile(InputStream fileToUncompress, String user) {
    OutputStream outputStream = null;
    try {
      File tmpFile = new File(String.format("%s/%s_%s.zip", uncompressFolder, user, new Date().getTime()));
      tmpFile.deleteOnExit();
      outputStream = new FileOutputStream(tmpFile);
      IOUtils.copy(fileToUncompress, outputStream);
      outputStream.close();
      return uncompressFile(tmpFile, user);
    } catch (IOException io) {
      loggingService.error("Error reading submitted file. Please, check your file and try again.", user, io);
      throw new WebcompilerException("Error reading submitted file. Please, check your file and try again.",
          Status.BAD_REQUEST);
    } finally {
      try {
        fileToUncompress.close();
      } catch (IOException io) {
        loggingService.error("Error reading submitted file. Please, check your file and try again.", user, io);
        throw new WebcompilerException("Error reading submitted file. Please, check your file and try again.",
            Status.BAD_REQUEST);
      }
    }
  }

  public String uncompressFile(File fileToUncompress, String user) {
    String extractionPath = String.format("%s/%s_%s", uncompressFolder, user, new Date().getTime());
    try {
      ZipFile zipFile = new ZipFile(fileToUncompress);
      zipFile.extractAll(extractionPath);
    } catch (ZipException e) {
      loggingService.error("Error uncompressing file. Please, check your file and try again.", user, e);
      throw new WebcompilerException("Error uncompressing file. Please, check your file and try again.",
          Status.BAD_REQUEST);
    }

    return extractionPath;
  }

  public File compressFolder(String pathToFolder, String user) {
    ZipFile zipFile = null;
    try {
      File file = new File(String.format("%s//%s_%s.zip", uncompressFolder, user, new Date().getTime()));
      zipFile = new ZipFile(file);
      ZipParameters parameters = new ZipParameters();
      parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
      parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
      zipFile.createZipFileFromFolder(pathToFolder, parameters, false, 0);
    } catch (ZipException e) {
      loggingService.error("Error uncompressing file. Please, check your file and try again.", user, e);
      throw new WebcompilerException("Error uncompressing file. Please, check your file and try again.",
          Status.BAD_REQUEST);
    }

    return zipFile.getFile();
  }
}
