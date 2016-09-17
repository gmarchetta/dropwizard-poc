package com.webcompiler.service.compression;

import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.webcompiler.service.logs.LoggingService;

public class CompressionServiceTest {
  private LoggingService loggingService = mock(LoggingService.class);
  private CompressionService compressionService = new CompressionService(loggingService);
  private String webcompilerProjectFolder = System.getProperty("user.dir");
  private String user = "user";

  @Test
  public void testZipAndUnzipFolder() throws Exception {
    File compressedFolder = compressionService.compressFolder(webcompilerProjectFolder, user);
    Assert.assertTrue(compressedFolder.length() > 0);

    InputStream inputStream = new FileInputStream(compressedFolder);
    String path = compressionService.uncompressFile(inputStream, user);
    Assert.assertNotNull(path);
  }
}
