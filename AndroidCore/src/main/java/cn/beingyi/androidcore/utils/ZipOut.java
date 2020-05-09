package cn.beingyi.androidcore.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipOut {

    ZipFile inputZipFile;
    String outPath;
    FileOutputStream fos;
    ZipOutputStream zos;
    List<String> saveList = new ArrayList<>();
    List<String> removeList = new ArrayList<>();


    List<String> entries = new ArrayList<>();

    public ZipOut(String outPath) throws Exception {
        this.outPath = outPath;
        if (new File(outPath).exists()) {
            FileUtils.delSingleFile(outPath);
        }
        this.fos = new FileOutputStream(outPath);
        this.zos = new ZipOutputStream(fos);
        this.zos.setLevel(6);
    }


    public ZipOut setInput(ZipFile zipFile) throws Exception {
        this.inputZipFile = zipFile;
        readZip(inputZipFile);
        return this;
    }

    public void addFile(String entry, byte[] data) throws IOException {
        zos.putNextEntry(new ZipEntry(entry));
        zos.write(data);
        zos.closeEntry();
        saveList.add(entry);
    }


    public void addFile(String entry, InputStream in) throws IOException {
        zos.putNextEntry(new ZipEntry(entry));
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            zos.write(buffer, 0, n);
        }
        zos.closeEntry();
        saveList.add(entry);
    }

    public void removeFile(String entry) {
        removeList.add(entry);

    }

    public void save() throws Exception {
        if (inputZipFile == null) {
            throw new FileNotFoundException("the input file not found!");
        }

        Iterator entry = entries.iterator();
        while (entry.hasNext()) {
            String key = (String) entry.next();

            if (removeList.contains(key) || saveList.contains(key)) {
                continue;
            }
            ZipEntry srcEntry = inputZipFile.getEntry(key);
            ZipEntry zipEntry = copyEntry(srcEntry);
            zos.putNextEntry(zipEntry);
            InputStream in = inputZipFile.getInputStream(inputZipFile.getEntry(key));
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ((n = in.read(buffer)) != -1) {
                zos.write(buffer, 0, n);
            }
            zos.closeEntry();

        }
        if (zos != null) {
            zos.close();
        }
        if (inputZipFile != null) {
            inputZipFile.close();
        }
        if (fos != null) {
            fos.close();
        }

    }


    public void save(CallBack callBack) throws Exception {
        if (inputZipFile == null) {
            throw new FileNotFoundException("the input file not found!");
        }
        int progress = 0;
        Iterator entry = entries.iterator();
        while (entry.hasNext()) {
            String key = (String) entry.next();
            progress++;
            callBack.onProgress(progress, entries.size(), key);

            if (removeList.contains(key) || saveList.contains(key)) {
                continue;
            }
            ZipEntry srcEntry = inputZipFile.getEntry(key);
            ZipEntry zipEntry = copyEntry(srcEntry);
            zos.putNextEntry(zipEntry);
            InputStream in = inputZipFile.getInputStream(inputZipFile.getEntry(key));
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ((n = in.read(buffer)) != -1) {
                zos.write(buffer, 0, n);
            }
            zos.closeEntry();

        }
        if (zos != null) {
            zos.close();
        }
        if (inputZipFile != null) {
            inputZipFile.close();
        }
        if (fos != null) {
            fos.close();
        }
    }


    private void readZip(ZipFile zip) throws Exception {
        Enumeration enums = zip.entries();
        while (enums.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) enums.nextElement();
            String entryName = entry.getName();
            entries.add(entryName);
        }

    }


    private ZipEntry copyEntry(ZipEntry srcEntry) {

        ZipEntry zipEntry = new ZipEntry(srcEntry.getName());
        zipEntry.setTime(new Date().getTime());
        zipEntry.setCrc(srcEntry.getCrc());
        zipEntry.setComment(srcEntry.getComment());
        zipEntry.setSize(srcEntry.getSize());
        zipEntry.setMethod(srcEntry.getMethod());

        return zipEntry;
    }


    public interface CallBack {

        void onProgress(int progress, int max, String entry);

    }

}
