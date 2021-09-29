package kz.umag.remote.test;

import kz.umag.nom.product_info.remote.IProductUnit;
import kz.umag.nom.product_info.model.ProductUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitializerBean implements InitializingBean {

    final IProductUnit productUnit;

    public InitializerBean(IProductUnit productUnit) {
        this.productUnit = productUnit;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ProductUnit unit = productUnit.find(1);
        if (unit != null) {
            log.info(String.format(
                    "Product unit '%s' for barcode %s received from monolith",
                    unit.getName(),
                    unit.getBarcode()
            ));
        }
    }

}
