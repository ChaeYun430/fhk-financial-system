package com.fhk.client.config;

import com.fhk.asset.client.s3.S3StorageProperties;
import com.fhk.asset.client.s3.S3StoredObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FinancialS3Storage {

    /**
     * 메시지 첨부와 프로필을 S3 안에서 폴더로만 구분
     */

    private final S3Client s3Client;

    private final S3StorageProperties s3StorageProperties;

    /**
     * 방/메시지 단위로 오브젝트가 폴더처럼 깔끔하게 그룹핑됨
     * 같은 메시지에 여러 파일이 있어도 UUID로 충돌 방지
     * 나중에 “이 메시지에 관련된 파일만 삭제” 같은 것도 용이
     */
    public S3StoredObject uploadChatAttachment(Long chatroomId, Long messageId, MultipartFile file) throws IOException {

        /**
         * 클라이언트가 업로드한 파일명은 공백, 한글, 특수문자, 슬래시 등 S3 key에 쓰기 애매한 문자가 많을 수 있음.
         * 그래서 정규식으로 허용 문자만 남기고 나머지는 _로 치환:
         * 허용: 알파벳, 숫자, ., _, -
         * 나머지: 전부 _
         * 파일명이 아예 없으면 "file"로 대체.
         */
        String safeFilename = file.getOriginalFilename() == null
                ? "file"
                : file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");

        String key = String.format("%s/%d/%d/%s_%s",
                s3StorageProperties.getBaseKeyPrefix(),
                chatroomId,
                messageId,
                UUID.randomUUID(),
                safeFilename
        );

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3StorageProperties.getBucket())
                .key(key)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return S3StoredObject.builder()
                .key(key)
                .contentType(file.getContentType())
                .size(file.getSize())
                .originalFilename(safeFilename)
                .build();
    }

    /**
     * 채팅 유저 프로필 이미지 업로드
     * - 경로 예: chat/profile/{authMemberId}/{uuid}_{filename}
     */
    public S3StoredObject uploadChatUserProfile(Long authMemberId, MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String safeFilename = (originalFilename == null || originalFilename.isBlank())
                ? "profile"
                : originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");

        String key = String.format("%s/profile/%d/%s_%s",
                s3StorageProperties.getBaseKeyPrefix(),  // 예: "chat"
                authMemberId,
                UUID.randomUUID(),
                safeFilename
        );

        PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(s3StorageProperties.getBucket())
                .key(key)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        s3Client.putObject(
                putReq,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        return new S3StoredObject(
                key,
                file.getContentType(),
                file.getSize(),
                originalFilename
        );
    }
}
