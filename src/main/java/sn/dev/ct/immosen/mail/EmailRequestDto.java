package sn.dev.ct.immosen.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EmailRequestDto {
    public String to;
    public String subject;
    public String content;
}
