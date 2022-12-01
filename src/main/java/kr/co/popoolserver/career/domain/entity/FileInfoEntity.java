package kr.co.popoolserver.career.domain.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
public class FileInfoEntity {

    private static final Logger log = LoggerFactory.getLogger(FileInfoEntity.class.getName());

    public long entityLength = -1;

    public long offset = -1;

    public String fileId;

    public String metadata;

    public String suggestedFileName;

    public String userName;

    public Map<String, String> decodeMetadata;

    @Builder
    public FileInfoEntity(long entityLength,
                          String metadata,
                          String userName) {
        this.entityLength = entityLength;
        this.metadata = metadata;
        this.userName = userName;

        this.fileId = UUID.randomUUID().toString();
        this.fileId = this.fileId.replace("-", "_");
        this.decodeMetadata = parseMetadata(metadata);
        this.suggestedFileName = decodeMetadata.get("filename");

        log.debug("New File ID = " + fileId + ", fileName = " + suggestedFileName);
    }

    /**
     * 메타데이터는 쉼표로 구분된 키/값으로 전송
     * 이때, 키/값은 공백으로 구분되며 값은 base64로 인코딩.
     * @param metadata
     * @return
     */
    public Map<String, String> parseMetadata(String metadata){
        Map<String, String> map = new HashMap<>();
        if(metadata == null) return map;

        String[] pairs = metadata.split(",");
        for(int i=0; i<pairs.length; i++){
            String[] element = pairs[i].trim().split(" ");
            if(element.length != 2){
                log.warn("Ignoring metadata element: " + pairs[i]);
                continue;
            }

            String key = element[0];
            byte[] value;

            try {
                value = Base64.getUrlDecoder().decode(element[1]);
            }catch (IllegalArgumentException e){
                log.warn("Invalid encoding of metadata element: " + pairs[i]);
                continue;
            }
            map.put(key, new String(value));
        }
        return map;
    }
}
