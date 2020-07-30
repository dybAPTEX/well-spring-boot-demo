package com.well.studio.util.mybatisUtil;

import lombok.Data;

import java.util.List;

@Data
public class ArFileInfo {

    private String cardFront;

    private String cardBack;

    private List<String> licensePics;

    private List<String> frPaths;

}
