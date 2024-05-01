package com.djk.core.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author duanjunkai
 * @date 2024/05/02
 */
@Data
@Builder
public class ContainerDist
{
    /**
     *
     */
    private String containerCode;
    /**
     *
     */
    private String containerSize;
    /**
     *
     */
    private String containerType;
}
