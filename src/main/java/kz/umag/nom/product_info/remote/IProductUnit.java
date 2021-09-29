package kz.umag.nom.product_info.remote;

import kz.umag.nom.product_info.model.ProductUnit;

import javax.ejb.Remote;

@Remote
public interface IProductUnit {
    ProductUnit find(Integer productUnitId);
}
