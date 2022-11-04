package be.lennix.oshish.cookie;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class OshiShSaveDto {
    public List<String> youtubers;
    public List<String> hashtags;
}
