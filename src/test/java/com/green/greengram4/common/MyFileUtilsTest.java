package com.green.greengram4.common;

import com.green.greengram4.common.MyFileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Import({ MyFileUtils.class })
@TestPropertySource(properties = {
        "file.dir=D:/home/download",
})
public class MyFileUtilsTest {

    @Autowired
    private MyFileUtils myFileUtils;

    @Test
    public void makeFolderTest() {
        String path = "/yyy";
        File preFolder = new File(myFileUtils.getUploadPrefixPath(), path);
        assertEquals(false, preFolder.exists());

        String newPath = myFileUtils.makeFolders(path);
        File newFolder = new File(newPath);
        assertEquals(preFolder.getAbsolutePath(), newFolder.getAbsolutePath());
        assertEquals(true, newFolder.exists());
    }

}
