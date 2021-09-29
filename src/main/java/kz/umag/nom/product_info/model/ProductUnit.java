package kz.umag.nom.product_info.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProductUnit implements Serializable {
    private Integer id;
    private String name;
    private Integer storeGroupId;
    private Long barcode;
    private String unitDirection;
    private Integer countOfBaseUnits;
    private boolean isDeleted;
    private Date editTime;
}
