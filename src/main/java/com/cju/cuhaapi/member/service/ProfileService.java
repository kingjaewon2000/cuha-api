package com.cju.cuhaapi.member.service;

import com.cju.cuhaapi.member.domain.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    @Value("${upload.path}")
    private String uploadPath;

    public String createFilename(String ext) {
        return UUID.randomUUID().toString() + "." + ext;
    }

    public String getFullPath(String filename) {
        return uploadPath + "/" + filename;
    }

    public Profile saveProfileFile(MultipartFile multipartFile) throws IOException {
        Profile profile = null;

        if (multipartFile != null) {
            String originalFilename = multipartFile.getOriginalFilename();
            Long size = multipartFile.getSize();
            String ext = StringUtils.getFilenameExtension(originalFilename);
            String filename = createFilename(ext);

            File file = new File(getFullPath(filename));
            multipartFile.transferTo(file);
            profile = Profile.createProfile(originalFilename, filename, size);
        }

        return profile;
    }
}
