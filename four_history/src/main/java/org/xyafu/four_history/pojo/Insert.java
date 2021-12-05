package org.xyafu.four_history.pojo;

import lombok.Data;
import lombok.ToString;

import java.lang.ref.PhantomReference;

@Data
@ToString
public class Insert {

    private String id;
    private String title,focusImg,pubDate,link,channelName;
}
