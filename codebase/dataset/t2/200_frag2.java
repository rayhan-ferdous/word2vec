            bis = new Base64.InputStream(new java.io.BufferedInputStream(new java.io.FileInputStream(file)), Base64.DECODE);

            while ((numBytes = bis.read(buffer, length, 4096)) >= 0) length += numBytes;

            decodedData = new byte[length];
